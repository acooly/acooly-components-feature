package com.acooly.module.olog.storage.service.impl;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.utils.Compressions;
import com.acooly.core.utils.Dates;
import com.acooly.core.utils.Reflections;
import com.acooly.core.utils.mapper.CsvMapper;
import com.acooly.module.olog.config.OLogProperties;
import com.acooly.module.olog.storage.dao.OlogDao;
import com.acooly.module.olog.storage.domain.OlogEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

/**
 * Olog数据归档数据库实现
 *
 * <p>功能：
 * <li>归档到指定目录，默认在WEB-INF/logs/archive/Olog_yyyyMMdd.log
 * <li>支持归档后自动压缩归档文件
 * <li>支持归档后清除已归档的数据
 * <li>支持设置归档的数据范围，使用设定N天前的数据可以归档
 * <li>支持数据每次抓取的大小（服务器内存占用更下，可控）
 * <li>支持指定归档文件的前缀和文件的编码
 *
 * @author zhangpu
 */
@Getter
@Setter
@Service
public class OlogArchiveService {

    /**
     * 系统日志
     */
    private static final Logger logger = LoggerFactory.getLogger(OlogArchiveService.class);

    @Autowired
    private OLogProperties oLogProperties;

    /**
     * Olog日志管理
     */
    @Autowired
    private OlogDao ologDao;

    public void archive() {
        if (!getArchive().isEnable()) {
            return;
        }
        long millisecond = getArchive().getBeforeDays() * 24 * 60 * 60 * 1000;
        Date executeTime = new Date();
        Date beforeDate =
                DateUtils.truncate(Dates.addDate(executeTime, -millisecond), Calendar.DAY_OF_MONTH);

        PrintWriter out = null;
        try {
            File dest = getArchiveFile();
            if (dest.exists()) {
                dest.delete();
            }
            out =
                    new PrintWriter(
                            new BufferedWriter(
                                    new OutputStreamWriter(
                                            new FileOutputStream(dest, true), getArchive().getFileEncoding())));

            Map<String, Object> conditions = new HashMap<>();
            conditions.put("LT_operateTime", beforeDate);
            int batchSize = getArchive().getBatchSize();
            PageInfo<OlogEntity> pageInfo = new PageInfo<>(batchSize, 1);
            pageInfo = ologDao.query(pageInfo, conditions, null);
            archivePage(pageInfo.getPageResults(), out);

            long totalPages = pageInfo.getTotalPage();
            for (int i = 2; i <= totalPages; i++) {
                pageInfo = new PageInfo<>(batchSize, i);
                pageInfo = ologDao.query(pageInfo, conditions, null);
                archivePage(pageInfo.getPageResults(), out);
            }
            // 先关闭归档文件，然后在归档压缩
            long totalCount = pageInfo.getTotalCount();
            pageInfo.setPageResults(null);
            pageInfo = null;

            IOUtils.closeQuietly(out);
            String executeTimeForLogger = Dates.format(executeTime);
            String beforeDateForLogger = Dates.format(beforeDate, "yyyy-MM-dd");
            logger.info(
                    "Archive file success. execute-time: ["
                            + executeTimeForLogger
                            + "], Data range before:["
                            + beforeDateForLogger
                            + "], total:["
                            + totalCount
                            + "], file:["
                            + dest.getName()
                            + "]");

            // 压缩
            archive(dest, totalCount, executeTimeForLogger, beforeDateForLogger);

            // 清理
            cleanup(beforeDate, totalCount, executeTimeForLogger, beforeDateForLogger);

        } catch (Exception e) {
            logger.info("Archive[ before " + Dates.format(beforeDate) + "] failue. -->" + e.getMessage());
            throw new RuntimeException(
                    "Archive[ before " + Dates.format(beforeDate) + "] failue. -->" + e.getMessage());
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    private void cleanup(Date beforeDate, long totalCount, String executeTimeForLogger, String beforeDateForLogger) {
        if (getArchive().isCleanup()) {
            ologDao.cleanup(beforeDate);
            logger.info(
                    "Archive cleanup success. execute-time: ["
                            + executeTimeForLogger
                            + "], Data range before:["
                            + beforeDateForLogger
                            + "], total:["
                            + totalCount
                            + "]");
        }
    }

    private void archive(File dest, long totalCount, String executeTimeForLogger, String beforeDateForLogger) {
        if (getArchive().isFileCompress()) {
            try {
                File target = Compressions.zip(dest);
                logger.info(
                        "Archive Compression success.execute-time: ["
                                + executeTimeForLogger
                                + "], Data range before:["
                                + beforeDateForLogger
                                + "], total:["
                                + totalCount
                                + "],file: ["
                                + target.getName()
                                + "]");
            } catch (Exception e) {
                logger.info(
                        "Archive Compression failure. execute-time: ["
                                + executeTimeForLogger
                                + "], Data range before:["
                                + beforeDateForLogger
                                + "], total:["
                                + totalCount
                                + "], errorMessage: [ "
                                + e.getMessage()
                                + "]");
                // don't do anything.
            }
        }
    }

    protected String marshal(OlogEntity olog) {
        return CsvMapper.marshal(
                olog, Reflections.getSimpleFieldNames(OlogEntity.class).toArray(new String[]{}));
    }

    /**
     * 按文件名前缀和天的日期戳命名
     *
     * @return
     */
    protected String getArchiveFileName() {
        return getArchive().getFileNamePrefix() + "_" + Dates.format(new Date(), "yyyyMMdd") + ".log";
    }

    /**
     * 归档1页数据，每页数据大小根据参数batchSize确定。
     *
     * @param ologs
     * @param out
     */
    private void archivePage(List<OlogEntity> ologs, PrintWriter out) {
        for (OlogEntity olog : ologs) {
            out.println(marshal(olog));
        }
        out.flush();
    }

    /**
     * 获取归档文件的文件名
     *
     * @return
     */
    private File getArchiveFile() {
        return new File(getRoot(), getArchiveFileName());
    }

    /**
     * 获取归档文件的存放目录，如果没有，则创建
     *
     * @return
     */
    private File getRoot() {
        String root = getArchive().getPath();
        if (StringUtils.isBlank(root)) {
            throw new RuntimeException("Archive storage root is not setting.");
        }
        if (StringUtils.contains(root, ":")) {
            String rootPrefix = StringUtils.substringBefore(root, ":");
            String rootPrefixReplaced = System.getProperty(rootPrefix);
            if (StringUtils.isBlank(rootPrefixReplaced)) {
                throw new RuntimeException(
                        "exsit prefix:[" + rootPrefix + "], but there is no system environment variable.");
            }
            root = StringUtils.trimToEmpty(rootPrefixReplaced) + StringUtils.substringAfter(root, ":");
        }
        File rootFile = new File(root);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        return rootFile;
    }

    private OLogProperties.Archive getArchive() {
        return oLogProperties.getStorage().getArchive();
    }
}

/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-14 16:11 创建
 */
package com.acooly.module.olog.config;

import com.acooly.core.common.olog.annotation.OlogModule;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

import static com.acooly.module.olog.config.OLogProperties.Collector.DEFAULT_IGNORE_PARAMETERS;
import static com.acooly.module.olog.config.OLogProperties.PREFIX;

/**
 * @author qiubo@yiji.com
 */
@ConfigurationProperties(prefix = PREFIX)
@Data
@Slf4j
public class OLogProperties implements InitializingBean {
    public static final String PREFIX = "acooly.olog";

    private Boolean enable = true;
    private Storage storage = new Storage();
    private Collector collector = new Collector();

    @Override
    public void afterPropertiesSet() throws Exception {
        this.getCollector()
                .setIgnoreParameters(
                        DEFAULT_IGNORE_PARAMETERS
                                + (Strings.isNullOrEmpty(this.getCollector().getIgnoreParameters())
                                ? ""
                                : this.getCollector().getIgnoreParameters()));
    }

    @Data
    public static class Collector {
        static final String DEFAULT_IGNORE_PARAMETERS = "ec*,autoInc,submit,*password*,_csrf,";
        private boolean saveParameter = true;
        // 全局忽略参数（支持前后*）
        private String ignoreParameters;
        // 全局忽略方法（支持前后*），多个逗号分隔
        private String ignoreMethods;

        /**
         * RequestURI 和模块名的映射信息。也可以通过{@link OlogModule}配置
         */
        private Map<String, String> moduleNameMapping = Maps.newHashMap();
        /**
         * 请求方法和操作名的映射信息。也可以通过{@link OlogModule}配置
         */
        private Map<String, String> actionNameMapping = Maps.newHashMap();

        public Collector() {
            // 默认请求方法和操作名的映射信息
            actionNameMapping.put("list", "分页查询");
            actionNameMapping.put("query", "条件查询");
            actionNameMapping.put("save", "新增");
            actionNameMapping.put("update", "修改");
            actionNameMapping.put("show", "查看/详情");
            actionNameMapping.put("get", "查看/详情");
            actionNameMapping.put("remove", "删除");
            actionNameMapping.put("removes", "批量删除");
            actionNameMapping.put("listJson", "分页查询");
            actionNameMapping.put("queryJson", "条件查询");
            actionNameMapping.put("saveJson", "新增");
            actionNameMapping.put("updateJson", "修改");
            actionNameMapping.put("showJson", "查看/详情");
            actionNameMapping.put("deleteJson", "删除");
            actionNameMapping.put("importJson", "导入");
            actionNameMapping.put("imports", "导入");
            actionNameMapping.put("exports", "导出");
            actionNameMapping.put("exportXls", "导出EXCEL");
            actionNameMapping.put("exportCsv", "导出CSV");
            actionNameMapping.put("importXls", "导入EXCEL");
            actionNameMapping.put("importCsv", "导入CSV");
            actionNameMapping.put("changePassword", "修改密码");
            actionNameMapping.put("getAllResourceNode", "角色查询");
            actionNameMapping.put("listAllJson", "查询");
        }
    }

    @Data
    public static class Storage {
        private boolean enable = true;
        private Archive archive = new Archive();
    }

    @Data
    public static class Archive {
        private boolean enable = true;

        /**
         * 归档调度
         */
        private String cron = "0 1 0 1 * ?";
        /**
         * 存储根目录
         *
         * <p>可选使用JVM系统变量和物理路径. 默认存在在webapp下的/WEB-INF/logs/archive目录
         */
        private String path = "/media/data/olog/";

        /**
         * 按天设定每次归档的数据量（多少天前的数据需要归档）
         */
        private int beforeDays = 0;
        /**
         * 抓取数据批量大小：为防止OR-MAPPING一次抓取过的数据到服务器内存，采用分页多次抓取，该参数为每次抓取的行数
         */
        private int batchSize = 1000;
        /**
         * 归档完成后是否清除已归档的数据
         */
        private boolean cleanup = true;

        /**
         * 归档文件前缀,组件会在后面自动加上："_yyyyMMdd.log"
         */
        private String fileNamePrefix = "olog_archive";
        /**
         * 归档文件的编码
         */
        private String fileEncoding = "UTF-8";
        /**
         * 归档文件是否需要压缩（如果压缩后，则替换原来的文本文件）
         */
        private boolean fileCompress = true;
    }
}

package com.acooly.module.obs;

import com.acooly.module.obs.common.OssFile;
import com.aliyun.oss.model.ObjectMetadata;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

/**
 * 对象存储服务访问接口
 *
 * @author xiyang
 */
public interface ObsService {

    /**
     * 传指定文件到指定的bucketName
     *
     * @param bucketName bucketName（为空则使用默认桶配置）
     * @param key        文件名
     * @param file       上传文件
     */
    OssFile putObject(String bucketName, String key, File file);

    /**
     * 传指定文件到指定的bucketName
     *
     * @param bucketName  bucketName （为空则使用默认桶配置）
     * @param key         文件名
     * @param inputStream 输入流
     */
    OssFile putObject(String bucketName, String key, InputStream inputStream);

    /**
     * 通过文件key获取完整url
     * @param key
     * @return
     */
    String getUrlByKey(String key);

    /**
     * 将指定文件key转换为临时授权的可访问的url
     *
     * @param bucketName   bucketName （为空则使用默认桶配置）
     * @param key          文件相对地址
     * @param processStyle 图片处理参数，一般用于获取缩略图
     * @param expireDate   过期时间 (为空则使用配置文件中指定的时间，24小时后过期)
     * @return
     */
    String getAccessUrlBySts(String bucketName, String key, String processStyle, Date expireDate);

    /**
     * 传指定文件到指定的bucketName
     *
     * @param bucketName bucketName （为空则使用默认桶配置）
     * @param key        文件名
     * @param file       文件
     * @param metadata   Object的元数据
     */
    OssFile putObject(String bucketName, String key, File file, ObjectMetadata metadata);

    /**
     * 传指定文件到指定的bucketName
     *
     * @param bucketName bucketName （为空则使用默认桶配置）
     * @param key        文件名
     * @param input      输入流
     * @param metadata   Object的元数据
     */
    OssFile putObject(String bucketName, String key, InputStream input, ObjectMetadata metadata);

    /**
     * 下载文件
     *
     * @param bucketName bucketName （为空则使用默认桶配置）
     * @param key        文件名
     */
    OssFile getObject(String bucketName, String key);

    /**
     * 删除文件
     *
     * @param bucketName bucketName （为空则使用默认桶配置）
     * @param key        文件名
     * @return 删除结果
     */
    void deleteObject(String bucketName, String key);
}

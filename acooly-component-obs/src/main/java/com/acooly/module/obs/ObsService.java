package com.acooly.module.obs;

import com.acooly.module.obs.common.model.ObjectMetadata;
import com.acooly.module.obs.common.model.ObjectResult;
import com.acooly.module.obs.common.model.ObsObject;
import com.acooly.module.obs.exceptions.ClientException;
import com.acooly.module.obs.exceptions.ObsException;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

/**
 * 对象存储服务访问接口
 *
 * @author shuijing
 */
public interface ObsService {

    /**
     * 传指定文件到指定的bucketName
     *
     * @param bucketName bucketName
     * @param key        文件名
     * @param file       上传文件
     */
    ObjectResult putObject(String bucketName, String key, File file)
            throws ObsException, ClientException;

    /**
     * 传指定文件到指定的bucketName
     *
     * @param bucketName  bucketName
     * @param key         文件名
     * @param inputStream 输入流
     */
    ObjectResult putObject(String bucketName, String key, InputStream inputStream)
            throws ObsException, ClientException;

    /**
     * 传指定文件到指定的bucketName
     *
     * @param bucketName bucketName
     * @param key        文件名
     * @param file       文件
     * @param metadata   Object的元数据
     */
    ObjectResult putObject(String bucketName, String key, File file, ObjectMetadata metadata)
            throws ObsException, ClientException;

    /**
     * 传指定文件到指定的bucketName
     *
     * @param bucketName bucketName
     * @param key        文件名
     * @param input      输入流
     * @param metadata   Object的元数据
     */
    ObjectResult putObject(String bucketName, String key, InputStream input, ObjectMetadata metadata)
            throws ObsException, ClientException;

    /**
     * 签名方式上传 暂未实现
     *
     * @param signedUrl      签名url
     * @param file           文件
     * @param requestHeaders 请求头
     */
    ObjectResult putObject(URL signedUrl, File file, Map<String, String> requestHeaders)
            throws ObsException, ClientException;

    /**
     * 签名方式上传 暂未实现
     *
     * @param signedUrl      签名url
     * @param inputStream    输入流
     * @param requestHeaders 请求头
     */
    ObjectResult putObject(URL signedUrl, InputStream inputStream, Map<String, String> requestHeaders)
            throws ObsException, ClientException;

    /**
     * 下载文件
     *
     * @param bucketName bucketName
     * @param key        文件名
     */
    ObsObject getObject(String bucketName, String key) throws ObsException, ClientException;

    /**
     * 删除文件
     *
     * @param bucketName bucketName
     * @param key        文件名
     * @return 删除结果
     */
    void deleteObject(String bucketName, String key) throws ObsException, ClientException;
}

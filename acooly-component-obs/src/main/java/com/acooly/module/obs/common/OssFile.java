package com.acooly.module.obs.common;

import com.aliyun.oss.model.ObjectMetadata;
import lombok.Data;

import java.io.InputStream;
import java.io.Serializable;

/**
 * @author liangsong
 * @date 2020-04-02 11:45
 */
@Data
public class OssFile implements Serializable {

    /**
     * 传入时的key，一般为相对路径
     */
    private String key;
    /**
     * eTag 文件的md5值
     */
    private String eTag;

    /**
     * 完整访问地址，若策略为公网可访问，则该url可直接通过公网访问，若开启为私有访问，则该url需要进行签名，或者通过sts进行转换为可访问的url
     */
    private String url;

    /**
     * 可浏览器展示的访问url，需要oss配合配置域名（仅阿里云支持）
     */
    private String viewUrl;

    /**
     * 文件流
     */
    private InputStream fileInputStream;

    /**
     * 元数据
     */
    private ObjectMetadata metadata = new ObjectMetadata();
}

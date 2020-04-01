package com.acooly.module.obs.client.oss;

import lombok.Getter;
import lombok.Setter;

/**
 * 包含了在发送OSS GET请求时可以重载的返回请求头。
 */
@Getter
@Setter
public class ResponseHeaderOverrides {

    public static final String RESPONSE_HEADER_CONTENT_TYPE = "response-content-type";
    public static final String RESPONSE_HEADER_CONTENT_LANGUAGE = "response-content-language";
    public static final String RESPONSE_HEADER_EXPIRES = "response-expires";
    public static final String RESPONSE_HEADER_CACHE_CONTROL = "response-cache-control";
    public static final String RESPONSE_HEADER_CONTENT_DISPOSITION = "response-content-disposition";
    public static final String RESPONSE_HEADER_CONTENT_ENCODING = "response-content-encoding";
    private String contentType;
    private String contentLangauge;
    private String expires;
    private String cacheControl;
    private String contentDisposition;
    private String contentEncoding;

}

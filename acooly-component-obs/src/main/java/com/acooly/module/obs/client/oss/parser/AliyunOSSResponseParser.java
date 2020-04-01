package com.acooly.module.obs.client.oss.parser;

/**
 * @author shuijing
 */
public class AliyunOSSResponseParser extends BaseMessageResponseParser {

    public static final String REQUEST_ID = "RequestId";
    public static final String MESSAGE = "Message";
    public static final String CONTENT_MD5 = "Content-MD5";
    private static final AliyunOSSResponseParser INSTANCE = new AliyunOSSResponseParser();

    public static final AliyunOSSResponseParser getInstance() {
        return INSTANCE;
    }

    //  private static class SingletonHolder {
    //    private static final AliyunOSSResponseParser INSTANCE = new AliyunOSSResponseParser();
    //  }
}

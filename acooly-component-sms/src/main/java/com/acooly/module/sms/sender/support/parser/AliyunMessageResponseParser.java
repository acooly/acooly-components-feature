package com.acooly.module.sms.sender.support.parser;

/**
 * @author shuijing
 */
public class AliyunMessageResponseParser extends BaseMessageResponseParser {

    public static final String MessageId = "MessageId";
    public static final String Message = "Message";
    public static final String MessageBodyMD5 = "MessageBodyMD5";

    public static final AliyunMessageResponseParser getInstance() {
        return AliyunMessageResponseParser.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final AliyunMessageResponseParser INSTANCE = new AliyunMessageResponseParser();
    }
}

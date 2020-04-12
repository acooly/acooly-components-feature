package com.acooly.module.smsend;

import java.util.List;
import java.util.Map;

/**
 * 短信发送服务
 *
 * @author zhangpu
 */
public interface SmsendService {

    /**
     * 单条发送
     *
     * @param mobileNo
     * @param content
     */
    void send(String mobileNo, String content);

    void send(List<String> mobileNos, String content);

    /**
     * 单条发送(附带context)，context的内容存储到数据库发送记录
     *
     * @param mobileNo
     * @param content
     * @param smsContext
     */
    void send(String mobileNo, String content, SmsContext smsContext);

    void send(List<String> mobileNos, String content, SmsContext smsContext);

    void send(String mobileNo, String templateCode, Map<String, String> templateParams, SmsContext smsContext);

    void send(String mobileNo, String templateCode, Map<String, String> templateParams);

    void send(List<String> mobileNos, String templateCode, Map<String, String> templateParams, SmsContext smsContext);

    void send(List<String> mobileNos, String templateCode, Map<String, String> templateParams);
}

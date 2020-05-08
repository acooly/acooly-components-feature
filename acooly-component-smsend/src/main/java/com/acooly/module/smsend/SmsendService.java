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
     * 直接发送（裸发）
     *
     * @param mobileNos
     * @param content
     * @param contentSign
     * @param smsContext
     */
    void send(String mobileNo, String content, String contentSign, SmsendContext smsContext);

    default void send(String mobileNo, String content, String contentSign) {
        send(mobileNo, content, contentSign, null);
    }

    default void send(String mobileNo, String content) {
        send(mobileNo, content, null);
    }

    /**
     * 直接群发
     *
     * @param mobileNos
     * @param content
     * @param contentSign
     * @param smsContext
     */
    void send(List<String> mobileNos, String content, String contentSign, SmsendContext smsContext);

    default void send(List<String> mobileNos, String content, String contentSign) {
        send(mobileNos, content, contentSign, null);
    }

    default void send(List<String> mobileNos, String content) {
        send(mobileNos, content, null);
    }

    /**
     * 模板单发
     *
     * @param mobileNo       手机号码
     * @param templateCode   模板编码
     * @param templateParams 模板参数
     * @param contentSign    内容签名
     * @param smsendContext  发送会话
     */
    void sendTemplate(String mobileNo, String templateCode, Map<String, String> templateParams, String contentSign, SmsendContext smsendContext);

    default void sendTemplate(String mobileNo, String templateCode, Map<String, String> templateParams, String contentSign) {
        sendTemplate(mobileNo, templateCode, templateParams, contentSign, null);
    }

    default void sendTemplate(String mobileNo, String templateCode, Map<String, String> templateParams) {
        sendTemplate(mobileNo, templateCode, templateParams, null);
    }


    void sendTemplate(List<String> mobileNos, String templateCode, Map<String, String> templateParams, String contentSign, SmsendContext smsendContext);

    default void sendTemplate(List<String> mobileNos, String templateCode, Map<String, String> templateParams, String contentSign) {
        sendTemplate(mobileNos, templateCode, templateParams, contentSign, null);
    }

    default void sendTemplate(List<String> mobileNos, String templateCode, Map<String, String> templateParams) {
        sendTemplate(mobileNos, templateCode, templateParams, null);
    }
}

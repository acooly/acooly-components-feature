package com.acooly.module.smsend.sender;

import com.acooly.module.smsend.enums.SmsProvider;

import java.util.List;
import java.util.Map;

public interface ShortMessageSender {
    /**
     * 发送单条短信
     *
     * @param mobileNo
     * @param content
     */
    String send(String mobileNo, String content);

    /**
     * 模板方式发送
     *
     * @param mobileNo
     * @param templateCode
     * @param templateParams
     * @return
     */
    String send(String mobileNo, String templateCode, Map<String, String> templateParams);

    /**
     * 批量发送短信（短信内容一致）
     *
     * @param mobileNos
     * @param content
     */
    String send(List<String> mobileNos, String content);

    String send(List<String> mobileNos, String templateCode, Map<String, String> templateParams);

    SmsProvider getProvider();
}

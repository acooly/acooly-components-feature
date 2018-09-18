package com.acooly.module.sms.sender;

import java.util.List;

public interface ShortMessageSender {
    /**
     * 发送单条短信
     *
     * @param mobileNo
     * @param content
     */
    String send(String mobileNo, String content);

    /**
     * 批量发送短信（短信内容一致）
     *
     * @param mobileNos
     * @param content
     */
    String send(List<String> mobileNos, String content);

    String getProvider();
}

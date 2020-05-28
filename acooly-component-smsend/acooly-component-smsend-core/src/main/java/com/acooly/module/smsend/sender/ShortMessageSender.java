package com.acooly.module.smsend.sender;

import com.acooly.module.smsend.common.enums.SmsProvider;
import com.acooly.module.smsend.sender.dto.SmsResult;

import java.util.Map;
import java.util.Set;

/**
 * 短信发送接口
 *
 * @author zhangpu
 * @date 2020-05-04
 */
public interface ShortMessageSender {

    /**
     * 直接发送
     *
     * @param mobileNo
     * @param content
     * @param contentSign
     * @return
     */
    SmsResult send(String mobileNo, String content, String contentSign);

    default SmsResult send(String mobileNo, String content) {
        return send(mobileNo, content, null);
    }

    SmsResult send(Set<String> mobileNos, String content, String contentSign);

    default SmsResult send(Set<String> mobileNos, String content) {
        return send(mobileNos, content, null);
    }

    /**
     * 模板发送
     *
     * @param mobileNo
     * @param templateCode
     * @param templateParams
     * @return
     */
    SmsResult sendTemplate(String mobileNo, String templateCode, Map<String, String> templateParams, String contentSign);

    default SmsResult sendTemplate(String mobileNo, String templateCode, Map<String, String> templateParams) {
        return sendTemplate(mobileNo, templateCode, templateParams, null);
    }

    SmsResult sendTemplate(Set<String> mobileNos, String templateCode, Map<String, String> templateParams, String contentSign);

    default SmsResult sendTemplate(Set<String> mobileNos, String templateCode, Map<String, String> templateParams) {
        return sendTemplate(mobileNos, templateCode, templateParams, null);
    }

    SmsProvider getProvider();
}

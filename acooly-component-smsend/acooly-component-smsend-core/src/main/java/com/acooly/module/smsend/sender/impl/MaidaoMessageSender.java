package com.acooly.module.smsend.sender.impl;

import com.acooly.module.smsend.common.enums.SmsProvider;
import com.acooly.module.smsend.sender.dto.SmsResult;

import java.util.Map;
import java.util.Set;

/**
 * 北京漫道短发发送实现
 *
 * @author zhangpu
 */
//@Component
public class MaidaoMessageSender extends AbstractShortMessageSender {

    @Override
    public SmsResult send(String mobileNo, String content, String contentSign) {
        return null;
    }

    @Override
    public SmsResult send(Set<String> mobileNos, String content, String contentSign) {
        return null;
    }

    @Override
    public SmsResult sendTemplate(String mobileNo, String templateCode, Map<String, String> templateParams, String contentSign) {
        return null;
    }

    @Override
    public SmsResult sendTemplate(Set<String> mobileNos, String templateCode, Map<String, String> templateParams, String contentSign) {
        return null;
    }

    @Override
    public SmsProvider getProvider() {
        return SmsProvider.MaiDao;
    }
}

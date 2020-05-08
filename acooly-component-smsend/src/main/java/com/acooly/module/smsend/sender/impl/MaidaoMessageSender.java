package com.acooly.module.smsend.sender.impl;

import com.acooly.module.smsend.enums.SmsProvider;
import com.acooly.module.smsend.sender.dto.SmsResult;

import java.util.List;
import java.util.Map;

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
    public SmsResult send(List<String> mobileNos, String content, String contentSign) {
        return null;
    }

    @Override
    public SmsResult sendTemplate(String mobileNo, String templateCode, Map<String, String> templateParams, String contentSign) {
        return null;
    }

    @Override
    public SmsResult sendTemplate(List<String> mobileNos, String templateCode, Map<String, String> templateParams, String contentSign) {
        return null;
    }

    @Override
    public SmsProvider getProvider() {
        return SmsProvider.MaiDao;
    }
}

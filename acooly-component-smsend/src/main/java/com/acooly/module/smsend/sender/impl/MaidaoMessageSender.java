package com.acooly.module.smsend.sender.impl;

import com.acooly.module.smsend.enums.SmsProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 北京漫道短发发送实现
 *
 * @author zhangpu
 */
@Component
public class MaidaoMessageSender extends AbstractShortMessageSender {

    @Override
    public String send(String mobileNo, String content) {
        return null;
    }

    @Override
    public String send(String mobileNo, String templateCode, Map<String, String> templateParams) {
        return null;
    }

    @Override
    public String send(List<String> mobileNos, String content) {
        return null;
    }

    @Override
    public String send(List<String> mobileNos, String templateCode, Map<String, String> templateParams) {
        return null;
    }

    @Override
    public SmsProvider getProvider() {
        return SmsProvider.MaiDao;
    }
}

package com.acooly.module.smsend.sender.impl;

import com.acooly.module.smsend.common.enums.SmsProvider;
import com.acooly.module.smsend.sender.dto.SmsResult;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

/**
 * 容联.云通讯短信接口
 *
 * @author shuijing
 * @link http://www.yuntongxun.com/doc/rest/sms/3_2_1_1.html
 * <p>只支持模板和签名为短信内容 发送接口send(String mobileNo, String content) content内容需为json格式 见测试用例： @See
 * com.acooly.core.test.web.TestController#testCloopenSms()
 */
@Slf4j
//@Component
public class CloopenMessageSender extends AbstractShortMessageSender {

    private final String SEND_URL_CLO = "https://app.cloopen.com:8883/";

    private final String ENCODING = "UTF-8";

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
        return SmsProvider.Cloopen;
    }
}

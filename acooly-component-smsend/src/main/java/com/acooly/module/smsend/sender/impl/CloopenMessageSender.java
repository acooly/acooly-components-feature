package com.acooly.module.smsend.sender.impl;

import com.acooly.module.smsend.enums.SmsProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 容联.云通讯短信接口
 *
 * @author shuijing
 * @link http://www.yuntongxun.com/doc/rest/sms/3_2_1_1.html
 * <p>只支持模板和签名为短信内容 发送接口send(String mobileNo, String content) content内容需为json格式 见测试用例： @See
 * com.acooly.core.test.web.TestController#testCloopenSms()
 */
@Slf4j
@Component
public class CloopenMessageSender extends AbstractShortMessageSender {

    private final String SEND_URL_CLO = "https://app.cloopen.com:8883/";

    private final String ENCODING = "UTF-8";

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
        return SmsProvider.Cloopen;
    }
}

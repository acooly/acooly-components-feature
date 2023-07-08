/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-04 01:17
 */
package com.acooly.module.test.smsend;

import com.acooly.module.smsend.SmsSendProperties;
import com.acooly.module.smsend.common.enums.SmsProvider;
import com.acooly.module.smsend.sender.ShortMessageSender;
import com.acooly.module.smsend.sender.impl.AliyunMessageSender;
import com.acooly.module.smsend.sender.impl.AnyCmpMessageSender;
import com.acooly.module.smsend.sender.impl.HuaweiMessageSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.junit.Test;

import java.util.Map;

/**
 * @author zhangpu
 * @date 2020-05-04 01:17
 */
@Slf4j
public class ShortMessageSenderTest {

    @Test
    public void testHuaweiyunSend() {
        Map<String, String> smsParam = new ListOrderedMap<String, String>();
        smsParam.put("captcha", "121312");
        smsParam.put("effectiveMinute", "10");
        getHuaweiyunSender().sendTemplate("13677897654", "abcdefghabcdefghabcdefghabcdefgh", smsParam);
    }

    @Test
    public void testAliyunSend() {
        Map<String, String> smsParam = new ListOrderedMap<String, String>();
        smsParam.put("captcha", "121312");
        smsParam.put("effectiveMinute", "10");
        getAliyunSender().sendTemplate("13896177630", "SMS_187930527", smsParam);
    }

    protected ShortMessageSender getHuaweiyunSender() {
        SmsSendProperties properties = new SmsSendProperties();
        properties.getProviders().put(SmsProvider.Huaweiyun, getHuaweiyunProviderInfo());
        HuaweiMessageSender messageSender = new HuaweiMessageSender();
        messageSender.setProperties(properties);
        return messageSender;
    }

    @Test
    public void testAnyCmpSend() {
        Map<String, String> smsParam = new ListOrderedMap<String, String>();
        smsParam.put("captcha", "121312");
        smsParam.put("effectiveMinute", "10");
        getAnyCmpSender().sendTemplate("13896177630", "SMS_189521938", smsParam);
    }

    protected ShortMessageSender getAliyunSender() {
        SmsSendProperties properties = new SmsSendProperties();
        properties.getProviders().put(SmsProvider.Aliyun, getAliyunProviderInfo());
        AliyunMessageSender messageSender = new AliyunMessageSender();
        messageSender.setProperties(properties);
        return messageSender;
    }

    protected ShortMessageSender getAnyCmpSender() {
        SmsSendProperties properties = new SmsSendProperties();
        properties.getProviders().put(SmsProvider.AnyCmp, getAnyCmpProviderInfo());
        AnyCmpMessageSender messageSender = new AnyCmpMessageSender();
        messageSender.setProperties(properties);
        return messageSender;
    }

    protected SmsSendProperties.SmsProviderInfo getHuaweiyunProviderInfo() {
        SmsSendProperties.SmsProviderInfo providerInfo = new SmsSendProperties.SmsProviderInfo();
        providerInfo.setProvider(SmsProvider.Huaweiyun);
        providerInfo.setAppId("youcheyun");
        providerInfo.setAccessKey("LTAI4Fdxf1Ch1Xk3sD6ocxRN");
        providerInfo.setSecretKey("TxWZhhFO9XdPHHutRdl7yaeanScGlM");
        providerInfo.setContentSign("新希望");
        providerInfo.getExt().put("regionId", "cn-north-4");
        providerInfo.getExt().put("sender", "csms12345678");
        return providerInfo;
    }


    protected SmsSendProperties.SmsProviderInfo getAliyunProviderInfo() {
        SmsSendProperties.SmsProviderInfo providerInfo = new SmsSendProperties.SmsProviderInfo();
        providerInfo.setProvider(SmsProvider.Aliyun);
        providerInfo.setAppId("newseed");
        providerInfo.setAccessKey("LTAI4Fdxf1Ch1Xk3sD6ocxRN");
        providerInfo.setSecretKey("TxWZhhFO9XdPHHutRdl7yaeanScGlM");
        providerInfo.setContentSign("新希望服务");
        providerInfo.getExt().put("topicName", "sms.topic-cn-hangzhou");
        return providerInfo;
    }

    protected SmsSendProperties.SmsProviderInfo getAnyCmpProviderInfo() {
        SmsSendProperties.SmsProviderInfo providerInfo = new SmsSendProperties.SmsProviderInfo();
        providerInfo.setProvider(SmsProvider.AnyCmp);
        providerInfo.setAppId("cnvex");
        providerInfo.setAccessKey("52AQCwgE+tsFd6C4N4qJ6Q==");
        providerInfo.setSecretKey("t2BRy849lMKWo5JDz8hCcA==");
        providerInfo.setContentSign("汽摩交易所");
        return providerInfo;
    }

}

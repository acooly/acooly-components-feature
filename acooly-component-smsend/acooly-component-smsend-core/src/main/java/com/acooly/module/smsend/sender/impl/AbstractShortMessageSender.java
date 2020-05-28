/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-24 22:30 创建
 */
package com.acooly.module.smsend.sender.impl;

import com.acooly.core.utils.Strings;
import com.acooly.module.smsend.SmsSendProperties;
import com.acooly.module.smsend.common.enums.SmsSendResultCode;
import com.acooly.module.smsend.sender.ShortMessageSender;
import com.acooly.module.smsend.sender.dto.SmsResult;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 短信发送器 抽象实现
 *
 * @author zhangpu@acooly.cn
 * @date 2020-04-12
 */
public abstract class AbstractShortMessageSender implements ShortMessageSender {
    @Autowired
    protected SmsSendProperties properties;

    protected SmsSendProperties.SmsProviderInfo getProviderInfo() {
        return properties.getProviders().get(getProvider());
    }

    public void setProperties(SmsSendProperties properties) {
        this.properties = properties;
    }

    protected void handleSmsResult(SmsResult result) {
        if (result.isSuccess()) {
            if (Strings.isBlank(result.getCode()) && Strings.isBlank(result.getMessage())) {
                result.setCode(SmsSendResultCode.SUCCESS.code());
                result.setMessage(SmsSendResultCode.SUCCESS.message());
            }
        }
    }
}

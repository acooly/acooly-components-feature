/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-24 22:30 创建
 */
package com.acooly.module.smsend.sender.impl;

import com.acooly.module.smsend.SmsendProperties;
import com.acooly.module.smsend.sender.ShortMessageSender;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 短信发送器 抽象实现
 *
 * @author zhangpu@acooly.cn
 * @date 2020-04-12
 */
public abstract class AbstractShortMessageSender implements ShortMessageSender {
    @Autowired
    protected SmsendProperties properties;

    protected SmsendProperties.SmsProviderInfo getProviderInfo() {
        return properties.getProviders().get(getProvider());
    }

    public void setProperties(SmsendProperties properties) {
        this.properties = properties;
    }
}

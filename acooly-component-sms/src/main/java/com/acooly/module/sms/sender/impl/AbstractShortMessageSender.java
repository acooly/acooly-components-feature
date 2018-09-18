/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-24 22:30 创建
 */
package com.acooly.module.sms.sender.impl;

import com.acooly.module.sms.SmsProperties;
import com.acooly.module.sms.sender.ShortMessageSender;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author qiubo@yiji.com
 */
public abstract class AbstractShortMessageSender implements ShortMessageSender, InitializingBean {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected String service;
    protected String username;
    protected String password;
    protected String batchUser;
    protected String batchPswd;
    protected String posfix;
    protected String prefix;
    protected int timeout;

    @Autowired
    private SmsProperties smsProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        service = smsProperties.getUrl();
        username = smsProperties.getUsername();
        password = smsProperties.getPassword();
        batchUser = smsProperties.getBatchUser();
        batchPswd = smsProperties.getBatchPswd();
        posfix = smsProperties.getPosfix();
        prefix = smsProperties.getPrefix();
        timeout = smsProperties.getTimeout();
    }

    protected String getContent(String content) {
        return StringUtils.trimToEmpty(prefix) + content + " " + StringUtils.trimToEmpty(posfix);
    }
}

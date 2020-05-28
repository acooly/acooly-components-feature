/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-16 17:04 创建
 */
package com.acooly.module.smsend;

import com.acooly.core.common.boot.component.ComponentInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author qiubo@yiji.com
 */
public class SmsSendComponentInitializer implements ComponentInitializer {
    private static final Logger logger = LoggerFactory.getLogger(SmsSendComponentInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // 设置Appservice对本组件的包烧苗路径
        setPropertyIfMissing("acooly.appserver.scan-packages.smsend", "com.acooly.module.smsend.service");
        setPropertyIfMissing("acooly.appserver.scanPackages.smsend", "com.acooly.module.smsend.service");
    }
}

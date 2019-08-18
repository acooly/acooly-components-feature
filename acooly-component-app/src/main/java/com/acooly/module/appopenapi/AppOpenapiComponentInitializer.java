/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-25 22:18 创建
 */
package com.acooly.module.appopenapi;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.common.boot.component.ComponentInitializer;
import com.acooly.openapi.framework.core.OpenAPIProperties;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * @author qiubo@yiji.com
 */
public class AppOpenapiComponentInitializer implements ComponentInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        OpenAPIProperties openAPIProperties = Apps.buildProperties(OpenAPIProperties.class);
        if (openAPIProperties.getAnonymous().isEnable()) {
            setPropertyIfMissing("acooly.openapi.anonymous.permissions.appopenapi", "*:bannerList,*:appLatestVersion,*:appCrashReport,*:welcomeInfo");
        }
    }
}

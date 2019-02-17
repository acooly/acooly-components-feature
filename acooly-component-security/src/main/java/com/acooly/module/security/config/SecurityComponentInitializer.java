/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-03-15 18:44 创建
 */
package com.acooly.module.security.config;

import com.acooly.core.common.boot.EnvironmentHolder;
import com.acooly.core.common.boot.component.ComponentInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author qiubo@yiji.com
 */
public class SecurityComponentInitializer implements ComponentInitializer {
    public static final String DUBBO_CUMSTOM_CONFIG_PACKAGE = "acooly.dubbo.customPackagesToScan.sso";

    public static final String DUBBO_SSO_CONFIG_PACKAGE = "com.acooly.module.security.service";

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        setPropertyIfMissing(
                "acooly.ds.Checker.excludedColumnTables.security", "SYS_ROLE_RESC, SYS_USER_ROLE");
        SecurityProperties securityProperties = new SecurityProperties();
        EnvironmentHolder.buildProperties(securityProperties);
        if (securityProperties.isEnableSSOAuthzService()) {
            System.setProperty(DUBBO_CUMSTOM_CONFIG_PACKAGE, DUBBO_SSO_CONFIG_PACKAGE);
            setPropertyIfMissing("acooly.olog.storage.enable", "false");
        }
    }
}

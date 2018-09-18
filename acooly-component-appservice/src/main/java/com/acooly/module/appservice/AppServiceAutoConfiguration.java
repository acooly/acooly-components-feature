/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2016-09-22 16:02 创建
 */
package com.acooly.module.appservice;

import com.acooly.core.common.boot.EnvironmentHolder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.acooly.module.appservice.AppServiceProperties.PREFIX;

/**
 * @author qiubo@yiji.com
 */
@Configuration
@EnableConfigurationProperties({AppServiceProperties.class})
@ConditionalOnProperty(value = PREFIX + ".enable", matchIfMissing = true)
public class AppServiceAutoConfiguration {

    @Bean
    public static AppServiceBeanPostProcessor facadeBeanPostProcessor(
            AppServiceProperties appServiceProperties) {
        EnvironmentHolder.buildProperties(appServiceProperties);
        appServiceProperties.check();
        return new AppServiceBeanPostProcessor(appServiceProperties);
    }
}

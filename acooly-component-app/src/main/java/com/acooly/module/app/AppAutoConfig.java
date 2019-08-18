/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * kuli@yiji.com 2017-02-14 17:04 创建
 */
package com.acooly.module.app;

import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
import com.acooly.module.app.notify.AppNotifyService;
import com.acooly.module.app.notify.jpush.JPushAppNotifyServiceImpl;
import com.acooly.module.security.config.SecurityAutoConfig;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.acooly.module.app.AppProperties.PREFIX;

/**
 * @author kuli@yiji.com
 */
@Configuration
@EnableConfigurationProperties({AppProperties.class})
@ConditionalOnProperty(value = PREFIX + ".enable", matchIfMissing = true)
@ComponentScan(basePackages = "com.acooly.module.app")
@AutoConfigureAfter(SecurityAutoConfig.class)
public class AppAutoConfig {
    @Autowired
    private AppProperties appProperties;

    @Bean
    public StandardDatabaseScriptIniter appScriptIniter() {
        return new StandardDatabaseScriptIniter() {
            @Override
            public String getEvaluateTable() {
                return "app_banner";
            }

            @Override
            public String getComponentName() {
                return "app";
            }

            @Override
            public List<String> getInitSqlFile() {
                return Lists.newArrayList("app", "app_urls");
            }
        };
    }
    @Bean
    @ConditionalOnMissingBean
    public AppNotifyService jPushAppNotifyService(){
        return new JPushAppNotifyServiceImpl();
    }
}

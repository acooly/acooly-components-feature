/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-24 21:58 创建
 */
package com.acooly.module.smsend;

import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
import com.acooly.module.security.config.SecurityAutoConfig;
import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.acooly.module.smsend.SmsendProperties.PREFIX;


/**
 * @author qiubo@yiji.com
 */
@Configuration
@EnableConfigurationProperties({SmsendProperties.class})
@ConditionalOnProperty(value = PREFIX + ".enable", matchIfMissing = true)
@AutoConfigureAfter(SecurityAutoConfig.class)
public class SmsAutoConfig {
    @Bean
    public StandardDatabaseScriptIniter smsScriptIniter() {
        return new StandardDatabaseScriptIniter() {
            @Override
            public String getEvaluateTable() {
                return "sms_log";
            }

            @Override
            public String getComponentName() {
                return "sms";
            }

            @Override
            public List<String> getInitSqlFile() {
                return Lists.newArrayList("sms", "sms_urls");
            }
        };
    }
}

/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-24 21:58 创建
 */
package com.acooly.module.smsend.analysis;

import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
import com.acooly.module.smsend.SmsSendAutoConfig;
import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

import static com.acooly.module.smsend.SmsSendProperties.PREFIX;


/**
 * @author zhangpu@acooly.cn
 */
@Configuration
@EnableConfigurationProperties({SmsSendAnalysisProperties.class})
@ConditionalOnProperty(value = PREFIX + ".enable", matchIfMissing = true)
@AutoConfigureAfter(SmsSendAutoConfig.class)
@ComponentScan(basePackages = "com.acooly.module.smsend.analysis")
public class SmsSendAnalysisAutoConfig {
    @Bean
    @Profile("!online")
    public StandardDatabaseScriptIniter smsendAnalysisScriptIniter() {
        return new StandardDatabaseScriptIniter() {
            @Override
            public String getEvaluateTable() {
                return "sms_send_day";
            }

            @Override
            public String getComponentName() {
                return "smsendAnalysis";
            }

            @Override
            public List<String> getInitSqlFile() {
                return Lists.newArrayList("smsend_analysis", "smsend_analysis_urls");
            }
        };
    }



}

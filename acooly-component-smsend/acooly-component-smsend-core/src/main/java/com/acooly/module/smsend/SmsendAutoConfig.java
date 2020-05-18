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
import com.acooly.module.smsend.facade.api.SmsSendRemoteService;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.List;

import static com.acooly.module.smsend.SmsSendProperties.PREFIX;


/**
 * @author zhangpu@acooly.cn
 */
@Configuration
@EnableConfigurationProperties({SmsSendProperties.class})
@ConditionalOnProperty(value = PREFIX + ".enable", matchIfMissing = true)
@AutoConfigureAfter(SecurityAutoConfig.class)
@ComponentScan(basePackages = "com.acooly.module.smsend")
public class SmsendAutoConfig {
    @Bean
    public StandardDatabaseScriptIniter smsendScriptIniter() {
        return new StandardDatabaseScriptIniter() {
            @Override
            public String getEvaluateTable() {
                return "sms_log";
            }

            @Override
            public String getComponentName() {
                return "smsend";
            }

            @Override
            public List<String> getInitSqlFile() {
                return Lists.newArrayList("sms", "sms_urls");
            }
        };
    }

    @Bean
    @ConditionalOnProperty(value = PREFIX + ".enable-facade", matchIfMissing = false, havingValue = "false")
    @ConditionalOnBean(ProtocolConfig.class)
    @DependsOn({"applicationConfig", "registryConfig", "protocolConfig"})
    public static ServiceConfig<SmsSendRemoteService> feedbackFacadeConfig(ApplicationConfig applicationConfig, RegistryConfig registryConfig,
                                                                           ProtocolConfig protocolConfig, SmsSendRemoteService smsSendRemoteService) {
        ServiceConfig<SmsSendRemoteService> service = new ServiceConfig<SmsSendRemoteService>();
        service.setApplication(applicationConfig);
        service.setRegistry(registryConfig);
        service.setProtocol(protocolConfig);
        service.setInterface(SmsSendRemoteService.class);
        service.setRef(smsSendRemoteService);
        service.setVersion("1.0");
        service.export();
        return service;
    }

}

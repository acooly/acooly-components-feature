/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-24 21:58 创建
 */
package com.acooly.module.smsend.facade;

import com.acooly.module.smsend.facade.client.SmsSendClientService;
import com.acooly.module.smsend.facade.client.impl.DubboSmsSendClientServiceImpl;
import com.acooly.module.smsend.facade.client.impl.MockSmsSendClientServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

import static com.acooly.module.smsend.facade.SmsSendClientProperties.PREFIX;


/**
 * @author zhangpu@acooly.cn
 */
@Configuration
@EnableConfigurationProperties({SmsSendClientProperties.class})
@ConditionalOnProperty(value = PREFIX + ".enable", matchIfMissing = true)
public class SmsSendClientAutoConfig {

    @Resource
    private SmsSendClientProperties properties;

    @Bean
    public SmsSendClientService smsSendClientService() {
        if (properties.getType() == SmsSendClientProperties.Type.dubbo) {
            return new DubboSmsSendClientServiceImpl();
        } else {
            return new MockSmsSendClientServiceImpl();
        }
    }

}

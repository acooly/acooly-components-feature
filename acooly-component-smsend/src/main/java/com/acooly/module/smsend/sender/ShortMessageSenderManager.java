/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-04-12 20:30
 */
package com.acooly.module.smsend.sender;

import com.acooly.module.smsend.SmsSendProperties;
import com.acooly.module.smsend.enums.SmsProvider;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author zhangpu
 * @date 2020-04-12 20:30
 */
@Slf4j
@Component
public class ShortMessageSenderManager implements InitializingBean {

    @Autowired
    private SmsSendProperties properties;

    @Autowired
    private ApplicationContext applicationContext;

    private List<ShortMessageSender> senders = Lists.newLinkedList();

    public void registry(ShortMessageSender shortMessageSender) {
        senders.add(shortMessageSender);
        log.info("短信发送组件 registed ShortMessageSender: {}",shortMessageSender.getProvider().code());
    }

    public List<ShortMessageSender> getAllSender() {
        return senders;
    }

    public SmsSendProperties.SmsProviderInfo getProviderInfo(SmsProvider smsProvider){
        return properties.getProviders().get(smsProvider);
    }

    public ShortMessageSender getSender(SmsProvider provider){
        for (ShortMessageSender sender:senders){
            if(sender.getProvider() == provider){
                return sender;
            }
        }
        return null;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Map<SmsProvider, SmsSendProperties.SmsProviderInfo> providers = properties.getProviders();
        if (providers == null || providers.size() == 0) {
            log.warn("短信发送组件 为配置可用的提供商。");
            return;
        }
        Map<String, ShortMessageSender> beans = applicationContext.getBeansOfType(ShortMessageSender.class);
        for (SmsProvider smsProvider : providers.keySet()) {
            ShortMessageSender shortMessageSender = getByProvider(beans, smsProvider);
            if (shortMessageSender != null) {
                registry(shortMessageSender);
            }
        }
    }

    protected ShortMessageSender getByProvider(Map<String, ShortMessageSender> beans, SmsProvider smsProvider) {
        for (ShortMessageSender shortMessageSender : beans.values()) {
            if (shortMessageSender.getProvider() == smsProvider) {
                return shortMessageSender;
            }
        }
        return null;
    }
}

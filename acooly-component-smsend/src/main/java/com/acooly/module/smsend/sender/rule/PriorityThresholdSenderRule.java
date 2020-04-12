/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-04-12 20:29
 */
package com.acooly.module.smsend.sender.rule;

import com.acooly.module.smsend.SmsendProperties;
import com.acooly.module.smsend.enums.SmsendResultCode;
import com.acooly.module.smsend.sender.ShortMessageSendException;
import com.acooly.module.smsend.sender.ShortMessageSender;
import com.acooly.module.smsend.sender.ShortMessageSenderManager;
import com.acooly.module.smsend.sender.SmsendCounter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhangpu
 * @date 2020-04-12 20:29
 */
@Slf4j
@Component
public class PriorityThresholdSenderRule implements MessageSenderRule {

    @Autowired
    private SmsendCounter smsendCounter;

    @Autowired
    private ShortMessageSenderManager shortMessageSenderManager;

    @Override
    public ShortMessageSender choose(String key) {
        // 按配置文件的注册优先级
        List<ShortMessageSender> senders = shortMessageSenderManager.getAllSender();
        for (ShortMessageSender sender : senders) {
            SmsendProperties.SmsProviderInfo providerInfo = shortMessageSenderManager.getProviderInfo(sender.getProvider());
            int max = providerInfo.getMaxCountOfDay();
            if (smsendCounter.getSendCount(key, sender.getProvider()) >= max) {
                log.warn("短信发送 超过渠道日限额 mobileNo:{}, provider:{}, maxCountOfDay: {}", key, sender.getProvider().code(), max);
                continue;
            } else {
                return sender;
            }
        }

        log.warn("短信发送 所有渠道发送配额用完 mobileNo:{}", key);
        throw new ShortMessageSendException(SmsendResultCode.all_provider_quota_full);
    }
}

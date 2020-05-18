/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-04 00:01
 */
package com.acooly.module.smsend.sender.rule;

import com.acooly.module.smsend.SmsSendProperties;
import com.acooly.module.smsend.common.enums.SmsSendResultCode;
import com.acooly.module.smsend.common.exception.ShortMessageSendException;
import com.acooly.module.smsend.sender.ShortMessageSender;
import com.acooly.module.smsend.sender.ShortMessageSenderManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 重试策略规则
 *
 * @author zhangpu
 * @date 2020-05-04 00:01
 */
@Slf4j
public class RetryMessageSenderRule implements MessageSenderRule {

    @Autowired
    private ShortMessageSenderManager shortMessageSenderManager;

    @Override
    public ShortMessageSender choose(String key) {
        // 按配置文件的注册优先级
        List<ShortMessageSender> senders = shortMessageSenderManager.getAllSender();
        for (ShortMessageSender sender : senders) {
            SmsSendProperties.SmsProviderInfo providerInfo = shortMessageSenderManager.getProviderInfo(sender.getProvider());
        }

        log.warn("短信发送 所有渠道发送配额用完 mobileNo:{}", key);
        throw new ShortMessageSendException(SmsSendResultCode.ALL_PROVIDER_QUOTA_FULL);
    }
}

/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-04-12 20:26
 */
package com.acooly.module.smsend.sender.rule;

import com.acooly.module.smsend.sender.ShortMessageSender;

/**
 * @author zhangpu
 * @date 2020-04-12 20:26
 */
public interface MessageSenderRule {

    /**
     * 选择发送实现
     *
     * @param key
     * @return
     */
    ShortMessageSender choose(String key);
}

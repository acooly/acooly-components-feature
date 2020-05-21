/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-21 19:42
 */
package com.acooly.module.smsend.facade.client.impl;

import com.acooly.module.smsend.facade.api.SmsSendRemoteService;
import com.acooly.module.smsend.facade.client.SmsSendClientService;
import com.acooly.module.smsend.facade.order.SmsSendOrder;
import com.acooly.module.smsend.facade.result.SmsSendResult;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;

/**
 * 短信发送 dubbo远程实现
 *
 * @author zhangpu
 * @date 2020-05-21 19:42
 */
@Slf4j
public class DubboSmsSendClientServiceImpl implements SmsSendClientService {

    @Reference(version = "1.0")
    private SmsSendRemoteService smsSendRemoteService;

    @Override
    public SmsSendResult send(SmsSendOrder smsSendOrder) {
        return smsSendRemoteService.send(smsSendOrder);
    }
}

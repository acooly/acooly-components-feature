/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-18 12:15
 */
package com.acooly.module.smsend.service.impl;

import com.acooly.core.utils.mapper.BeanCopier;
import com.acooly.module.appservice.AppService;
import com.acooly.module.smsend.common.dto.SenderInfo;
import com.acooly.module.smsend.facade.api.SmsSendRemoteService;
import com.acooly.module.smsend.facade.order.SmsSendOrder;
import com.acooly.module.smsend.facade.result.SmsSendResult;
import com.acooly.module.smsend.service.SmsSendService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangpu
 * @date 2020-05-18 12:15
 */
@Slf4j
public class SmsSendRemoteServiceImpl implements SmsSendRemoteService {

    private SmsSendService smsSendService;

    @Override
    @AppService
    public SmsSendResult send(SmsSendOrder smsSendOrder) {
        SenderInfo senderInfo = BeanCopier.copy(smsSendOrder, SenderInfo.class);
        smsSendService.send(senderInfo);
        SmsSendResult result = new SmsSendResult();
        result.setAppId(smsSendOrder.getAppId());
        return result;
    }
}

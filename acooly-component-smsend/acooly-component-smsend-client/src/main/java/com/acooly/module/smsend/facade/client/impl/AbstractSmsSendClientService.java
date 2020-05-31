/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-31 12:23
 */
package com.acooly.module.smsend.facade.client.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.exception.CommonErrorCodes;
import com.acooly.core.common.exception.OrderCheckException;
import com.acooly.core.utils.Ids;
import com.acooly.core.utils.Strings;
import com.acooly.module.smsend.facade.client.SmsSendClientService;
import com.acooly.module.smsend.facade.order.SmsSendOrder;
import com.acooly.module.smsend.facade.result.SmsSendResult;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangpu
 * @date 2020-05-31 12:23
 */
@Slf4j
public abstract class AbstractSmsSendClientService implements SmsSendClientService {

    @Override
    public SmsSendResult send(SmsSendOrder smsSendOrder) {
        try {
            doCheck(smsSendOrder);
        } catch (BusinessException be) {
            SmsSendResult result = new SmsSendResult();
            result.setStatus(be);
            return result;
        }
        return doSend(smsSendOrder);
    }

    protected void doCheck(SmsSendOrder smsSendOrder) {
        if (Strings.isBlank(smsSendOrder.getGid())) {
            smsSendOrder.setGid(Ids.gid());
        }
        if (Strings.isBlank(smsSendOrder.getPartnerId())) {
            smsSendOrder.setPartnerId(smsSendOrder.getAppId());
        }
        try {
            smsSendOrder.check();
        } catch (OrderCheckException oce) {
            throw new BusinessException(CommonErrorCodes.PARAMETER_ERROR, oce.getMessage());
        }
    }

    protected abstract SmsSendResult doSend(SmsSendOrder smsSendOrder);
}

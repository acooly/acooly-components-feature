/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-21 19:39
 */
package com.acooly.module.smsend.facade.client.impl;

import com.acooly.module.smsend.common.enums.SmsSendResultCode;
import com.acooly.module.smsend.facade.order.SmsSendOrder;
import com.acooly.module.smsend.facade.result.SmsSendResult;
import lombok.extern.slf4j.Slf4j;

/**
 * 短信发送MOCK实现
 * <p>
 * 用于开发联调阶段
 *
 * @author zhangpu
 * @date 2020-05-21 19:39
 */
@Slf4j
public class MockSmsSendClientServiceImpl extends AbstractSmsSendClientService {

    @Override
    protected SmsSendResult doSend(SmsSendOrder smsSendOrder) {
        log.info("短信客户端 MOCK. templateCode:{}, templateParams:{}, order:{}", smsSendOrder.getTemplateCode(),
                smsSendOrder.getTemplateParams(), smsSendOrder);
        return new SmsSendResult(SmsSendResultCode.SUCCESS);
    }

}

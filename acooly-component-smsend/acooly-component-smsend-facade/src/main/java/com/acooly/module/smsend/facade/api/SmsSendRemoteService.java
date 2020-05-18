/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-13 12:52
 */
package com.acooly.module.smsend.facade.api;

import com.acooly.module.smsend.facade.order.SmsSendOrder;
import com.acooly.module.smsend.facade.result.SmsSendResult;

/**
 * @author zhangpu
 * @date 2020-05-13 12:52
 */
public interface SmsSendRemoteService {

    /**
     * 短信发送
     *
     * @param smsSendOrder
     * @return
     */
    SmsSendResult send(SmsSendOrder smsSendOrder);

}

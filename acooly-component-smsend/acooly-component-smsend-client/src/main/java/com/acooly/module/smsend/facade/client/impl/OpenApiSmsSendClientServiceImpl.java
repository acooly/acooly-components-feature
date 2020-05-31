/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-21 19:42
 */
package com.acooly.module.smsend.facade.client.impl;

import com.acooly.core.utils.mapper.BeanCopier;
import com.acooly.module.smsend.facade.openapi.SmsSendApiRequest;
import com.acooly.module.smsend.facade.openapi.SmsSendApiResponse;
import com.acooly.module.smsend.facade.order.SmsSendOrder;
import com.acooly.module.smsend.facade.result.SmsSendResult;
import com.acooly.openapi.framework.common.OpenApiTools;
import lombok.extern.slf4j.Slf4j;

/**
 * 短信发送 dubbo远程实现
 *
 * @author zhangpu
 * @date 2020-05-21 19:42
 */
@Slf4j
public class OpenApiSmsSendClientServiceImpl extends AbstractSmsSendClientService {

    private OpenApiTools openApiTools;

    public OpenApiSmsSendClientServiceImpl(OpenApiTools openApiTools) {
        this.openApiTools = openApiTools;
    }

    @Override
    protected SmsSendResult doSend(SmsSendOrder smsSendOrder) {
        SmsSendApiRequest request = BeanCopier.copy(smsSendOrder, SmsSendApiRequest.class);
        SmsSendApiResponse response = openApiTools.send(request, SmsSendApiResponse.class);
        SmsSendResult result = new SmsSendResult();
        result.setCode(response.getCode());
        result.setDetail(response.getDetail());
        return result;
    }
}

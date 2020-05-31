/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-30 17:54
 */
package com.acooly.module.smsend.openapi;

import com.acooly.core.utils.mapper.BeanCopier;
import com.acooly.module.smsend.facade.api.SmsSendRemoteService;
import com.acooly.module.smsend.facade.openapi.SmsSendApiRequest;
import com.acooly.module.smsend.facade.openapi.SmsSendApiResponse;
import com.acooly.module.smsend.facade.order.SmsSendOrder;
import com.acooly.module.smsend.facade.result.SmsSendResult;
import com.acooly.openapi.framework.common.ApiConstants;
import com.acooly.openapi.framework.common.annotation.ApiDocNote;
import com.acooly.openapi.framework.common.annotation.ApiDocType;
import com.acooly.openapi.framework.common.annotation.OpenApiService;
import com.acooly.openapi.framework.common.enums.ApiBusiType;
import com.acooly.openapi.framework.common.enums.ResponseType;
import com.acooly.openapi.framework.core.service.base.BaseApiService;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangpu
 * @date 2020-05-30 17:54
 */
@Slf4j
@ApiDocType(code = ApiConstants.BUILDIN_APIDOC_CODE, name = ApiConstants.BUILDIN_APIDOC_NAME)
@ApiDocNote("通用短信发送，主要用于体系内部的夸内网场景使用。提供认证方式的公网短信服务发送能力")
@OpenApiService(name = "smsSend", desc = "短信发送", responseType = ResponseType.SYN, owner = "zhangpu", busiType = ApiBusiType.Trade)
public class SmsSendApiService extends BaseApiService<SmsSendApiRequest, SmsSendApiResponse> {

    @Reference(version = "1.0")
    private SmsSendRemoteService smsSendRemoteService;

    @Override
    protected void doService(SmsSendApiRequest request, SmsSendApiResponse response) {
        SmsSendOrder order = new SmsSendOrder();
        order.setPartnerId(request.getPartnerId());
        order.setGid(getGid());
        BeanCopier.copy(request, order);
        SmsSendResult result = smsSendRemoteService.send(order);
        result.throwIfFailure();
        result.copyTo(response);
        response.setMessage(result.message());
    }
}
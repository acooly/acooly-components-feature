/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-17 21:08
 */
package com.acooly.module.smsend.web;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.module.smsend.common.dto.SenderInfo;
import com.acooly.module.smsend.facade.api.SmsSendRemoteService;
import com.acooly.module.smsend.facade.order.SmsSendOrder;
import com.acooly.module.smsend.service.SmsSendService;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zhangpu
 * @date 2020-05-17 21:08
 */
@Slf4j
@RestController
@RequestMapping("/smssend/")
public class SmsSenderTestController {

    @Autowired
    private SmsSendService smsSendService;

    @Reference(version = "1.0")
    private SmsSendRemoteService smsSendRemoteService;

    @RequestMapping("send")
    public Object send() {
        JsonResult result = new JsonResult();
        try {
            Map<String, String> smsParam = new ListOrderedMap<String, String>();
            smsParam.put("captcha", "121312");
            smsParam.put("effectiveMinute", "10");
            SenderInfo senderInfo = new SenderInfo("test", "13896177630", "SMS_187930527", smsParam, "", "");
            smsSendService.send(senderInfo);
        } catch (BusinessException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping("sendFacade")
    public Object sendFacade() {
        Map<String, String> smsParam = new ListOrderedMap<String, String>();
        smsParam.put("captcha", "121312");
        SmsSendOrder smsSendOrder = new SmsSendOrder("test", "13896177630", "SMS_187930527", smsParam);
        return smsSendRemoteService.send(smsSendOrder);
    }

}

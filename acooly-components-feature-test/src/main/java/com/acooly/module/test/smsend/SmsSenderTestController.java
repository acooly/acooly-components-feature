/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-17 21:08
 */
package com.acooly.module.test.smsend;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Ids;
import com.acooly.core.utils.system.IPUtil;
import com.acooly.module.smsend.common.dto.SenderInfo;
import com.acooly.module.smsend.facade.api.SmsSendRemoteService;
import com.acooly.module.smsend.facade.client.SmsSendClientService;
import com.acooly.module.smsend.facade.order.SmsSendOrder;
import com.acooly.module.smsend.service.SmsSendService;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private SmsSendClientService smsSendClientService;

    @RequestMapping("facade/sendTemplate")
    public Object facadeSendTemplate(HttpServletRequest request) {
        // 短信服务统一分配的发送应用的Id
        String appId = "youcheyun";
        // 短信服务统一配置的模板编码，需要对需要的多个渠道配置对应的绑定渠道模板ID
        String templateCode = "youcheyun_register";
        // 如果渠道需预先注册签名，则请先在渠道方申请签名；如果不传入，则表示使用短信服务的渠道默认配置，建议都传入
        String contentSign = null; //""有车云";
        // 客户请求IP，如果需要IP流控，则传入，短信服务默认配置为：100/IP/分钟
        String clientIp = IPUtil.getIpAddr(request);
        Map<String, String> smsParam = new ListOrderedMap<String, String>();
        smsParam.put("captcha", "121312");
        smsParam.put("effectiveMinute", "10");
        SmsSendOrder smsSendOrder = new SmsSendOrder(appId, "18996283138", templateCode, smsParam, contentSign, clientIp);
        String gid = Ids.gid();
        smsSendOrder.setGid(gid);
        smsSendOrder.setPartnerId(appId);
        return smsSendClientService.send(smsSendOrder);
    }


    @RequestMapping("facade/sendTemplateSync")
    public Object facadeSendAsync() {
        String appId = "youcheyun";
        Map<String, String> smsParam = new ListOrderedMap<String, String>();
        smsParam.put("captcha", "121312");
        smsParam.put("effectiveMinute", "10");
        SmsSendOrder smsSendOrder = new SmsSendOrder(appId, "13896177630", "youcheyun_register", smsParam);
        smsSendOrder.setAsync(false);
        return smsSendRemoteService.send(smsSendOrder);
    }


    /**
     * 内部服务方式调用
     *
     * @return
     */
    @RequestMapping("send")
    public Object send() {
        JsonResult result = new JsonResult();
        try {
            Map<String, String> smsParam = new ListOrderedMap<String, String>();
            smsParam.put("captcha", "121312");
            smsParam.put("effectiveMinute", "10");
            SenderInfo senderInfo = new SenderInfo("200519151525CB2D0001", "13896177630", "youcheyun_register", smsParam, "", "");
            senderInfo.setAsync(false);
            smsSendService.send(senderInfo);
        } catch (BusinessException e) {
            result.setCode(e.getCode());
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }


}

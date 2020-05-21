/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-21 19:37
 */
package com.acooly.module.smsend.facade.client;

import com.acooly.module.smsend.facade.order.SmsSendOrder;
import com.acooly.module.smsend.facade.result.SmsSendResult;

import java.util.Map;

/**
 * 短信发送客户端
 *
 * @author zhangpu
 * @date 2020-05-21 19:37
 */
public interface SmsSendClientService {

    /**
     * 通用短信发送
     *
     * @param smsSendOrder
     * @return
     */
    SmsSendResult send(SmsSendOrder smsSendOrder);


    /**
     * 异步发送
     *
     * @param appId
     * @param mobileNo
     * @param templateCode
     * @param templateParams
     * @param contentSign
     * @param clientIp
     * @return
     */
    default SmsSendResult sendAsync(String appId, String mobileNo, String templateCode, Map<String, String> templateParams, String contentSign, String clientIp) {
        SmsSendOrder order = new SmsSendOrder(appId, mobileNo, templateCode, templateParams, contentSign, clientIp);
        return send(order);
    }

    default SmsSendResult sendAsync(String appId, String mobileNo, String templateCode, Map<String, String> templateParams, String contentSign) {
        return sendAsync(appId, mobileNo, templateCode, templateParams, contentSign, null);
    }

    default SmsSendResult sendAsync(String appId, String mobileNo, String templateCode, Map<String, String> templateParams) {
        return sendAsync(appId, mobileNo, templateCode, templateParams, null, null);
    }

    default SmsSendResult send(String appId, String mobileNo, String templateCode, Map<String, String> templateParams, String contentSign, String clientIp) {
        SmsSendOrder order = new SmsSendOrder(appId, mobileNo, templateCode, templateParams, contentSign, clientIp);
        order.setAsync(false);
        return send(order);
    }

    default SmsSendResult send(String appId, String mobileNo, String templateCode, Map<String, String> templateParams, String contentSign) {
        return send(appId, mobileNo, templateCode, templateParams, contentSign, null);
    }

    default SmsSendResult send(String appId, String mobileNo, String templateCode, Map<String, String> templateParams) {
        return send(appId, mobileNo, templateCode, templateParams, null, null);
    }

}

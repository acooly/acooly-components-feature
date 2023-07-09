/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2023-07-06 08:53
 */
package com.acooly.module.smsend.sender.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Strings;
import com.acooly.module.smsend.SmsSendProperties;
import com.acooly.module.smsend.common.enums.SmsProvider;
import com.acooly.module.smsend.common.enums.SmsSendResultCode;
import com.acooly.module.smsend.sender.dto.SmsResult;
import com.acooly.module.smsend.sender.support.huaweiyun.Constant;
import com.acooly.module.smsend.sender.support.huaweiyun.HuaweiyunResult;
import com.acooly.module.smsend.sender.support.huaweiyun.SSLCipherSuiteUtil;
import com.alibaba.fastjson.JSON;
import com.cloud.apigateway.sdk.utils.Client;
import com.cloud.apigateway.sdk.utils.Request;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

import static org.apache.commons.codec.CharEncoding.UTF_8;

/**
 * 华为短信发送器
 * <p>
 * 具体发送的代码，copy from 华为SDK的烂代码
 *
 * @author zhangpu
 * @date 2023-07-06 08:53
 */
@Slf4j
@Component
public class HuaweiMessageSender extends AbstractShortMessageSender {

    private static final String DEFAULT_REGION_ID = "cn-north-4";
    private static final String SUCCESS = "000000";


    @Override
    public SmsResult send(String mobileNo, String content, String contentSign) {
        return null;
    }

    @Override
    public SmsResult send(Set<String> mobileNos, String content, String contentSign) {
        return null;
    }

    @Override
    public SmsResult sendTemplate(String mobileNo, String templateCode, Map<String, String> templateParams, String contentSign) {
        Set mobileNos = Sets.newLinkedHashSet();
        mobileNos.add(mobileNo);
        return sendTemplate(mobileNos, templateCode, templateParams, contentSign);
    }

    @Override
    public SmsResult sendTemplate(Set<String> mobileNos, String templateCode, Map<String, String> templateParams, String contentSign) {
        return doSendTemplate(mobileNos, templateCode, templateParams, contentSign);
    }

    @Override
    public SmsProvider getProvider() {
        return SmsProvider.Huaweiyun;
    }

    protected SmsResult doSendTemplate(Set<String> mobileNos, String templateCode, Map<String, String> templateParams, String contentSign) {
        SmsSendProperties.SmsProviderInfo providerInfo = properties.getProviders().get(this.getProvider());
        // 获取区域ID
        String regionId = (String) providerInfo.getExt().get("regionId");
        if (Strings.isBlank(regionId)) {
            regionId = DEFAULT_REGION_ID;
        }
        // 网关地址，APP接入地址(在控制台"应用管理"页面获取)+接口访问URI
        String url = "https://smsapi." + regionId + ".myhuaweicloud.com:443/sms/batchSendSms/v1";
        //APP_Key
        String appKey = providerInfo.getAccessKey();
        //APP_Secret
        String appSecret = providerInfo.getSecretKey();
        //短信签名通道号
        String sender = (String) providerInfo.getExt().get("sender");
        if (Strings.isBlank(sender)) {
            sender = "csms12345678";
        }
        //模板ID
        String templateId = templateCode;
        //签名名称
        String signature = Strings.isNoneBlank(contentSign) ? contentSign : providerInfo.getContentSign();
        //必填,全局号码格式(包含国家码),示例:+86151****6789,多个号码之间用英文逗号分隔
        String receiver = getReceivers(mobileNos);
        /**
         * 选填,使用无变量模板时请赋空值 String templateParas = "";
         * 单变量模板示例:模板内容为"您的验证码是${1}"时,templateParas可填写为"[\"369751\"]"
         * 双变量模板示例:模板内容为"您有${1}件快递请到${2}领取"时,templateParas可填写为"[\"3\",\"人民公园正门\"]"
         * 模板中的每个变量都必须赋值，且取值不能为空
         * 查看更多模板和变量规范:产品介绍>模板和变量规范
         */
        String templateParas = templateParams == null || templateParams.isEmpty() ? "" : JSON.toJSONString(templateParams.values());
        CloseableHttpClient client = null;
        SmsResult result = new SmsResult(getProvider());
        try {
            //请求Body,不携带签名名称时,signature请填null
            String body = buildRequestBody(sender, receiver, templateId, templateParas, null, signature);
            Request request = new Request();
            request.setKey(appKey);
            request.setSecret(appSecret);
            request.setMethod("POST");
            request.setUrl(url);
            request.addHeader("Content-Type", "application/x-www-form-urlencoded");
            request.setBody(body);
            log.info("Request Body: {}", body);
            HttpRequestBase signedRequest = Client.sign(request, Constant.SIGNATURE_ALGORITHM_SDK_HMAC_SHA256);
            // 为防止因HTTPS证书认证失败造成API调用失败,需要先忽略证书信任问题
            client = (CloseableHttpClient) SSLCipherSuiteUtil.createHttpClient(Constant.INTERNATIONAL_PROTOCOL);
            HttpResponse response = client.execute(signedRequest);
            HttpEntity respEntity = response.getEntity();
            String respBody = EntityUtils.toString(respEntity, "UTF-8");
            log.info("Response: {} {}", response.getStatusLine().toString(), respBody);
            // 解析JSON字符串
            // {"result":[{"originTo":"+86155****5678","createTime":"2018-05-25T16:34:34Z","from":"1069********0012","smsMsgId":"d6e3cdd0-522b-4692-8304-a07553cdf591_8539659","status":"000000"}],"code":"000000","description":"Success"}
            HuaweiyunResult huaweiyunResult = JSON.parseObject(respBody, HuaweiyunResult.class);
            // 错误处理：只针对总体结果的结果码处理，不针对单个号码的结果码处理。
            boolean sendSuccess = SUCCESS.equalsIgnoreCase(huaweiyunResult.getCode());
            log.info("短信发送 [{}] {},template:{}, params:{}, result: {}", getProvider().code(),
                    sendSuccess ? "成功" : "失败", templateCode, templateParams, body);
            result.setSuccess(true);
            result.setCode(huaweiyunResult.getCode());
            result.setMessage(huaweiyunResult.getDescription());
            result.setProvider(getProvider());
        } catch (Exception e) {
            log.warn("短信发送 {} 异常，mobileNo:{}, template:{}, params:{}", getProvider(), mobileNos, templateCode, templateParams, e);
            result.setErrorCode(SmsSendResultCode.NETWORK_CONN_ERROR);
        } finally {
            IOUtils.closeQuietly(client);
        }
        return result;
    }

    private String buildRequestBody(String sender, String receiver, String templateId, String templateParas, String statusCallBack, String signature) throws UnsupportedEncodingException {
        StringBuilder body = new StringBuilder();
        appendToBody(body, "from=", sender);
        appendToBody(body, "&to=", receiver);
        appendToBody(body, "&templateId=", templateId);
        appendToBody(body, "&templateParas=", templateParas);
        if (Strings.isNotBlank(statusCallBack)) {
            appendToBody(body, "&statusCallback=", statusCallBack);
        }
        return body.toString();
    }

    private static void appendToBody(StringBuilder body, String key, String val) throws UnsupportedEncodingException {
        if (null != val && !val.isEmpty()) {
            log.debug("Print appendToBody: {}:{}", key, val);
            body.append(key).append(URLEncoder.encode(val, UTF_8));
        }
    }

    private String getReceivers(Set<String> mobileNos) {
        if (mobileNos == null || mobileNos.isEmpty()) {
            throw new BusinessException(SmsSendResultCode.RECEIVER_MOBILE_EMPTY);
        }
        StringBuilder sb = new StringBuilder();
        for (String mobileNo : mobileNos) {
            if (!Strings.startsWith(mobileNo, "+86")) {
                mobileNo = "+86" + mobileNo;
            }
            sb.append(mobileNo).append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }


}


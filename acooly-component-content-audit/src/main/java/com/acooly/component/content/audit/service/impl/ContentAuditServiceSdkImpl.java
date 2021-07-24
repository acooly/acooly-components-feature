/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-07-23 21:34
 */
package com.acooly.component.content.audit.service.impl;

import com.acooly.component.content.audit.domain.ImageAuditRequest;
import com.acooly.component.content.audit.domain.TextAuditRequest;
import com.acooly.component.content.audit.domain.TextAuditResponse;
import com.acooly.component.content.audit.domain.TextAuditResult;
import com.acooly.component.content.audit.enums.AduitLabel;
import com.acooly.component.content.audit.enums.AliyunComError;
import com.acooly.component.content.audit.enums.ContentAuditError;
import com.acooly.component.content.audit.enums.Suggestions;
import com.acooly.component.content.audit.service.ContentAuditService;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.exception.CommonErrorCodes;
import com.acooly.core.utils.Strings;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.green.model.v20180509.ImageSyncScanRequest;
import com.aliyuncs.green.model.v20180509.TextScanRequest;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.google.common.collect.Lists;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhangpu
 * @date 2021-07-23 21:34
 */
@Slf4j
@NoArgsConstructor
public class ContentAuditServiceSdkImpl implements ContentAuditService {

    @Autowired
    IAcsClient acsClient;

    @Override
    public void textScan(TextAuditRequest request) {

        TextScanRequest textScanRequest = new TextScanRequest();
        textScanRequest.setAcceptFormat(FormatType.JSON);
        textScanRequest.setHttpContentType(FormatType.JSON);
        textScanRequest.setMethod(com.aliyuncs.http.MethodType.POST);
        textScanRequest.setEncoding("UTF-8");

        String requestJson = JSON.toJSONString(request);
        log.debug("文字审计 请求: {}", requestJson);
        textScanRequest.setHttpContent(requestJson.getBytes(), "UTF-8", FormatType.JSON);
        // 请务必设置超时时间。
        textScanRequest.setConnectTimeout(3000);
        textScanRequest.setReadTimeout(6000);
        try {
            HttpResponse httpResponse = acsClient.doAction(textScanRequest);
            if (!httpResponse.isSuccess()) {
                throw new ServerException(CommonErrorCodes.COMMUNICATION_ERROR.code(),
                        CommonErrorCodes.COMMUNICATION_ERROR.message(),
                        "response not success. status:" + httpResponse.getStatus());
            }
            String responseJson = new String(httpResponse.getHttpContent(), "UTF-8");
            log.debug("文字审计 响应: {}", responseJson);
            JSONObject scrResponse = JSON.parseObject(responseJson);
            // 主响应状态处理
            handleResult(scrResponse);
            JSONArray taskResults = scrResponse.getJSONArray("data");

            List<TextAuditResponse> responses = taskResults.toJavaList(TextAuditResponse.class);

            for (Object taskResult : taskResults) {
                JSONObject jsonObject = (JSONObject) taskResult;
                handleResult(jsonObject);
                TextAuditResponse response = jsonObject.toJavaObject(TextAuditResponse.class);
                for (TextAuditResult result : response.getResults()) {
                    if (Suggestions.pass.code().equals(result.getSuggestion())) {
                        continue;
                    }
                    AduitLabel aduitLabel = AduitLabel.find(result.getLabel());
                    if (aduitLabel != null) {
                        throw new BusinessException(aduitLabel, response.getFilteredContent());
                    }
                    throw new BusinessException(ContentAuditError.AUDIT_BLOCK, result.getLabel());
                }
            }
        } catch (ServerException e) {
            throw new BusinessException(e.getErrCode(), e.getErrMsg(), e);
        } catch (ClientException e) {
            throw new BusinessException(e.getErrCode(), e.getErrMsg(), e);
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            throw new BusinessException(CommonErrorCodes.INTERNAL_ERROR, e);
        }
    }

    @Override
    public void imageScan(ImageAuditRequest request) {

        ImageSyncScanRequest imageSyncScanRequest = new ImageSyncScanRequest();
        // 指定API返回格式。
        imageSyncScanRequest.setAcceptFormat(FormatType.JSON);
        // 指定请求方法。
        imageSyncScanRequest.setMethod(MethodType.POST);
        imageSyncScanRequest.setEncoding("utf-8");
        // 支持HTTP和HTTPS。
        imageSyncScanRequest.setProtocol(ProtocolType.HTTP);

        String requestJson = JSON.toJSONString(request);
        log.debug("图片审计 请求: {}", requestJson);
        imageSyncScanRequest.setHttpContent(requestJson.getBytes(), "UTF-8", FormatType.JSON);
        // 请务必设置超时时间。
        imageSyncScanRequest.setConnectTimeout(3000);
        imageSyncScanRequest.setReadTimeout(10000);

        try {
            HttpResponse httpResponse = acsClient.doAction(imageSyncScanRequest);
            if (!httpResponse.isSuccess()) {
                throw new ServerException(CommonErrorCodes.COMMUNICATION_ERROR.code(),
                        CommonErrorCodes.COMMUNICATION_ERROR.message(),
                        "response not success. status:" + httpResponse.getStatus());
            }

            String responseJson = new String(httpResponse.getHttpContent(), "UTF-8");
            log.debug("图片审计 响应: {}", responseJson);

            JSONObject scrResponse = JSON.parseObject(responseJson);
            // 主响应状态处理
            handleResult(scrResponse);
            JSONArray taskResults = scrResponse.getJSONArray("data");

            List<TextAuditResponse> responses = taskResults.toJavaList(TextAuditResponse.class);

            for (Object taskResult : taskResults) {
                JSONObject jsonObject = (JSONObject) taskResult;
                handleResult(jsonObject);
                TextAuditResponse response = jsonObject.toJavaObject(TextAuditResponse.class);
                for (TextAuditResult result : response.getResults()) {
                    if (Suggestions.pass.code().equals(result.getSuggestion())) {
                        continue;
                    }
                    AduitLabel aduitLabel = AduitLabel.find(result.getLabel());
                    if (aduitLabel != null) {
                        throw new BusinessException(aduitLabel, response.getFilteredContent());
                    }
                    throw new BusinessException(ContentAuditError.AUDIT_BLOCK, result.getLabel());
                }
            }
        } catch (ServerException e) {
            throw new BusinessException(e.getErrCode(), e.getErrMsg(), e);
        } catch (ClientException e) {
            throw new BusinessException(e.getErrCode(), e.getErrMsg(), e);
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            throw new BusinessException(CommonErrorCodes.INTERNAL_ERROR, e);
        }
    }

    public ContentAuditServiceSdkImpl(IAcsClient acsClient) {
        this.acsClient = acsClient;
    }


    private void handleResult(JSONObject jsonObject) {
        Integer code = jsonObject.getInteger("code");
        if (!AliyunComError.OK.getCode().equals(code)) {
            AliyunComError aliyunComError = AliyunComError.find(code);
            String responseMsg = jsonObject.getString("msg");
            throw new BusinessException(aliyunComError != null ? aliyunComError.name() : String.valueOf(code), responseMsg, "");
        }
    }

    private List<String> getContexts(JSONObject jsonObject) {
        List<String> contexts = Lists.newArrayList();
        JSONArray resultDetails = jsonObject.getJSONArray("details");
        for (Object detail : resultDetails) {
            JSONArray detailContexts = ((JSONObject) detail).getJSONArray("contexts");
            for (Object detailContext : detailContexts) {
                String cxt = ((JSONObject) detailContext).getString("context");
                if (Strings.isNoneBlank(cxt)) {
                    contexts.add(cxt);
                }
            }
        }
        return contexts;
    }

}

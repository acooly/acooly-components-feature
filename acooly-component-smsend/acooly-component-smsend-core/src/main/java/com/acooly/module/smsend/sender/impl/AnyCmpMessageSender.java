package com.acooly.module.smsend.sender.impl;

import com.acooly.core.utils.Asserts;
import com.acooly.core.utils.Strings;
import com.acooly.module.smsend.SmsSendProperties;
import com.acooly.module.smsend.common.enums.SmsProvider;
import com.acooly.module.smsend.common.enums.SmsSendResultCode;
import com.acooly.module.smsend.sender.dto.SmsResult;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * 阿里云短信接口
 *
 * @author zhangpu
 * @date 2020-05-04
 */
@Slf4j
@Component
public class AnyCmpMessageSender extends AbstractShortMessageSender {


    @Override
    public SmsResult sendTemplate(String mobileNo, String templateCode, Map<String, String> templateParams, String contentSign) {
        return doSendTemplate(mobileNo, templateCode, templateParams, contentSign);
    }

    @Override
    public SmsResult sendTemplate(Set<String> mobileNos, String templateCode, Map<String, String> templateParams, String contentSign) {
        return null;
    }

    @Override
    public SmsResult send(String mobileNo, String content, String contentSign) {
        return new SmsResult(SmsSendResultCode.NOT_SUPPORT_OPERATE, getProvider());
    }

    @Override
    public SmsResult send(Set<String> mobileNos, String content, String contentSign) {
        return new SmsResult(SmsSendResultCode.NOT_SUPPORT_OPERATE, getProvider());
    }


    @Override
    public SmsProvider getProvider() {
        return SmsProvider.AnyCmp;
    }

    /**
     * 发送
     *
     * @param mobileNos
     * @param templateCode
     * @param templateParams
     * @return
     */
    protected SmsResult doSendTemplate(String mobileNo, String templateCode, Map<String, String> templateParams, String contentSign) {
        Asserts.notEmpty(mobileNo, "手机号码");
        SmsSendProperties.SmsProviderInfo providerInfo = getProviderInfo();
        DefaultProfile profile = DefaultProfile.getProfile("ydy", providerInfo.getAccessKey(), providerInfo.getSecretKey());
        IAcsClient client = new DefaultAcsClient(profile);
        //创建发送请求实体
        CommonRequest request = new CommonRequest();
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        //短信平台单发接口地址
        request.setDomain("sms.alicqyun.com/sms/send");

        //发送参数
        request.putQueryParameter("PhoneNumbers", mobileNo);
        request.putQueryParameter("SignName", Strings.isNotBlank(contentSign) ? contentSign : providerInfo.getContentSign());
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", JSON.toJSONString(templateParams));

        SmsResult result = new SmsResult(getProvider());
        try {
            CommonResponse response = client.getCommonResponse(request);
            String body = response.getData();
            AliyunSmsResult aliyunSmsResult = JSON.parseObject(body, AliyunSmsResult.class);
            boolean sendSuccess = AliyunSmsResult.SUCCESS_CODE.equalsIgnoreCase(AliyunSmsResult.SUCCESS_CODE);
            log.info("短信发送 [{}] {},template:{}, params:{}, result: {}", getProvider().code(),
                    sendSuccess ? "成功" : "失败", templateCode, templateParams, body);
            result.setSuccess(sendSuccess);
            result.setCode(aliyunSmsResult.getCode());
            result.setMessage(aliyunSmsResult.getMessage());
            result.setRequestId(aliyunSmsResult.getRequestId());
        } catch (ClientException ce) {
            result.setSuccess(false);
            result.setCode(ce.getErrCode());
            result.setMessage(ce.getErrMsg());
            log.warn("短信发送 [{}] 失败，mobileNo:{}, template:{}, params:{}, error: {}", getProvider(), mobileNo, templateCode, templateParams, ce.getMessage());
        } catch (Exception e) {
            log.warn("短信发送 [{}] 异常，mobileNo:{}, template:{}, params:{}, error: {}", getProvider(), mobileNo, templateCode, templateParams, e.getMessage());
            result.setErrorCode(SmsSendResultCode.NETWORK_CONN_ERROR);
        }
        return result;
    }


    @Data
    protected static class AliyunSmsResult {
        public static final String SUCCESS_CODE = "OK";
        private String Code;
        private String Message;
        private String RequestId;
        private String BizId;
    }

}

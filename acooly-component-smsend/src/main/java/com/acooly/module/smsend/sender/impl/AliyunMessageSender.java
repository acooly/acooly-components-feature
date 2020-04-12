package com.acooly.module.smsend.sender.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.module.smsend.SmsendProperties;
import com.acooly.module.smsend.enums.SmsProvider;
import com.acooly.module.smsend.enums.SmsendResultCode;
import com.acooly.module.smsend.sender.ShortMessageSendException;
import com.alibaba.fastjson.JSON;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 阿里云短信接口
 *
 * @author shuijing
 * @link https://help.aliyun.com/document_detail/56189.html?spm=a2c4g.11186623.2.16.30b913c3oBiLE3
 * <p>阿里云通道只支持模板和签名为短信内容 发送接口send(String mobileNo, String content) content内容需为json格式 见测试用例： @See
 * Scom.acooly.core.test.web.TestController#testAliyunSms()
 */
@Slf4j
@Component
public class AliyunMessageSender extends AbstractShortMessageSender {

    private String getGMT(Date dateCST) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return (df.format(dateCST));
    }

    @Override
    public String send(String mobileNo, String content) {
        ArrayList<String> list = Lists.newArrayListWithCapacity(1);
        list.add(mobileNo);
        return send(list, content);
    }

    @Override
    public String send(List<String> mobileNos, String content) {
        return null;
    }


    protected String doSend(List<String> mobileNos, String templateCode, Map<String, String> templateParams) {

        //已经更新为云 通信短信服务 新接口
        String mobileNo = Joiner.on(",").join(mobileNos);
        String gmt = getGMT(new Date());
        SmsendProperties.SmsProviderInfo providerInfo = properties.getProviders().get(this.getProvider());
        String topicName = (String) providerInfo.getExt().get("topicName");

        Map<String, String> paras = new HashMap<>();
        paras.put("SignatureMethod", "HMAC-SHA1");
        paras.put("SignatureNonce", UUID.randomUUID().toString());
        paras.put("AccessKeyId", providerInfo.getAccessKey());
        paras.put("SignatureVersion", "1.0");
        paras.put("Timestamp", gmt);
        paras.put("Format", "XML");

        paras.put("Action", "SendSms");
        paras.put("Version", "2017-05-25");
        paras.put("RegionId", topicName.substring(topicName.indexOf("-") + 1));
        paras.put("PhoneNumbers", mobileNo);
        paras.put("SignName", providerInfo.getContentSign());
        paras.put("TemplateCode", templateCode);
        paras.put("TemplateParam", JSON.toJSONString(templateParams));
        paras.remove("Signature");

        TreeMap<String, String> sortParas = new TreeMap<>(paras);
        Iterator<String> it = sortParas.keySet().iterator();
        StringBuilder sortQueryStringTmp = new StringBuilder();
        while (it.hasNext()) {
            String key = it.next();
            sortQueryStringTmp.append("&").append(specialUrlEncode(key)).append("=").append(specialUrlEncode(paras.get(key)));
        }
        String sortedQueryString = sortQueryStringTmp.substring(1);
        StringBuilder stringToSign = new StringBuilder();
        stringToSign.append("GET").append("&");
        stringToSign.append(specialUrlEncode("/")).append("&");
        stringToSign.append(specialUrlEncode(sortedQueryString));

        try {
            String sign = sign(providerInfo.getSecretKey() + "&", stringToSign.toString());
            String signature = specialUrlEncode(sign);
            String url = "http://dysmsapi.aliyuncs.com/?Signature=" + signature + sortQueryStringTmp;
            String body = HttpRequest.get(url)
                    .readTimeout(properties.getReadTimeout()).connectTimeout(properties.getConnTimeout())
                    .trustAllCerts().trustAllHosts().body();
            AliyunSmsResult aliyunSmsResult = JSON.parseObject(body, AliyunSmsResult.class);
            boolean sendSuccess = AliyunSmsResult.SUCCESS_CODE.equalsIgnoreCase(aliyunSmsResult.Code);
            log.info("短信发送 [{}] {},template:{}, params:{}, result: {}", getProvider().code(),
                    sendSuccess ? "success" : "failure", templateCode, templateParams, body);
            if (!sendSuccess) {
                throw new ShortMessageSendException(aliyunSmsResult.Code, aliyunSmsResult.Message);
            }
            return aliyunSmsResult.Message;
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            log.warn("短信发送 {} 异常，mobileNo:{}, template:{}, params:{}", getProvider(), mobileNo, templateCode, templateParams, e);
            throw new ShortMessageSendException(SmsendResultCode.network_conn_error, e.getMessage());
        }
    }

    @Override
    public String send(String mobileNo, String templateCode, Map<String, String> templateParams) {
        return null;
    }

    @Override
    public String send(List<String> mobileNos, String templateCode, Map<String, String> templateParams) {
        return null;
    }

    @Override
    public SmsProvider getProvider() {
        return SmsProvider.Aliyun;
    }


    protected String specialUrlEncode(String value) {
        try {
            return java.net.URLEncoder.encode(value, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
        } catch (UnsupportedEncodingException e) {
            throw new ShortMessageSendException("url编码失败", e.getMessage());
        }
    }

    protected String sign(String accessSecret, String stringToSign) throws Exception {
        javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA1");
        mac.init(new javax.crypto.spec.SecretKeySpec(accessSecret.getBytes("UTF-8"), "HmacSHA1"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        return new sun.misc.BASE64Encoder().encode(signData);
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

package com.acooly.module.sms.sender.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.module.sms.SmsProperties;
import com.acooly.module.sms.sender.ShortMessageSendException;
import com.acooly.module.sms.sender.support.AliyunSmsSendVo;
import com.acooly.module.sms.sender.support.parser.AliyunMessageResponseParser;
import com.acooly.module.sms.sender.support.parser.BaseMessageResponseParser;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
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
@Service("aliyunMessageSender")
public class AliyunMessageSender extends AbstractShortMessageSender {

    @Autowired
    private SmsProperties properties;

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

        //已经更新为云 通信短信服务 新接口
        String mobileNo = Joiner.on(",").join(mobileNos);
        String gmt = getGMT(new Date());
        SmsProperties.Aliyun aliyun = properties.getAliyun();
        String topicName = aliyun.getTopicName();

        AliyunSmsSendVo aliVo = AliyunSmsSendVo.getGson().fromJson(content, AliyunSmsSendVo.class);
        java.util.Map<String, String> paras = new java.util.HashMap<>();
        paras.put("SignatureMethod", "HMAC-SHA1");
        paras.put("SignatureNonce", java.util.UUID.randomUUID().toString());
        paras.put("AccessKeyId", aliyun.getAccessKeyId());
        paras.put("SignatureVersion", "1.0");
        paras.put("Timestamp", gmt);
        paras.put("Format", "XML");

        paras.put("Action", "SendSms");
        paras.put("Version", "2017-05-25");
        paras.put("RegionId", topicName.substring(topicName.indexOf("-") + 1, topicName.length()));
        paras.put("PhoneNumbers", mobileNo);
        paras.put("SignName", aliVo.getFreeSignName());
        paras.put("TemplateParam", aliVo.getSmsParams());
        paras.put("TemplateCode", aliVo.getTemplateCode());
        paras.remove("Signature");

        TreeMap<String, String> sortParas = new TreeMap<>(paras);
        java.util.Iterator<String> it = sortParas.keySet().iterator();
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
            String sign = sign(aliyun.getAccessKeySecret() + "&", stringToSign.toString());

            String signature = specialUrlEncode(sign);

            String url = "http://dysmsapi.aliyuncs.com/?Signature=" + signature + sortQueryStringTmp;

            String body = com.github.kevinsawicki.http.HttpRequest.get(url)
                    .readTimeout(timeout)
                    .connectTimeout(timeout)
                    .trustAllCerts()
                    .trustAllHosts()
                    .body();
            logger.info("短信发送：{}，返回结果:{}", mobileNo, body);
            return handleResult(body, mobileNo);
        } catch (Exception e) {
            logger.warn("发送短信失败 {号码:" + mobileNo + ",内容:" + content + "}, 原因:" + e.getMessage());
            if (e instanceof BusinessException) {
                String code = ((BusinessException) e).getCode();
                throw new ShortMessageSendException(
                        code, ((BusinessException) e).message(), e.getMessage());
            }
            if (e instanceof IOException
                    || e instanceof ParserConfigurationException
                    || e instanceof SAXException) {
                throw new ShortMessageSendException("-1", "解析返回数据出错", e.getMessage());
            }
            throw new ShortMessageSendException("-1", "请求失败", e.getMessage());
        }
    }

    protected String handleResult(String body, String mobile)
            throws IOException, ParserConfigurationException, SAXException {
        if (StringUtils.isEmpty(body)) {
            throw new BusinessException("返回数据为空");
        }
        Document document = AliyunMessageResponseParser.getInstance().parse(body);
        NodeList message = document.getElementsByTagName(AliyunMessageResponseParser.Message);
        Element line = (Element) message.item(0);
        String resMsg = BaseMessageResponseParser.getCharacterDataFromElement(line);
        System.out.println(resMsg);
        if ("OK".equals(resMsg)) {
            logger.info("发送到：{}成功!", mobile);
        } else {
            throw new BusinessException("发送失败：" + resMsg);
        }
        return resMsg;
    }

    @Override
    public String getProvider() {
        return "阿里云";
    }


    protected String specialUrlEncode(String value) {
        try {
            return java.net.URLEncoder.encode(value, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new ShortMessageSendException("url编码失败", e.getMessage());
        }
    }

    protected String sign(String accessSecret, String stringToSign) throws Exception {
        javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA1");
        mac.init(new javax.crypto.spec.SecretKeySpec(accessSecret.getBytes("UTF-8"), "HmacSHA1"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        return new sun.misc.BASE64Encoder().encode(signData);
    }

}

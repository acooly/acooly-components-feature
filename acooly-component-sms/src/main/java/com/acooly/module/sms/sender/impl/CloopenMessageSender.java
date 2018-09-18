package com.acooly.module.sms.sender.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.net.HttpResult;
import com.acooly.core.utils.net.Https;
import com.acooly.module.sms.SmsProperties;
import com.acooly.module.sms.sender.ShortMessageSendException;
import com.acooly.module.sms.sender.support.CloopenSmsSendVo;
import com.acooly.module.sms.sender.support.parser.BaseMessageResponseParser;
import com.acooly.module.sms.sender.support.parser.CloopenMessageResponseParser;
import com.acooly.module.sms.sender.support.serializer.CloopenMessageSendSerializer;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.DateUtils;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.InputStreamEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.acooly.module.sms.sender.support.parser.BaseMessageResponseParser.getCharacterDataFromElement;
import static com.acooly.module.sms.sender.support.serializer.CloopenMessageSendSerializer.*;

/**
 * 容联.云通讯短信接口
 *
 * @author shuijing
 * @link http://www.yuntongxun.com/doc/rest/sms/3_2_1_1.html
 * <p>只支持模板和签名为短信内容 发送接口send(String mobileNo, String content) content内容需为json格式 见测试用例： @See
 * com.acooly.core.test.web.TestController#testCloopenSms()
 */
@Slf4j
@Service("cloopenMessageSender")
public class CloopenMessageSender extends AbstractShortMessageSender {

    private final String SEND_URL_CLO = "https://app.cloopen.com:8883/";

    private final String ENCODING = "UTF-8";

    @Autowired
    private SmsProperties properties;

    @Override
    public String send(String mobileNo, String content) {
        ArrayList<String> list = Lists.newArrayListWithCapacity(1);
        list.add(mobileNo);
        return send(list, content);
    }

    @Override
    public String send(List<String> mobileNos, String content) {
        String mobileNo = Joiner.on(",").join(mobileNos);
        SmsProperties.Cloopen cloopen = properties.getCloopen();
        String accountId = cloopen.getAccountId();
        String accountToken = cloopen.getAccountToken();
        String appId = cloopen.getAppId();
        String crrDate = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");

        String sign = cloopenSignMd5(accountId, accountToken, crrDate);
        String base64Sign = cloopenSignBase64(accountId, crrDate);

        CloopenSmsSendVo cloVo = CloopenSmsSendVo.getGson().fromJson(content, CloopenSmsSendVo.class);
        String templateId = cloVo.getTemplateId();
        List datas = cloVo.getDatas();

        Map<String, Object> params = Maps.newHashMap();
        params.put(TO, mobileNo);
        params.put(APP_ID, appId);
        params.put(TEMPLATE_ID, templateId);
        params.put(DATAS, datas);

        Https instance = Https.getInstance();
        instance.connectTimeout(timeout / 2);
        instance.readTimeout(timeout / 2);
        try {
            String xml = CloopenMessageSendSerializer.getInstance().serialize(params, ENCODING);
            InputStream xmlSerialize = new ByteArrayInputStream(xml.getBytes(ENCODING));
            //2013-12-26/Accounts/{accountSid}/SMS/TemplateSMS?sig={SigParameter}
            HttpPost httppost =
                    new HttpPost(
                            SEND_URL_CLO + "2013-12-26/Accounts/" + accountId + "/SMS/TemplateSMS?sig=" + sign);

            InputStreamEntity inputStreamEntity = new InputStreamEntity(xmlSerialize);
            httppost.setEntity(inputStreamEntity);
            BasicHttpEntity basicHttpEntity = new BasicHttpEntity();
            basicHttpEntity.setContent(xmlSerialize);
            basicHttpEntity.setContentLength(xml.getBytes(ENCODING).length);

            Map<String, String> headers = Maps.newHashMap();
            headers.put("Authorization", base64Sign);
            headers.put("Accept", "application/xml");
            headers.put("Content-Type", "text/xml;charset=utf-8");

            HttpResult result = instance.execute(null, httppost, headers, false, "utf-8");
            return handleResult(result, CloopenSmsSendVo.getGson().toJson(params));
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

    private String cloopenSignMd5(String accountId, String appId, String crrDate) {
        String md5Hex = DigestUtils.md5Hex((accountId + appId + crrDate));
        return md5Hex.toUpperCase();
    }

    private String cloopenSignBase64(String accountId, String crrDate) {
        return new String(Base64.encodeBase64((accountId + ":" + crrDate).getBytes()));
    }

    protected String handleResult(HttpResult result, String paramString)
            throws IOException, ParserConfigurationException, SAXException {

        if (result.getStatus() != 200) {
            throw new BusinessException("http StatusCode=" + result.getStatus());
        }

        String body = result.getBody();
        if (StringUtils.isEmpty(body)) {
            throw new BusinessException("返回数据为空");
        }
        Document document = BaseMessageResponseParser.parse(body);
        NodeList statusCodeNode =
                document.getElementsByTagName(CloopenMessageResponseParser.STATUS_CODE);
        if (statusCodeNode.getLength() > 0) {
            Element esn = (Element) statusCodeNode.item(0);
            String statusCode = getCharacterDataFromElement(esn);
            if (!StringUtils.isEmpty(statusCode)) {
                if (CloopenMessageResponseParser.SUCCESS_CODE.equals(statusCode)) {
                    log.info("SMS cloopen send success {}", paramString);
                    return "success";
                } else {
                    log.info(
                            "SMS cloopen send fail {},reason is {}",
                            paramString,
                            CloopenMessageResponseParser.codeMapping.get(statusCode));
                    throw new BusinessException(
                            CloopenMessageResponseParser.codeMapping.get(statusCode), statusCode);
                }
            }
        }
        throw new BusinessException("发送失败,返回数据为：{}", body);
    }

    @Override
    public String getProvider() {
        return "容联.云通讯";
    }
}

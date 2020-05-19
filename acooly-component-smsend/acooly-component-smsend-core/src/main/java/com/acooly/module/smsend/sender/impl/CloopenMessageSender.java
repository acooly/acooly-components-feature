package com.acooly.module.smsend.sender.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Dates;
import com.acooly.core.utils.Ids;
import com.acooly.core.utils.Strings;
import com.acooly.module.smsend.SmsSendProperties;
import com.acooly.module.smsend.common.enums.SmsProvider;
import com.acooly.module.smsend.common.enums.SmsSendResultCode;
import com.acooly.module.smsend.sender.dto.SmsResult;
import com.acooly.module.smsend.sender.support.parser.BaseMessageResponseParser;
import com.acooly.module.smsend.sender.support.parser.CloopenMessageResponseParser;
import com.acooly.module.smsend.sender.support.serializer.CloopenMessageSendSerializer;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.acooly.module.smsend.sender.support.parser.BaseMessageResponseParser.getCharacterDataFromElement;

/**
 * 容联.云通讯短信接口
 *
 * @author shuijing
 * @link http://www.yuntongxun.com/doc/rest/sms/3_2_1_1.html
 * <p>只支持模板和签名为短信内容 发送接口send(String mobileNo, String content) content内容需为json格式 见测试用例： @See
 * com.acooly.core.test.web.TestController#testCloopenSms()
 */
@Slf4j
@Component
public class CloopenMessageSender extends AbstractShortMessageSender {

    public static final String TEMPLATE_SMS = "TemplateSMS";
    public static final String TO = "to";
    public static final String APP_ID = "appId";
    public static final String TEMPLATE_ID = "templateId";
    public static final String DATAS = "datas";
    public static final String DATA = "data";

    private final String SEND_URL_CLO = "https://app.cloopen.com:8883/";

    private final String ENCODING = "UTF-8";

    @Override
    public SmsResult send(String mobileNo, String content, String contentSign) {
        return new SmsResult(SmsSendResultCode.NOT_SUPPORT_OPERATE, getProvider());
    }

    @Override
    public SmsResult send(Set<String> mobileNos, String content, String contentSign) {
        return new SmsResult(SmsSendResultCode.NOT_SUPPORT_OPERATE, getProvider());
    }

    @Override
    public SmsResult sendTemplate(String mobileNo, String templateCode, Map<String, String> templateParams, String contentSign) {
        Set<String> mobileNos = Sets.newLinkedHashSet();
        mobileNos.add(mobileNo);
        return sendTemplate(mobileNos, templateCode, templateParams, contentSign);
    }

    @Override
    public SmsResult sendTemplate(Set<String> mobileNos, String templateCode, Map<String, String> templateParams, String contentSign) {
        String mobileNo = Joiner.on(",").join(mobileNos);
        SmsSendProperties.SmsProviderInfo providerInfo = properties.getProviders().get(this.getProvider());
        String accountId = providerInfo.getAccessKey();
        String accountToken = providerInfo.getSecretKey();
        String appId = providerInfo.getAppId();
        String crrDate = Dates.format("yyyyMMddHHmmss");

        String sign = cloopenSignMd5(accountId, accountToken, crrDate);
        String base64Sign = cloopenSignBase64(accountId, crrDate);

        String templateId = templateCode;
        List<String> datas = Lists.newArrayList(templateParams.values());
        String reqId = Ids.getDid();
        Map<String, Object> params = Maps.newHashMap();
        params.put(TO, mobileNo);
        params.put(APP_ID, appId);
        params.put(TEMPLATE_ID, templateId);
        params.put(DATAS, datas);

        SmsResult result = new SmsResult(getProvider());
        try {
            String xml = CloopenMessageSendSerializer.getInstance().serialize(params, ENCODING);
            String url = SEND_URL_CLO + "2013-12-26/Accounts/" + accountId + "/SMS/TemplateSMS?sig=" + sign;
            Map<String, String> headers = Maps.newHashMap();
            headers.put("Authorization", base64Sign);
            headers.put("Accept", "application/xml");
            headers.put("Content-Type", "text/xml;charset=utf-8");
            // 网络请求
            HttpRequest httpRequest = HttpRequest.post(url).headers(headers)
                    .readTimeout(properties.getReadTimeout()).connectTimeout(properties.getConnTimeout())
                    .trustAllCerts().trustAllHosts().send(xml);

            if (httpRequest.code() != 200) {
                throw new BusinessException("http StatusCode=" + httpRequest.code());
            }
            String body = httpRequest.body();
            if (StringUtils.isEmpty(body)) {
                throw new BusinessException("返回数据为空");
            }
            String statusCode = parseResponseCode(body);
            result.setCode(statusCode);
            result.setMessage(CloopenMessageResponseParser.codeMapping.get(statusCode));
            if (CloopenMessageResponseParser.SUCCESS_CODE.equals(statusCode)) {
                log.info("短信发送 [容联云] 成功, mobileNo:{}, templateCode:{} , templateParam:{}", mobileNo, templateCode, templateParams);
                result.setSuccess(true);
            } else {
                log.info("短信发送 [容联云] 失败：{} ：{}, mobileNo:{}, templateCode:{} , templateParam:{}", result.getCode(), result.getMessage(),
                        mobileNo, templateCode, templateParams);
                throw new BusinessException(result);
            }
        } catch (BusinessException be) {
            result.setErrorCode(be);
        } catch (Exception e) {
            if (e instanceof IOException || e instanceof ParserConfigurationException || e instanceof SAXException) {
                result.setErrorCode(SmsSendResultCode.DATA_PARSE_ERORR);
            } else {
                result.setErrorCode(SmsSendResultCode.NETWORK_CONN_ERROR);
            }
        }
        return result;
    }

    @Override
    public SmsProvider getProvider() {
        return SmsProvider.Cloopen;
    }

    private String cloopenSignMd5(String accountId, String appId, String crrDate) {
        String md5Hex = DigestUtils.md5Hex((accountId + appId + crrDate));
        return md5Hex.toUpperCase();
    }

    private String cloopenSignBase64(String accountId, String crrDate) {
        return new String(Base64.encodeBase64((accountId + ":" + crrDate).getBytes()));
    }


    protected String parseResponseCode(String body) throws IOException, ParserConfigurationException, SAXException {
        Document document = BaseMessageResponseParser.parse(body);
        NodeList statusCodeNode = document.getElementsByTagName(CloopenMessageResponseParser.STATUS_CODE);
        if (statusCodeNode.getLength() > 0) {
            Element esn = (Element) statusCodeNode.item(0);
            String statusCode = getCharacterDataFromElement(esn);
            if (Strings.isNoneBlank(statusCode)) {
                return statusCode;
            }
        }
        throw new BusinessException("返回数据解析失败");
    }
}

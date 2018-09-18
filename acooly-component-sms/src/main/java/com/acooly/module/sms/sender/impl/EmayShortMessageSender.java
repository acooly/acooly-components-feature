/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-03-05 10:51 创建
 */
package com.acooly.module.sms.sender.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Ids;
import com.acooly.core.utils.net.HttpResult;
import com.acooly.core.utils.net.Https;
import com.acooly.module.sms.SmsProperties;
import com.acooly.module.sms.sender.ShortMessageSendException;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.*;

/**
 * @author qiubo@yiji.com
 */
@Service("emayShortMessageSender")
public class EmayShortMessageSender extends AbstractShortMessageSender {
    protected static final int SUCCESS_CODE = 0;
    private static Map<String, String> codeMapping = new HashMap<>();

    static {
        codeMapping.put("-1", "系统异常");
        codeMapping.put("-101", "命令不被支持");
        codeMapping.put("-102", "RegistryTransInfo删除信息失败");
        codeMapping.put("-103", "RegistryInfo更新信息失败");
        codeMapping.put("-104", "请求超过限制");
        codeMapping.put("-111", "企业注册失败");
        codeMapping.put("-117", "发送短信失败");
        codeMapping.put("-118", "接收MO失败");
        codeMapping.put("-119", "接收Report失败");
        codeMapping.put("-120", "修改密码失败");
        codeMapping.put("-122", "号码注销激活失败");
        codeMapping.put("-110", "号码注册激活失败");
        codeMapping.put("-123", "查询单价失败");
        codeMapping.put("-124", "查询余额失败");
        codeMapping.put("-125", "设置MO转发失败");
        codeMapping.put("-126", "路由信息失败");
        codeMapping.put("-127", "计费失败0余额");
        codeMapping.put("-128", "计费失败余额不足");
        codeMapping.put("-1100", "序列号错误,序列号不存在内存中,或尝试攻击的用户");
        codeMapping.put("-1103", "序列号Key错误");
        codeMapping.put("-1102", "序列号密码错误");
        codeMapping.put("-1104", "路由失败，请联系系统管理员");
        codeMapping.put("-1105", "注册号状态异常, 未用 1");
        codeMapping.put("-1107", "注册号状态异常, 停用 3");
        codeMapping.put("-1108", "注册号状态异常, 停止 5");
        codeMapping.put("-113", "充值失败");
        codeMapping.put("-1131", "充值卡无效");
        codeMapping.put("-1132", "充值密码无效");
        codeMapping.put("-1133", "充值卡绑定异常");
        codeMapping.put("-1134", "充值状态无效");
        codeMapping.put("-1135", "充值金额无效");
        codeMapping.put("-190", "数据操作失败");

        codeMapping.put("-1901", "数据库插入操作失败");
        codeMapping.put("-1902", "数据库更新操作失败");
        codeMapping.put("-1903", "数据库删除操作失败");
    }

    private final String SEND_URL999 = "http://sdk999in.eucp.b2m.cn:8080/sdkproxy/sendsms.action";
    @Autowired
    private SmsProperties properties;
    private List<String> tagNames = Arrays.asList("error", "message");

    @PostConstruct
    public void init() {
    }

    @Override
    public String send(String mobileNo, String content) {
        ArrayList<String> list = Lists.newArrayListWithCapacity(1);
        list.add(mobileNo);
        return send(list, content);
    }

    protected String unmashall(HttpResult result) {
        if (result.getStatus() != 200) {
            throw new BusinessException("http StatusCode=" + result.getStatus());
        } else {
            Map<String, String> response;
            response = convertXML(org.apache.commons.lang.StringUtils.trim(result.getBody()), tagNames);
            if (response != null) {
                String errorCode = response.get("error");
                String message = response.get("message");
                if (SUCCESS_CODE == Integer.parseInt(errorCode)) {
                    return errorCode;
                } else {
                    if (Strings.isNullOrEmpty(message)) {
                        message = codeMapping.get(errorCode);
                    }
                    throw new BusinessException(message, errorCode);
                }
            } else {
                throw new BusinessException("response null");
            }
        }
    }

    private Map<String, String> convertXML(String xml, List<String> tagNames) {
        Map<String, String> map = Maps.newHashMap();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            Document doc =
                    builder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
            for (String tagName : tagNames) {
                String tagText = doc.getElementsByTagName(tagName).item(0).getTextContent();
                map.put(tagName, tagText);
            }
        } catch (Exception e) {
            logger.error("解析XML{}失败{}", xml, e.getMessage());
        }
        return map;
    }

    public void register(String cdkey, String password) {
        logger.info("亿美短信帐号注册");
        HttpRequest request =
                HttpRequest.get(
                        "http://sdk999in.eucp.b2m.cn:8080/sdkproxy/regist.action?cdkey="
                                + cdkey
                                + "&password="
                                + password)
                        .connectTimeout(timeout / 2)
                        .readTimeout(timeout / 2);
        logger.info("亿美短信帐号注册响应:{}", request.body());
    }

    @Override
    public String send(List<String> mobileNos, String content) {
        String mobileNo = Joiner.on(",").join(mobileNos);
        content = getContent(content);

        String sn = properties.getEmay().getSn();
        String passwd = properties.getEmay().getPassword();
        Map<String, String> dataMap = Maps.newHashMap();
        dataMap.put("cdkey", sn);
        dataMap.put("password", passwd);

        dataMap.put("seqid", Ids.getDid());

        dataMap.put("phone", mobileNo);

        dataMap.put("addserial", "");
        dataMap.put("smspriority", "1");
        dataMap.put("message", content);
        Https instance = Https.getInstance();
        instance.connectTimeout(timeout / 2);
        instance.readTimeout(timeout / 2);
        try {
            HttpResult httpResult = instance.post(SEND_URL999, dataMap);
            String result = unmashall(httpResult);
            logger.info("发送短信完成 {mobile:{},content:{},result:{}}", mobileNo, content, result);
            return result;
        } catch (Exception e) {
            logger.warn("发送短信失败 {号码:" + mobileNo + ",内容:" + content + "}, 原因:" + e.getMessage());
            if (e instanceof BusinessException) {
                String code = ((BusinessException) e).getCode();
                if ("-1105".equals(code)) {
                    register(sn, passwd);
                }
                throw new ShortMessageSendException(
                        code, ((BusinessException) e).message(), e.getMessage());
            }
            throw new ShortMessageSendException("-1", "请求失败", e.getMessage());
        }
    }

    @Override
    protected String getContent(String content) {
        String signContent = properties.getEmay().getSign() + content;
        return StringUtils.isEmpty(posfix) ? signContent : signContent + posfix;
    }

    @Override
    public String getProvider() {
        return "亿美";
    }
}

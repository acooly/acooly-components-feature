package com.acooly.module.sms.sender.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Dates;
import com.acooly.core.utils.net.HttpResult;
import com.acooly.core.utils.net.Https;
import com.acooly.module.sms.SmsProperties;
import com.acooly.module.sms.sender.ShortMessageSendException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

/**
 * 创蓝253 Json接口
 *
 * @author shuijing
 */
@Service("cl253JsonShortMessageSender")
public class CL253JsonShortMessageSender extends AbstractShortMessageSender {

    private static Map<String, String> codeMapping = new HashMap<>();

    static {
        codeMapping.put("0", "发送成功");
        codeMapping.put("101", "无此用户");
        codeMapping.put("102", "密码错");
        codeMapping.put("103", "提交过快");
        codeMapping.put("104", "系统忙");
        codeMapping.put("105", "敏感短信");
        codeMapping.put("106", "消息长度错");
        codeMapping.put("107", "包含错误的手机号码");
        codeMapping.put("108", "手机号码个数错");
        codeMapping.put("109", "无发送额度");
        codeMapping.put("110", "不在发送时间内");
        codeMapping.put("113", "扩展码格式错");
        codeMapping.put("114", "可用参数组个数错误");
        codeMapping.put("116", "签名不合法或未带签名");
        codeMapping.put("117", "IP地址认证错,请求调用的IP地址不是系统登记的IP地址");
        codeMapping.put("118", "用户没有相应的发送权限");
        codeMapping.put("119", "用户已过期");
        codeMapping.put("120", "违反防盗用策略");
        codeMapping.put("123", "发送类型错误");
        codeMapping.put("124", "白模板匹配错误");
        codeMapping.put("125", "匹配驳回模板，提交失败");
        codeMapping.put("127", "定时发送时间格式错误");
        codeMapping.put("128", "内容编码失败");
        codeMapping.put("129", "JSON格式错误");
        codeMapping.put("130", "请求参数错误");
    }

    @Autowired
    private SmsProperties properties;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String send(String mobileNo, String content) {
        ArrayList<String> list = Lists.newArrayListWithCapacity(1);
        list.add(mobileNo);
        return send(list, content);
    }

    @Override
    public String send(List<String> mobileNos, String content) {
        String mobileNo = Joiner.on(",").join(mobileNos);

        String account = properties.getUsername();
        String pswd = properties.getPassword();
        String url = properties.getUrl();

        String pcontent = getContent(content);

        Map<String, String> dataMap = Maps.newHashMap();
        dataMap.put("account", account);
        dataMap.put("password", pswd);
        dataMap.put("msg", pcontent);
        dataMap.put("phone", mobileNo);

        String paraJson;
        try {
            paraJson = objectMapper.writeValueAsString(dataMap);
        } catch (JsonProcessingException e) {
            throw new ShortMessageSendException("-2", "请求数据解析失败", e.getMessage());
        }
        Https instance = Https.getInstance();
        instance.connectTimeout(timeout / 2);
        instance.readTimeout(timeout / 2);
        try {
            HttpResult httpResult = instance.post(url, paraJson, null, false, APPLICATION_JSON);
            String result = handleResult(httpResult);
            logger.info("发送短信成功  {mobile:{},content:{},result:{}}", mobileNo, pcontent, result);
            return result;
        } catch (Exception e) {
            logger.warn("发送短信失败 {号码:" + mobileNo + ",内容:" + pcontent + "}, 原因:" + e.getMessage());
            if (e instanceof BusinessException) {
                String code = ((BusinessException) e).getCode();
                throw new ShortMessageSendException(
                        code, ((BusinessException) e).message(), e.getMessage());
            }
            throw new ShortMessageSendException("-1", "请求失败", e.getMessage());
        }
    }

    protected String handleResult(HttpResult result) {
        if (result.getStatus() != 200) {
            throw new BusinessException("http code=" + result.getStatus());
        } else {
            /** {"time":"20170410103836", " msgId":"17041010383624511", " errorMsg":"", " code":"0" } */
            String body = result.getBody();
            if (StringUtils.isEmpty(body)) {
                throw new BusinessException("返回数据为空 ");
            }
            HashMap resData;
            try {
                resData = objectMapper.readValue(body, HashMap.class);
            } catch (IOException e) {
                throw new BusinessException("解析请求结果失败", e);
            }
            String resCode = (String) resData.get("code");
            Date responseTime = Dates.parse((String) resData.get("time"), "yyyyMMddHHmmss");
            logger.info("短信响应时间：{}", Dates.format(responseTime));
            if (0 == Integer.parseInt(resCode)) {
                return codeMapping.get(resCode);
            } else {
                String errorMsg = (String) resData.get("errorMsg");
                if (StringUtils.isEmpty(errorMsg)) {
                    errorMsg = codeMapping.get(resCode);
                }
                throw new BusinessException(errorMsg, resCode);
            }
        }
    }

    @Override
    public String getProvider() {
        return "创蓝253 Json接口";
    }

    @Override
    protected String getContent(String content) {
        if (StringUtils.isNotBlank(prefix) && !prefix.startsWith("【")) {
            prefix = "【" + prefix + "】";
        }
        return super.getContent(content);
    }
}

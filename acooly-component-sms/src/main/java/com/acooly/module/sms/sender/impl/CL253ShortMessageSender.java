package com.acooly.module.sms.sender.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Dates;
import com.acooly.core.utils.net.HttpResult;
import com.acooly.core.utils.net.Https;
import com.acooly.module.sms.SmsProperties;
import com.acooly.module.sms.sender.ShortMessageSendException;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 创蓝253短信平台
 *
 * @author shuijing
 */
@Service("cl253ShortMessageSender")
public class CL253ShortMessageSender extends AbstractShortMessageSender {

    private static Map<String, String> codeMapping = new HashMap<>();

    static {
        codeMapping.put("0", "提交成功");
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
        codeMapping.put("113", "extno格式错");
        codeMapping.put("116", "签名不合法或未带签名");
        codeMapping.put("117", "IP地址认证错,请求调用的IP地址不是系统登记的IP地址");
        codeMapping.put("118", "用户没有相应的发送权限");
        codeMapping.put("119", "用户已过期");
        codeMapping.put("120", "违反放盗用策略");
        codeMapping.put("121", "必填参数。是否需要状态报告，取值true或false");
        codeMapping.put("122", "5分钟内相同账号提交相同消息内容过多");
        codeMapping.put("123", "发送类型错误");
        codeMapping.put("124", "白模板匹配错误");
        codeMapping.put("125", "匹配驳回模板，提交失败");
        codeMapping.put("126", "审核通过模板匹配错误");
    }

    private final String SEND_URL253 = "https://sms.253.com/msg/send";
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
        content = getContent(content);

        String un = properties.getCl253().getUn();
        String passwd = properties.getCl253().getPw();

        Map<String, String> dataMap = Maps.newHashMap();
        dataMap.put("un", un);
        dataMap.put("pw", passwd);
        dataMap.put("phone", mobileNo);
        dataMap.put("msg", content);
        dataMap.put("rd", "1");

        Https instance = Https.getInstance();
        instance.connectTimeout(timeout / 2);
        instance.readTimeout(timeout / 2);
        try {
            HttpResult httpResult = instance.post(SEND_URL253, dataMap);
            String result = handleResult(httpResult);
            logger.info("发送短信完成  {mobile:{},content:{},result:{}}", mobileNo, content, result);
            return result;
        } catch (Exception e) {
            logger.warn("发送短信失败 {号码:" + mobileNo + ",内容:" + content + "}, 原因:" + e.getMessage());
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
            throw new BusinessException("http StatusCode=" + result.getStatus());
        } else {
            //响应时间,状态
            //20161025170822,0
            String body = result.getBody();
            if (StringUtils.isEmpty(body)) {
                throw new BusinessException("返回数据为空");
            }
            if (body.contains("\n")) {
                body = body.replace("\n", ",");
            }
            String[] bodySplit = body.split(",");
            String resCode = bodySplit[1];
            Date responseTime = Dates.parse(bodySplit[0], "yyyyMMddHHmmss");
            logger.info("短信响应时间：{}", Dates.format(responseTime));
            //logger.info("短信返回的messageid：{}", bodySplit[2]);
            if (0 == Integer.parseInt(resCode)) {
                return codeMapping.get(resCode);
            } else {
                String message = codeMapping.get(resCode);
                throw new BusinessException(message, resCode);
            }
        }
    }

    @Override
    public String getProvider() {
        return "创蓝253";
    }

    @Override
    protected String getContent(String content) {
        return properties.getCl253().getSign() + content + posfix;
    }
}

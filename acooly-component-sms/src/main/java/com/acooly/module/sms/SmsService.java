package com.acooly.module.sms;

import java.util.List;
import java.util.Map;

/**
 * 短信发送服务
 *
 * @author zhangpu
 */
public interface SmsService {

    /**
     * 单条发送
     *
     * @param mobileNo
     * @param content
     */
    void send(String mobileNo, String content);

    /**
     * 异步单条发送
     *
     * @param mobileNo
     * @param content
     */
    void sendAsync(String mobileNo, String content);

    /**
     * 单条发送(附带context)，context的内容存储到数据库发送记录
     *
     * @param mobileNo
     * @param content
     * @param smsContext
     */
    void send(String mobileNo, String content, SmsContext smsContext);

    /**
     * 异步单条发送(附带context)，context的内容存储到数据库发送记录
     *
     * @param mobileNo
     * @param content
     * @param smsContext
     */
    void sendAsync(String mobileNo, String content, SmsContext smsContext);

    /**
     * 单条发送(模板方式)
     *
     * @param mobileNo
     * @param template 模板key(sms.properties中的模板段，如：sms.template.activate)
     * @param data     模板数据
     */
    void sendByTemplate(String mobileNo, String template, Map<String, Object> data);

    /**
     * 单条异步发送(模板方式)
     *
     * @param mobileNo
     * @param template 模板key(sms.properties中的模板段，如：sms.template.activate)
     * @param data
     */
    void sendByTemplateAsync(String mobileNo, String template, Map<String, Object> data);

    /**
     * 单条发送(模板方式+附带context)
     *
     * @param mobileNo
     * @param template
     * @param data
     * @param smsContext
     */
    void sendByTemplate(
            String mobileNo, String template, Map<String, Object> data, SmsContext smsContext);

    /**
     * 单条异步发送(模板方式+附带context)
     *
     * @param mobileNo   手机号码
     * @param template   模板key（sms.properties中的模板段，如：sms.template.activate）
     * @param data       模板数据
     * @param smsContext 发送会话，会写入发送日志
     */
    void sendByTemplateAsync(
            String mobileNo, String template, Map<String, Object> data, SmsContext smsContext);

    /**
     * 批量方式
     *
     * <p>注意：批量发送账号必须配置
     *
     * @param mobileNos
     * @param content
     */
    void batchSend(List<String> mobileNos, String content);

    /**
     * 批量方式(模板)
     *
     * @param mobileNos
     * @param template  模板名称（sms.properties中的模板段，如：sms.template.activate）
     * @param data
     */
    void batchSendByTemplate(List<String> mobileNos, String template, Map<String, Object> data);
}

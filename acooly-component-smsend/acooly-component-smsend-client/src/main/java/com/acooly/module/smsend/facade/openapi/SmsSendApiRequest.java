/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-30 17:50
 */
package com.acooly.module.smsend.facade.openapi;

import com.acooly.core.common.exception.ParameterException;
import com.acooly.core.utils.Asserts;
import com.acooly.core.utils.validate.predicate.MobileNoPredicate;
import com.acooly.module.smsend.common.enums.SmsSendType;
import com.acooly.openapi.framework.common.annotation.OpenApiField;
import com.acooly.openapi.framework.common.annotation.OpenApiFieldCondition;
import com.acooly.openapi.framework.common.message.ApiRequest;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 短信发送 请求报文
 *
 * @author zhangpu
 * @date 2020-05-30 17:50
 */
@Slf4j
@Getter
@Setter
public class SmsSendApiRequest extends ApiRequest {

    /**
     * 应用编码
     * 用于识别是那个系统或应用请求发送
     */
    @NotBlank
    @Size(max = 32)
    @OpenApiField(desc = "应用ID", constraint = "唯一标志发送短信的应用", demo = "youcheyun", ordinal = 1)
    private String appId;


    /**
     * 异步发送
     */
    @NotNull
    @OpenApiField(desc = "发送方式", constraint = "true: 同步(默认),false:异步", demo = "true", ordinal = 2)
    private Boolean async = true;


    @NotEmpty
    @OpenApiField(desc = "手机号码", constraint = "支持多个，Json-Array格式", demo = "[\"13896177777\",\"13899999999\"]", ordinal = 3)
    private Set<String> mobileNos = Sets.newLinkedHashSet();

    /**
     * 发送类型
     * (默认模板方式发送)
     */
    @NotNull
    @OpenApiField(desc = "发送类型", constraint = "发送类型，默认template", demo = "template", ordinal = 4)
    private SmsSendType smsSendType = SmsSendType.template;

    /**
     * 模板编码
     * 短信服务定义模板编码，需配合后转换为渠道模板编码
     * smsSendType = SmsSendType.template 时有效和必选
     */
    @OpenApiFieldCondition("smsSendType = SmsSendType.template 时有效和必选")
    @OpenApiField(desc = "模板编码", constraint = "该编码需在短信服务后台先定义。", demo = "login_verify", ordinal = 5)
    private String templateCode;

    /**
     * 模板参数
     * smsSendType = SmsSendType.template 时有效
     */
    @OpenApiFieldCondition("smsSendType = SmsSendType.template 时有效")
    @OpenApiField(desc = "模板数据", constraint = "该编码需在短信服务后台先定义。", demo = "{\"code\":\"232321\"}", ordinal = 6)
    private Map<String, String> templateParams = Maps.newHashMap();


    /**
     * 直接发送内容
     * smsSendType = SmsSendType.content时有效和必选
     */
    @Size(max = 255)
    @OpenApiFieldCondition("smsSendType = SmsSendType.content时有效和必选")
    @OpenApiField(desc = "短信内容", constraint = "直接发送短信内容", demo = "你注册的短信验证码为:121212", ordinal = 7)
    private String content;

    /**
     * 签名
     * 可选，如果是模板方式发送，必须在第三方提供方预注册
     * 1. 如没有传入则使用系统默认配置的提供方的签名
     * 2. 如果有传入则使用你参数的为准
     */
    @OpenApiField(desc = "短信签名", constraint = "短信的内容签名，如果渠道需要，请预先在各渠道注册，否则采用默认签名", demo = "汽摩交易所", ordinal = 8)
    private String contentSign;

    /**
     * 客户端IP
     * 发送用户请求IP（注意，这里是终端客户IP）
     */
    @NotBlank
    @Size(max = 16)
    @OpenApiField(desc = "请求IP", constraint = "终端方用户设备的IP", demo = "222.111.222.1", ordinal = 9)
    private String clientIp;

    public SmsSendApiRequest() {
    }


    public SmsSendApiRequest(String appId, @NotEmpty Set<String> mobileNos, String templateCode, Map<String, String> templateParams, String contentSign, String clientIp) {
        this.appId = appId;
        this.mobileNos = mobileNos;
        this.templateCode = templateCode;
        this.templateParams = templateParams;
        this.contentSign = contentSign;
        this.clientIp = clientIp;
    }

    public SmsSendApiRequest(String appId, @NotEmpty Set<String> mobileNos, String templateCode, Map<String, String> templateParams) {
        this.appId = appId;
        this.mobileNos = mobileNos;
        this.templateCode = templateCode;
        this.templateParams = templateParams;
        this.contentSign = contentSign;
        this.clientIp = clientIp;
    }

    public SmsSendApiRequest(String appId, String mobileNo, String templateCode, Map<String, String> templateParams, String contentSign, String clientIp) {
        this.appId = appId;
        this.templateCode = templateCode;
        this.templateParams = templateParams;
        this.contentSign = contentSign;
        this.clientIp = clientIp;
        addMobileNo(mobileNo);
    }

    public SmsSendApiRequest(String appId, String mobileNo, String templateCode, Map<String, String> templateParams) {
        this.appId = appId;
        this.templateCode = templateCode;
        this.templateParams = templateParams;
        this.contentSign = contentSign;
        this.clientIp = clientIp;
        addMobileNo(mobileNo);
    }

    public SmsSendApiRequest(String appId, String templateCode, Map<String, String> templateParams, String contentSign) {
        this.appId = appId;
        this.templateCode = templateCode;
        this.templateParams = templateParams;
        this.contentSign = contentSign;
        this.smsSendType = SmsSendType.template;
    }

    public SmsSendApiRequest(String appId, @Size(max = 255) String content, String contentSign) {
        this.appId = appId;
        this.content = content;
        this.contentSign = contentSign;
        this.smsSendType = SmsSendType.content;
    }


    @Override
    public void check() {
        if (this.smsSendType == null) {
            Asserts.notNull(this.smsSendType, "smsSendType", "发送类型不能为空");
        }
        if (this.smsSendType == SmsSendType.template) {
            Asserts.notEmpty(this.templateCode, "模板编码");
        }
        if (this.smsSendType == SmsSendType.content) {
            Asserts.notEmpty(this.content, "短信内容");
        }
        for (String mobileNo : this.mobileNos) {
            if (!MobileNoPredicate.INSTANCE.apply(mobileNo)) {
                throw new ParameterException("手机号码格式错误");
            }
        }
        super.check();
    }

    public SmsSendApiRequest appId(String appId) {
        this.appId = appId;
        return this;
    }

    public SmsSendApiRequest addMobileNo(String mobileNo) {
        this.mobileNos.add(mobileNo);
        return this;
    }

    public SmsSendApiRequest addMobileNo(Collection<String> mobileNos) {
        this.mobileNos.addAll(mobileNos);
        return this;
    }

    public SmsSendApiRequest templateCode(String templateCode) {
        this.smsSendType = SmsSendType.template;
        this.templateCode = templateCode;
        return this;
    }

    public SmsSendApiRequest addTemplateParam(String key, String value) {
        this.smsSendType = SmsSendType.template;
        this.templateParams.put(key, value);
        return this;
    }

    public SmsSendApiRequest content(String content) {
        this.smsSendType = SmsSendType.content;
        this.content = content;
        return this;
    }

    public SmsSendApiRequest contentSign(String contentSign) {
        this.contentSign = contentSign;
        return this;
    }

    public SmsSendApiRequest clientIp(String clientIp) {
        this.clientIp = clientIp;
        return this;
    }

    public SmsSendApiRequest async(boolean async) {
        this.async = async;
        return this;
    }

    public static SmsSendApiRequest newRequest() {
        return new SmsSendApiRequest();
    }

}

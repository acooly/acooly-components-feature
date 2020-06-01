/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-13 12:04
 */
package com.acooly.module.smsend.facade.order;

import com.acooly.core.common.exception.ParameterException;
import com.acooly.core.common.facade.OrderBase;
import com.acooly.core.utils.Asserts;
import com.acooly.core.utils.validate.predicate.MobileNoPredicate;
import com.acooly.module.smsend.common.enums.SmsSendType;
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
 * 短信发送服务 请求对象
 *
 * @author zhangpu
 * @date 2020-05-13 12:04
 */
@Slf4j
@Getter
@Setter
public class SmsSendOrder extends OrderBase {

    /**
     * 发送请求号
     * （预留，暂无用）
     */
    @Size(max = 64)
    private String requestId;

    /**
     * 应用编码
     * 用于识别是那个系统或应用请求发送
     */
    @NotBlank
    private String appId;


    /**
     * 异步发送
     */
    private boolean async = true;


    @NotEmpty
    private Set<String> mobileNos = Sets.newLinkedHashSet();

    /**
     * 发送类型
     * (默认模板方式发送)
     */
    @NotNull
    private SmsSendType smsSendType = SmsSendType.template;

    /**
     * 模板编码
     * 短信服务定义模板编码，需配合后转换为渠道模板编码
     * smsSendType = SmsSendType.template 时有效和必选
     */
    private String templateCode;

    /**
     * 模板参数
     * smsSendType = SmsSendType.template 时有效
     */
    private Map<String, String> templateParams = Maps.newHashMap();


    /**
     * 直接发送内容
     * smsSendType = SmsSendType.content时有效和必选
     */
    @Size(max = 255)
    private String content;

    /**
     * 签名
     * 可选，如果是模板方式发送，必须在第三方提供方预注册
     * 1. 如没有传入则使用系统默认配置的提供方的签名
     * 2. 如果有传入则使用你参数的为准
     */
    private String contentSign;

    /**
     * 客户端IP
     * 发送用户请求IP（注意，这里是终端客户IP）
     */
    @Size(max = 16)
    private String clientIp;

    /**
     * 备注
     * 只存入数据库
     */
    @Size(max = 127)
    private String comments;

    public SmsSendOrder() {
    }

    public SmsSendOrder(String appId, @NotEmpty Set<String> mobileNos, String templateCode, Map<String, String> templateParams, String contentSign, String clientIp) {
        this.appId = appId;
        this.mobileNos = mobileNos;
        this.templateCode = templateCode;
        this.templateParams = templateParams;
        this.contentSign = contentSign;
        this.clientIp = clientIp;
    }

    public SmsSendOrder(String appId, @NotEmpty Set<String> mobileNos, String templateCode, Map<String, String> templateParams) {
        this.appId = appId;
        this.mobileNos = mobileNos;
        this.templateCode = templateCode;
        this.templateParams = templateParams;
        this.contentSign = contentSign;
        this.clientIp = clientIp;
    }

    public SmsSendOrder(String appId, String mobileNo, String templateCode, Map<String, String> templateParams, String contentSign, String clientIp) {
        this.appId = appId;
        this.templateCode = templateCode;
        this.templateParams = templateParams;
        this.contentSign = contentSign;
        this.clientIp = clientIp;
        addMobileNo(mobileNo);
    }

    public SmsSendOrder(String appId, String mobileNo, String templateCode, Map<String, String> templateParams) {
        this.appId = appId;
        this.templateCode = templateCode;
        this.templateParams = templateParams;
        this.contentSign = contentSign;
        this.clientIp = clientIp;
        addMobileNo(mobileNo);
    }

    public SmsSendOrder(String appId, String templateCode, Map<String, String> templateParams, String contentSign) {
        this.appId = appId;
        this.templateCode = templateCode;
        this.templateParams = templateParams;
        this.contentSign = contentSign;
        this.smsSendType = SmsSendType.template;
    }

    public SmsSendOrder(String appId, @Size(max = 255) String content, String contentSign) {
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


    public SmsSendOrder appId(String appId) {
        this.appId = appId;
        return this;
    }

    public SmsSendOrder addMobileNo(String mobileNo) {
        this.mobileNos.add(mobileNo);
        return this;
    }

    public SmsSendOrder addMobileNo(Collection<String> mobileNos) {
        this.mobileNos.addAll(mobileNos);
        return this;
    }

    public SmsSendOrder templateCode(String templateCode) {
        this.smsSendType = SmsSendType.template;
        this.templateCode = templateCode;
        return this;
    }

    public SmsSendOrder addTemplateParam(String key, String value) {
        this.smsSendType = SmsSendType.template;
        this.templateParams.put(key, value);
        return this;
    }

    public SmsSendOrder content(String content) {
        this.smsSendType = SmsSendType.content;
        this.content = content;
        return this;
    }

    public SmsSendOrder contentSign(String contentSign) {
        this.contentSign = contentSign;
        return this;
    }

    public SmsSendOrder clientIp(String clientIp) {
        this.clientIp = clientIp;
        return this;
    }

    public SmsSendOrder async(boolean async) {
        this.async = async;
        return this;
    }

    public static SmsSendOrder newOrder() {
        return new SmsSendOrder();
    }
}

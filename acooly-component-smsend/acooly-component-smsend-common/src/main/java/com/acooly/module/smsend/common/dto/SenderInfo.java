/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-17 19:55
 */
package com.acooly.module.smsend.common.dto;

import com.acooly.core.common.facade.InfoBase;
import com.acooly.core.utils.Asserts;
import com.acooly.module.smsend.common.enums.SmsSendType;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangpu
 * @date 2020-05-17 19:55
 */
@Getter
@Setter
public class SenderInfo extends InfoBase {
    /**
     * 发送请求号
     */
    @Size(max = 64)
    private String requestId;


    /**
     * 应用编码
     * 用于识别是那个系统或应用请求发送
     */
    private String appId = "default";


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
     */
    @Size(max = 16)
    private String clientIp;

    /**
     * 备注
     */
    /**
     * 备注
     */
    @Size(max = 127)
    private String comments;


    public SenderInfo() {
    }


    public SenderInfo(String appId, @NotEmpty Set<String> mobileNos, String templateCode, Map<String, String> templateParams, String contentSign, String clientIp) {
        this.appId = appId;
        this.mobileNos = mobileNos;
        this.templateCode = templateCode;
        this.templateParams = templateParams;
        this.contentSign = contentSign;
        this.clientIp = clientIp;
    }

    public SenderInfo(String appId, String mobileNo, String templateCode, Map<String, String> templateParams, String contentSign, String clientIp) {
        this.appId = appId;
        this.templateCode = templateCode;
        this.templateParams = templateParams;
        this.contentSign = contentSign;
        this.clientIp = clientIp;
        addMobileNo(mobileNo);
    }

    public SenderInfo(String appId, String templateCode, Map<String, String> templateParams, String contentSign) {
        this.appId = appId;
        this.templateCode = templateCode;
        this.templateParams = templateParams;
        this.contentSign = contentSign;
        this.smsSendType = SmsSendType.template;
    }

    public SenderInfo(String appId, @Size(max = 255) String content, String contentSign) {
        this.appId = appId;
        this.content = content;
        this.contentSign = contentSign;
        this.smsSendType = SmsSendType.content;
    }

    public void addMobileNo(String mobileNo) {
        this.mobileNos.add(mobileNo);
    }

    public void addMobileNo(Collection<String> mobileNos) {
        this.mobileNos.addAll(mobileNos);
    }

    @Override
    public void check() {
        if (smsSendType == null) {
            Asserts.notNull(smsSendType, "smsSendType", "发送类型不能为空");
        }
        if (smsSendType == SmsSendType.template) {
            Asserts.notEmpty(this.templateCode, "模板编码");
        }
        if (smsSendType == SmsSendType.content) {
            Asserts.notEmpty(this.content, "短信内容");
        }
        super.check();
    }

}

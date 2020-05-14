/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-05-13 12:04
 */
package com.acooly.module.smsend.facade.order;

import com.acooly.core.common.facade.OrderBase;
import com.acooly.core.utils.Asserts;
import com.acooly.module.smsend.enums.SmsSendType;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;

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
     */
    @Size(max = 64)
    private String requestId;


    /**
     * 应用编码
     * 用于识别是那个系统或应用请求发送
     */
    private String appId = "default";

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
    @NotBlank
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

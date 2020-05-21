/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by acooly
 * date:2020-05-08
 */
package com.acooly.module.smsend.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.smsend.common.enums.SmsProvider;
import com.acooly.module.smsend.common.enums.SmsSendStatus;
import com.acooly.module.smsend.common.enums.SmsSendType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 短信发送记录 Entity
 *
 * @author acooly
 * @date 2020-05-08 14:32:47
 */
@Entity
@Table(name = "sms_send_log")
@Getter
@Setter
public class SmsSendLog extends AbstractEntity implements Cloneable {

    /**
     * 应用编码
     * 用于识别是那个系统或应用请求发送
     */
    private String appId;

    /**
     * request_id
     */
    @Size(max = 64)
    private String requestId;

    /**
     * 批次号
     */
    @Size(max = 32)
    private String batchNo;

    /**
     * 手机号码
     */
    @NotBlank
    @Size(max = 16)
    private String mobileNo;

    /**
     * 发送类型
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private SmsSendType sendType;

    /**
     * 短信内容
     */
    @NotBlank
    @Size(max = 255)
    private String content;

    /**
     * 模板编码
     * smsSendType = SmsSendType.template 时有效和必选
     */
    private String templateCode;

    /**
     * 模板参数
     * smsSendType = SmsSendType.template 时有效
     * Json格式
     */
    private String templateJsonParams;


    /**
     * 发送时间
     */
    @NotNull
    private Date sendTime;

    /**
     * 提供方
     */
    @Enumerated(EnumType.STRING)
    private SmsProvider provider;

    /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 消息编码
     */
    @Size(max = 32)
    private String resultCode;

    /**
     * 消息内容
     */
    @Size(max = 2048)
    private String resultMessage;

    /**
     * 客户IP
     */
    @Size(max = 16)
    private String clientIp;

    /**
     * 状态
     */
    @NotNull
    private SmsSendStatus status = SmsSendStatus.WAIT;

    /**
     * 备注
     */
    @Size(max = 256)
    private String comments;

    /**
     * 渠道模板编码
     */
    @Size(max = 32)
    private String templateProvider;

    @Override
    public SmsSendLog clone() throws CloneNotSupportedException {
        return (SmsSendLog) super.clone();
    }
}

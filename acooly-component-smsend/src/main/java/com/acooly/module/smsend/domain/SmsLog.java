package com.acooly.module.smsend.domain;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.smsend.enums.SmsProvider;
import com.acooly.module.smsend.enums.SmsendStatus;
import com.acooly.module.smsend.enums.SmsSendType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Date;

/**
 * 短信发送记录 Entity
 *
 * <p>Date: 2013-08-05 22:28:35
 *
 * @author Acooly Code Generator
 */
@Entity
@Table(name = "sms_log")
@Getter
@Setter
public class SmsLog extends AbstractEntity {

    /**
     * 发送批次
     */
    private String batchNo;
    /**
     * 手机号码
     */
    private String mobileNo;

    /**
     * 发送类型
     */
    private SmsSendType sendType;

    /**
     * 短信内容
     */
    private String content;


    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 模板数据(JSON)
     */
    private String templateData;


    private String system;

    /**
     * 发送时间
     */
    private Date sendTime = new Date();
    /**
     * 定时发送时间(预留，暂不支持)
     */
    private Date timerTime;
    /**
     * 短信发送提供商
     */
    @Enumerated(EnumType.STRING)
    private SmsProvider provider;

    /**
     * 提供商状态
     */
    private String providerStatus;
    /**
     * 提供商说明
     */
    private String providerMemo;

    /**
     * 状态
     */
    private SmsendStatus status = SmsendStatus.WAIT;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 备注
     */
    private String comments;

    public SmsLog() {
        super();
    }

    public SmsLog(String mobileNo, String content) {
        super();
        this.mobileNo = mobileNo;
        this.content = content;
    }
}

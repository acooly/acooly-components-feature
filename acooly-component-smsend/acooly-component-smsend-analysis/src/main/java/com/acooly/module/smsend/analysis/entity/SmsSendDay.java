/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by zhangpu
 * date:2020-07-26
 */
package com.acooly.module.smsend.analysis.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.utils.Money;
import com.acooly.module.smsend.common.enums.SmsProvider;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.annotation.Order;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 发送统计 Entity
 *
 * @author zhangpu
 * @date 2020-07-26 14:13:43
 */
@Entity
@Table(name = "sms_send_day")
@Getter
@Setter
public class SmsSendDay extends AbstractEntity {

    /**
     * 应用ID
     */
    @Size(max = 45)
    @Order(2)
    private String appId;

    /**
     * 提供方
     */
    @Order(3)
    @Enumerated(EnumType.STRING)
    private SmsProvider provider;

    /**
     * 日期
     */
    @Order(1)
    private Date period;

    /**
     * 发送数
     */
    @Order(4)
    private Integer count;

    /**
     * 费用
     */
    @Order(5)
    private Money amount;

    /**
     * 备注
     */
    @Order(6)
    @Size(max = 256)
    private String comments;

}

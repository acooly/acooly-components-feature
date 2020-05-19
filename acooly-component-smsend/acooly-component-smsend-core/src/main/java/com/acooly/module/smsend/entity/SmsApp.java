/*
* acooly.cn Inc.
* Copyright (c) 2020 All Rights Reserved.
* create by acooly
* date:2020-05-19
*/
package com.acooly.module.smsend.entity;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import com.acooly.core.common.domain.AbstractEntity;
import java.util.Date;
import com.acooly.core.utils.enums.SimpleStatus;

/**
 * 短信发送应用 Entity
 *
 * @author acooly
 * @date 2020-05-19 15:03:06
 */
@Entity
@Table(name = "sms_app")
@Getter
@Setter
public class SmsApp extends AbstractEntity {

    /**
     * 应用ID
     */
	@Size(max = 32)
    private String appId;

    /**
     * 应用名称
     */
	@Size(max = 32)
    private String appName;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    private SimpleStatus status;

    /**
     * 备注
     */
	@Size(max = 127)
    private String comments;

}

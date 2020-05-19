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

import com.acooly.module.smsend.common.enums.SmsProvider;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import com.acooly.core.common.domain.AbstractEntity;
import java.util.Date;

/**
 * 模板渠道 Entity
 *
 * @author acooly
 * @date 2020-05-19 15:03:06
 */
@Entity
@Table(name = "sms_template_provider")
@Getter
@Setter
public class SmsTemplateProvider extends AbstractEntity {

    /**
     * 模板编码
     */
	@NotBlank
	@Size(max = 32)
    private String templateCode;

    /**
     * 渠道
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private SmsProvider provider;

    /**
     * 渠道模板编码
     */
	@NotBlank
	@Size(max = 64)
    private String providerTemplateCode;

    /**
     * 备注
     */
	@Size(max = 127)
    private String comments;

}

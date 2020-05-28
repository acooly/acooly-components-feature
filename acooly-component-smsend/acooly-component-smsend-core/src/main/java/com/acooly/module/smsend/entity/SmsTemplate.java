/*
* acooly.cn Inc.
* Copyright (c) 2020 All Rights Reserved.
* create by acooly
* date:2020-05-19
*/
package com.acooly.module.smsend.entity;


import com.acooly.core.common.domain.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 短信模板 Entity
 *
 * @author acooly
 * @date 2020-05-19 15:03:05
 */
@Entity
@Table(name = "sms_template")
@Getter
@Setter
public class SmsTemplate extends AbstractEntity {


    /**
     * 应用ID
     */
    @Size(max = 32)
    @NotBlank
    private String appId;

    /**
     * 应用名称
     */
    @Size(max = 32)
    private String appName;

    /**
     * 模板编码
     */
	@NotBlank
	@Size(max = 32)
    private String templateCode;

    /**
     * 模板名称
     */
	@NotBlank
	@Size(max = 45)
    private String templateName;

    /**
     * 模板内容
     */
	@Size(max = 127)
    private String templateContent;

    /**
     * 备注
     */
	@Size(max = 127)
    private String comments;

}

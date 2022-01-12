/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by zhangpu
 * date:2022-01-11
 */
package com.acooly.module.mail.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.*;

import javax.persistence.Transient;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import com.acooly.core.common.domain.AbstractEntity;

import java.util.Date;

/**
 * 邮件发送记录 Entity
 *
 * @author zhangpu
 * @date 2022-01-11 09:34:49
 */
@Entity
@Table(name = "email_record")
@Getter
@Setter
public class EmailRecord extends AbstractEntity {

    /**
     * 模板名称
     */
    @Size(max = 32)
    private String templateName;

    /**
     * 模板标题
     */
    @Size(max = 32)
    private String templateTitle;

    /**
     * 主题
     */
    @Size(max = 256)
    private String subject;

    /**
     * 内容
     */
    @Transient
    private String content;

    /**
     * 发送人地址
     */
    @NotBlank
    @Size(max = 64)
    private String fromAddress;

    /**
     * 发送人
     */
    @Size(max = 32)
    private String fromName;

    /**
     * 收件人地址列表
     */
    @NotBlank
    @Size(max = 1024)
    private String toAddressList;

    /**
     * 备注
     */
    @Size(max = 255)
    private String comments;

}

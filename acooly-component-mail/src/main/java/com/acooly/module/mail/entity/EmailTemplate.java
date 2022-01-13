/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-04-27
 */
package com.acooly.module.mail.entity;

import com.acooly.core.common.domain.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * @author shuijing
 */
@Getter
@Setter
@Entity
@Table(name = "email_template")
public class EmailTemplate extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 模版名称
     */
    @NotBlank
    private String name;

    @NotBlank
    private String title;

    /**
     * 模板邮件主题
     */
    @NotBlank
    private String subject;

    /**
     * 模板邮件内容
     */
    @NotBlank
    private String content;
}

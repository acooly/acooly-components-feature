/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by zhangpu
 * date:2022-01-11
 */
package com.acooly.module.mail.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.*;

import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import com.acooly.core.common.domain.AbstractEntity;

import java.util.Date;

/**
 * 邮件发送内容 Entity
 *
 * @author zhangpu
 * @date 2022-01-11 09:34:49
 */
@Entity
@Table(name = "email_record_content")
@Getter
@Setter
@NoArgsConstructor
public class EmailRecordContent extends AbstractEntity {

    /**
     * 邮件内容
     */
    private String content;

    public EmailRecordContent(Long id, String content) {
        setId(id);
        this.content = content;
    }
}

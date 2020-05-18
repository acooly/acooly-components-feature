/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by shuijing
 * date:2017-08-01
 */
package com.acooly.module.smsend.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.utils.enums.SimpleStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 短信黑名单 Entity
 *
 * @author zhangpu
 * Date: 2020-05-06
 */
@Entity
@Table(name = "sms_black_list")
@Getter
@Setter
public class SmsBlackList extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;


    /**
     * mobile
     */
    @NotBlank
    @Size(max = 20)
    private String mobile;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private SimpleStatus status;

    /**
     * description
     */
    @Size(max = 512)
    private String description;

}

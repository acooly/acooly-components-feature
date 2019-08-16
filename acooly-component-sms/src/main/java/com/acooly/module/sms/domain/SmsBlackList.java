/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by shuijing
 * date:2017-08-01
 */
package com.acooly.module.sms.domain;


import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.sms.enums.StatusEnum;

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
 * @author shuijing
 * Date: 2017-08-01 17:28:24
 */
@Entity
@Table(name = "sms_black_list")
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
    private StatusEnum status;

    /**
     * description
     */
    @Size(max = 512)
    private String description;


    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public StatusEnum getStatus() {
        return this.status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}

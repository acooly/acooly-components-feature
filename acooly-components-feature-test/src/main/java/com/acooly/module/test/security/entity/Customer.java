/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by zhangpu
 * date:2020-04-06
 */
package com.acooly.module.test.security.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.common.enums.Gender;
import com.acooly.core.utils.Money;
import com.acooly.module.test.security.enums.CustomerTypeEnum;
import com.acooly.module.test.security.enums.IdcardTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * acoolycoder测试 Entity
 *
 * @author zhangpu
 * Date: 2020-04-06 23:23:46
 */
@Entity
@Table(name = "dm_customer")
@Getter
@Setter
public class Customer extends AbstractEntity {

    /**
     * 用户名
     */
    @NotEmpty
    @Size(max = 32)
    private String username;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 生日
     */
    @NotNull
    private Date birthday;

    /**
     * 性别
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    /**
     * 姓名
     */
    @NotEmpty
    @Size(max = 16)
    private String realName;

    /**
     * 证件类型
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private IdcardTypeEnum idcardType;

    /**
     * 身份证号码
     */
    @NotEmpty
    @Size(max = 48)
    private String idcardNo;

    /**
     * 手机号码
     */
    @Size(max = 11)
    private String mobileNo;

    /**
     * 邮件
     */
    @Size(max = 64)
    private String mail;

    /**
     * 照片
     */
    @Size(max = 128)
    private String photo;

    /**
     * 照片缩略图
     */
    @Size(max = 128)
    private String photoThum;

    /**
     * 摘要
     */
    @Size(max = 64)
    private String subject;

    /**
     * 客户类型
     */
    @Enumerated(EnumType.STRING)
    private CustomerTypeEnum customerType;

    /**
     * 薪水
     */
    private Money salary;

    /**
     * 手续费
     */
    private BigDecimal fee;

    /**
     * 测试Text类型
     */
    @Size(max = 100000)
    private String content;


    /**
     * 状态
     */
    @NotNull
    private Integer status;

    /**
     * 备注
     */
    @Size(max = 255)
    private String comments;

}

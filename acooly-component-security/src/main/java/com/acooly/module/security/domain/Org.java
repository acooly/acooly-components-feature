/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by shuijing
 * date:2017-05-26
 */
package com.acooly.module.security.domain;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.security.enums.OrgStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 组织机构 Entity
 *
 * @author shuijing
 */
@Getter
@Setter
@Entity
@Table(name = "sys_org")
public class Org extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 父类id
     */
    private Long parentId;

    /**
     * 组织名称
     */
    private String name;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    private OrgStatus status;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 县
     */
    private String county;

    /**
     * 手机号
     */
    private String mobileNo;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 固定电话
     */
    private String telephone;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String memo;

    /**
     * 机构状态展示字段
     */
    @Transient
    private String statusView;

    /**
     * 银行名称展示字段
     */
    @Transient
    private String bankViewName;
    /**
     * 树形下拉展示
     */
    @Transient
    private String text;

    @Transient
    private List<Org> children;
}

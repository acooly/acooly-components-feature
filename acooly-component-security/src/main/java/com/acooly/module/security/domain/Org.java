/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by shuijing
 * date:2017-05-26
 */
package com.acooly.module.security.domain;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.common.domain.Sortable;
import com.acooly.module.security.enums.OrgStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
public class Org extends AbstractEntity implements Sortable, Comparable<Org> {

    private static final long serialVersionUID = 1L;

    /**
     * 父类id
     */
    @Column(name = "parent_id", nullable = false)
    private Long parentId;

    /**
     * 组织名称
     */
    @NotBlank
    @Size(max = 32)
    @Column(name = "name", nullable = false, length = 32)
    private String name;

    /**
     * 管理用户
     */
    @Column(name = "username", length = 32)
    private String username;

    /**
     * 状态
     */
    @Column(name = "status", length = 16)
    @Enumerated(EnumType.STRING)
    private OrgStatus status;

    /**
     * 省
     */
    @Column(name = "province", length = 32)
    private String province;

    /**
     * 市
     */
    @Column(name = "city", length = 32)
    private String city;

    /**
     * 县
     */
    @Column(name = "county", length = 32)
    private String county;

    /**
     * 手机号
     */
    @Column(name = "mobile_no", length = 20)
    private String mobileNo;

    /**
     * 邮箱
     */
    @Column(name = "email", length = 128)
    private String email;

    /**
     * 地址
     */
    @Column(name = "address", length = 255)
    private String address;

    /**
     * 排序值
     */
    @Column(name = "sort_time")
    private Long sortTime;

    /**
     * 联系人
     */
    @Column(name = "contacts", length = 64)
    private String contacts;

    /**
     * 固定电话
     */
    @Column(name = "telephone", length = 20)
    private String telephone;

    /**
     * 备注
     */
    @Column(name = "memo", length = 255)
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


    /**
     * <0：当前对象比传入对象小。
     * =0：当前对象等于传入对象。
     * >0：当前对象比传入对象大。
     *
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Org o) {
        if (this.getSortTime() == null || o.getSortTime() == null) {
            return 0;
        }
        if (this.getSortTime().equals(o.getSortTime())) {
            return 0;
        }
        return this.getSortTime() > o.getSortTime() ? 1 : -1;
    }
}

/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by zhangpu
 * date:2022-10-05
 */
package cn.acooly.component.rbac.entity;


import com.acooly.core.common.domain.AbstractEntity;

import cn.acooly.component.rbac.enums.RoleType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 角色表 Entity
 *
 * @author zhangpu
 * @date 2022-10-05 23:03:16
 */
@Entity
@Table(name = "rbac_role")
@Getter
@Setter
public class RbacRole extends AbstractEntity {

    /**
     * 角色名称
     */
    @NotBlank
    @Size(max = 64)
    private String name;

    /**
     * 角色标题
     */
    @NotBlank
    @Size(max = 64)
    private String title;

    /**
     * 角色说明
     */
    @Size(max = 256)
    private String memo;
    
    /**
     * 角色类型
     */
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    
    /**
     * 当角色类型为私有时，归属三方会员的编码
     */
    private String belongCode;

    /**
     * 角色所属的资源权限
     */
    @Transient
    private List<RbacResource> rbacResources;

}

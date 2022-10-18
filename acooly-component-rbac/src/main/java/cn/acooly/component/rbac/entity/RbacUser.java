/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by zhangpu
 * date:2022-10-05
 */
package cn.acooly.component.rbac.entity;


import cn.acooly.component.rbac.enums.UserStatus;
import com.acooly.core.common.domain.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 用户表 Entity
 *
 * @author zhangpu
 * @date 2022-10-05 23:03:16
 */
@Entity
@Table(name = "rbac_user")
@Getter
@Setter
public class RbacUser extends AbstractEntity {

    /**
     * 用户名
     */
    @NotBlank
    @Size(max = 32)
    private String username;

    /**
     * 会员编码
     * （冗余，可选）
     */
    @Size(max = 32)
    private String memberNo;

    /**
     * 姓名
     */
    @Size(max = 32)
    private String realName;

    /**
     * 姓名拼音
     */
    @Size(max = 32)
    private String pinyin;

    /**
     * 登录密码
     */
    @NotBlank
    @Size(max = 128)
    private String password;

    /**
     * 密码加盐
     */
    @Size(max = 32)
    private String salt;

    /**
     * 用户类型
     */
    @NotNull
    private Integer userType;

    /**
     * 邮件
     */
    @Size(max = 64)
    private String email;

    /**
     * 手机号码
     */
    @Size(max = 20)
    private String mobileNo;

    /**
     * 组织ID
     */
    private Long orgId;

    /**
     * 组织名称
     */
    @Size(max = 128)
    private String orgName;

    /**
     * 密码过期时间
     */
    private Date expireTime;

    /**
     * 解锁时间
     */
    private Date unlockTime;

    /**
     * 登录失败次数
     */
    private Integer loginFailCount;

    /**
     * 最后登录时间
     */
    private Date loginTime;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    private UserStatus status;

    /**
     * 描述
     */
    @Size(max = 256)
    private String memo;


    /**
     * 用户绑定的角色
     */
    @Transient
    private List<String> roleNames;

}

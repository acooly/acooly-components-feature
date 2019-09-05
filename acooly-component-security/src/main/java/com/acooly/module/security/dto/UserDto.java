/*
 * www.acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * zhangpu@acooly.cn 2017-08-10 17:55 创建
 */
package com.acooly.module.security.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 系统用户 查询Dto
 *
 * @author zhangpu 2017-08-10 17:55
 */
@Getter
@Setter
public class UserDto {

    private Long id;

    private String username;

    private String realName;

    private String email;

    private String mobileNo;

    private int userType;

    private Date createTime = new Date();

    private Date lastModifyTime = new Date();

    private Date unlockTime;

    private int status;

    private String descn;

    private Long orgId;

    private String orgName;

    private Long roleId;

    private String roleName;

    private String roleDescn;

    private String rolesIdStr;
}

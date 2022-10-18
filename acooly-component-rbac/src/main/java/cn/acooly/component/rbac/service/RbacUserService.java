/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by zhangpu
 * date:2022-10-05
 *
 */
package cn.acooly.component.rbac.service;

import cn.acooly.component.rbac.entity.RbacUser;
import com.acooly.core.common.service.EntityService;

import java.util.List;

/**
 * 用户表 Service接口
 *
 * @author zhangpu
 * @date 2022-10-05 23:03:16
 */
public interface RbacUserService extends EntityService<RbacUser> {

    /**
     * 据登录名获取用户
     *
     * @param username
     * @return
     */
    RbacUser findUserByUsername(String username);

    /**
     * 根据会员编码获取用户
     *
     * @param memberNo
     * @return
     */
    RbacUser findUserByMemberNo(String memberNo);

    /**
     * 保存用户信息
     *
     * @param rbacUser
     */
    RbacUser saveUser(RbacUser rbacUser);


    /**
     * 变更用户角色
     * @param memberNo
     * @param roleNames
     */
    void updateUserRoles(String memberNo, List<String> roleNames);

}

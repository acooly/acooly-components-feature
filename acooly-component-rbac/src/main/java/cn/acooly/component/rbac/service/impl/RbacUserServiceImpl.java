/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by zhangpu
 * date:2022-10-05
 */
package cn.acooly.component.rbac.service.impl;

import cn.acooly.component.rbac.dao.RbacUserDao;
import cn.acooly.component.rbac.entity.RbacRole;
import cn.acooly.component.rbac.entity.RbacUser;
import cn.acooly.component.rbac.entity.RbacUserRole;
import cn.acooly.component.rbac.service.RbacRoleService;
import cn.acooly.component.rbac.service.RbacUserRoleService;
import cn.acooly.component.rbac.service.RbacUserService;
import com.acooly.core.common.service.EntityServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户表 Service实现
 *
 * @author zhangpu
 * @date 2022-10-05 23:03:16
 */
@Service
public class RbacUserServiceImpl extends EntityServiceImpl<RbacUser, RbacUserDao> implements RbacUserService {

    @Autowired
    private RbacUserRoleService rbacUserRoleService;
    @Autowired
    private RbacRoleService rbacRoleService;

    @Override
    public RbacUser findUserByUsername(String username) {
        return getEntityDao().findByUsername(username);
    }


    @Override
    public RbacUser findUserByMemberNo(String memberNo) {
        return getEntityDao().findByMemberNo(memberNo);
    }

    /**
     * 保存用户信息
     *
     * @param rbacUser
     */
    @Override
    @Transactional
    public RbacUser saveUser(RbacUser rbacUser) {
        //保存用户信息
        save(rbacUser);

        //用户角色关系
        List<String> roleNames = rbacUser.getRoleNames();
        List<RbacUserRole> userRoles = Lists.newArrayList();
        for (String roleName : roleNames) {
            RbacRole rbacRole = rbacRoleService.findByName(roleName);
            RbacUserRole userRole = new RbacUserRole();
            userRole.setUserId(rbacUser.getId());
            userRole.setRoleId(rbacRole.getId());
            userRoles.add(userRole);
        }
        rbacUserRoleService.saves(userRoles);
        return rbacUser;
    }

    /**
     * 变更用户角色
     *
     * @param memberNo
     * @param roleNames
     */
    @Override
    @Transactional
    public void updateUserRoles(String memberNo, List<String> roleNames) {
        RbacUser rbacUser = getEntityDao().findByMemberNo(memberNo);
        rbacUserRoleService.removeByUserId(rbacUser.getId());

        List<RbacUserRole> userRoles = Lists.newArrayList();
        for (String roleName : roleNames) {
            RbacRole rbacRole = rbacRoleService.findByName(roleName);
            RbacUserRole userRole = new RbacUserRole();
            userRole.setUserId(rbacUser.getId());
            userRole.setRoleId(rbacRole.getId());
            userRoles.add(userRole);
        }
        rbacUserRoleService.saves(userRoles);
    }
}

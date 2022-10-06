/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by zhangpu
 * date:2022-10-05
 */
package cn.acooly.component.rbac.service.impl;

import cn.acooly.component.rbac.dao.RbacRoleDao;
import cn.acooly.component.rbac.entity.RbacRole;
import cn.acooly.component.rbac.service.RbacResourceService;
import cn.acooly.component.rbac.service.RbacRoleService;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Collections3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色表 Service实现
 *
 * @author zhangpu
 * @date 2022-10-05 23:03:16
 */
@Service
public class RbacRoleServiceImpl extends EntityServiceImpl<RbacRole, RbacRoleDao> implements RbacRoleService {

    @Autowired
    private RbacResourceService rbacResourceService;

    @Override
    public List<RbacRole> getRolesCascadeResources(Long userId) {
        List<RbacRole> rbacRoles = getEntityDao().findByUserId(userId);
        if (Collections3.isNotEmpty(rbacRoles)) {
            for (RbacRole rbacRole : rbacRoles) {
                rbacRole.setRbacResources(rbacResourceService.findByRole(rbacRole.getId()));
            }
        }
        return rbacRoles;
    }
}

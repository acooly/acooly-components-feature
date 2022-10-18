/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by acooly
 * date:2022-10-12
 */
package cn.acooly.component.rbac.service.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import cn.acooly.component.rbac.service.RbacUserRoleService;
import cn.acooly.component.rbac.dao.RbacUserRoleDao;
import cn.acooly.component.rbac.entity.RbacUserRole;

import java.util.List;

/**
 * 用户角色表 Service实现
 *
 * @author acooly
 * @date 2022-10-12 16:36:28
 */
@Service("rbacUserRoleService")
public class RbacUserRoleServiceImpl extends EntityServiceImpl<RbacUserRole, RbacUserRoleDao> implements RbacUserRoleService {

    @Override
    public List<RbacUserRole> findByUserId(long userId) {
        return getEntityDao().findByUserId(userId);
    }

    @Override
    public void removeByUserId(long userId) {
        getEntityDao().removeByUserId(userId);
    }
}

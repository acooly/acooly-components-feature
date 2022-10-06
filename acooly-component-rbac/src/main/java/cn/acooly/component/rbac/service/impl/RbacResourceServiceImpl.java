/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by zhangpu
 * date:2022-10-05
 */
package cn.acooly.component.rbac.service.impl;

import cn.acooly.component.rbac.dao.RbacResourceDao;
import cn.acooly.component.rbac.entity.RbacResource;
import cn.acooly.component.rbac.service.RbacResourceService;
import com.acooly.core.common.service.EntityServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限资源表 Service实现
 *
 * @author zhangpu
 * @date 2022-10-05 23:03:16
 */
@Service
public class RbacResourceServiceImpl extends EntityServiceImpl<RbacResource, RbacResourceDao> implements RbacResourceService {

    @Override
    public List<RbacResource> findByRole(Long roleId) {
        return getEntityDao().findByRoleId(roleId);
    }
}

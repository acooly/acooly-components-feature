/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by zhangpu
 * date:2022-10-05
 */
package cn.acooly.component.rbac.service.impl;

import cn.acooly.component.rbac.dao.RbacResourceDao;
import cn.acooly.component.rbac.dto.RbacResourceNode;
import cn.acooly.component.rbac.entity.RbacResource;
import cn.acooly.component.rbac.service.RbacResourceService;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.arithmetic.tree.QuickTree;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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

    @Override
    public List<RbacResourceNode> getAuthorizedResourceNode(Long userId) {
        List<RbacResourceNode> resources = getEntityDao().getAuthorizedResourceNodeWithUserId(userId);
        return QuickTree.quickTree(resources, null,
                Comparator.nullsLast(Comparator.comparing(RbacResourceNode::getOrderTime).reversed()));
    }

}

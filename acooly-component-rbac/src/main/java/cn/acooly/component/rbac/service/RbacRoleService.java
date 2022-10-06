/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by zhangpu
 * date:2022-10-05
 *
 */
package cn.acooly.component.rbac.service;

import cn.acooly.component.rbac.entity.RbacRole;
import com.acooly.core.common.service.EntityService;

import java.util.List;

/**
 * 角色表 Service接口
 *
 * @author zhangpu
 * @date 2022-10-05 23:03:16
 */
public interface RbacRoleService extends EntityService<RbacRole> {

    /**
     * 根据用户ID获取所有的授权角色及角色的所有资源权限
     *
     * @param userId
     * @return
     */
    List<RbacRole> getRolesCascadeResources(Long userId);

}

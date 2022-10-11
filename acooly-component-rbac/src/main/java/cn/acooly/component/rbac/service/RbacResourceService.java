/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by zhangpu
 * date:2022-10-05
 *
 */
package cn.acooly.component.rbac.service;

import cn.acooly.component.rbac.dto.RbacResourceNode;
import cn.acooly.component.rbac.entity.RbacResource;
import com.acooly.core.common.service.EntityService;

import java.util.List;

/**
 * 权限资源表 Service接口
 *
 * @author zhangpu
 * @date 2022-10-05 23:03:16
 */
public interface RbacResourceService extends EntityService<RbacResource> {

    /**
     * 根据roleId查询资源
     *
     * @param roleId
     * @return
     */
    List<RbacResource> findByRole(Long roleId);


    /**
     * 获取用户授权的资源树
     * 这里为提高性能，暂时只提供userId的查询，如需username或memberNo的查询，请从会话中获取userId
     * `RbacShiros.getSessionUser().getId()`
     *
     * @param userId
     * @return
     */
    List<RbacResourceNode> getAuthorizedResourceNode(Long userId);
}

/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by zhangpu
 * date:2022-10-05
 *
 */
package cn.acooly.component.rbac.service;

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

}

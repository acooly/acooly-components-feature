/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by acooly
 * date:2022-10-12
 *
 */
package cn.acooly.component.rbac.service;

import com.acooly.core.common.service.EntityService;
import cn.acooly.component.rbac.entity.RbacUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色表 Service接口
 *
 * @author acooly
 * @date 2022-10-12 16:36:28
 */
public interface RbacUserRoleService extends EntityService<RbacUserRole> {


    List<RbacUserRole> findByUserId(long userId);


    void removeByUserId(long userId);
}

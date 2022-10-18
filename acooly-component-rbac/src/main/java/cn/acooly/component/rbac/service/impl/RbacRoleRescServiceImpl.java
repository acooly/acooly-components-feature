/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by acooly
 * date:2022-10-12
 */
package cn.acooly.component.rbac.service.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import cn.acooly.component.rbac.service.RbacRoleRescService;
import cn.acooly.component.rbac.dao.RbacRoleRescDao;
import cn.acooly.component.rbac.entity.RbacRoleResc;

/**
 * 角色权限表 Service实现
 *
 * @author acooly
 * @date 2022-10-12 16:36:28
 */
@Service("rbacRoleRescService")
public class RbacRoleRescServiceImpl extends EntityServiceImpl<RbacRoleResc, RbacRoleRescDao> implements RbacRoleRescService {

}

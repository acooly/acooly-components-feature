/*
* acooly.cn Inc.
* Copyright (c) 2022 All Rights Reserved.
* create by zhangpu
* date:2022-10-05
*/
package cn.acooly.component.rbac.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJsonEntityController;
import cn.acooly.component.rbac.entity.RbacRole;
import cn.acooly.component.rbac.service.RbacRoleService;

/**
 * 角色表 管理控制器
 *
 * @author zhangpu
 * @date 2022-10-05 23:03:16
 */
@Controller
@RequestMapping(value = "/manage/rbac/role")
public class RbacRoleManagerController extends AbstractJsonEntityController<RbacRole, RbacRoleService> {


	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private RbacRoleService rbacRoleService;







}

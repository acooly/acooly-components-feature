/*
* acooly.cn Inc.
* Copyright (c) 2022 All Rights Reserved.
* create by acooly
* date:2022-10-12
*/
package cn.acooly.component.rbac.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJsonEntityController;
import cn.acooly.component.rbac.entity.RbacUserRole;
import cn.acooly.component.rbac.service.RbacUserRoleService;

import com.google.common.collect.Maps;

/**
 * 用户角色表 管理控制器
 *
 * @author acooly
 * @date 2022-10-12 16:36:28
 */
@Controller
@RequestMapping(value = "/manage/rbac/rbacUserRole")
public class RbacUserRoleManagerController extends AbstractJsonEntityController<RbacUserRole, RbacUserRoleService> {


	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private RbacUserRoleService rbacUserRoleService;




}

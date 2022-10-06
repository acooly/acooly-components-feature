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
import cn.acooly.component.rbac.entity.RbacOrg;
import cn.acooly.component.rbac.service.RbacOrgService;

/**
 * 组织机构 管理控制器
 *
 * @author zhangpu
 * @date 2022-10-05 23:03:16
 */
@Controller
@RequestMapping(value = "/manage/rbac/org")
public class RbacOrgManagerController extends AbstractJsonEntityController<RbacOrg, RbacOrgService> {


	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private RbacOrgService rbacOrgService;







}

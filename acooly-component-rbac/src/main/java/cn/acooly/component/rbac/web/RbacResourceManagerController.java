/*
* acooly.cn Inc.
* Copyright (c) 2022 All Rights Reserved.
* create by zhangpu
* date:2022-10-05
*/
package cn.acooly.component.rbac.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.acooly.component.rbac.entity.RbacResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJsonEntityController;
import cn.acooly.component.rbac.service.RbacResourceService;
import cn.acooly.component.rbac.enums.ResourceType;
import com.acooly.core.utils.enums.WhetherStatus;
import cn.acooly.component.rbac.enums.ResourceShowMode;

/**
 * 权限资源表 管理控制器
 *
 * @author zhangpu
 * @date 2022-10-05 23:03:16
 */
@Controller
@RequestMapping(value = "/manage/rbac/resource")
public class RbacResourceManagerController extends AbstractJsonEntityController<RbacResource, RbacResourceService> {


	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private RbacResourceService rbacResourceService;






	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("allTypes", ResourceType.mapping());
		model.put("allShowStatuss", WhetherStatus.mapping());
		model.put("allShowModes", ResourceShowMode.mapping());
	}

}

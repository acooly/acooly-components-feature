/*
* acooly.cn Inc.
* Copyright (c) 2022 All Rights Reserved.
* create by zhangpu
* date:2022-10-05
*/
package cn.acooly.component.rbac.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.acooly.component.rbac.entity.RbacUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJsonEntityController;
import cn.acooly.component.rbac.service.RbacUserService;
import cn.acooly.component.rbac.enums.UserStatus;

import com.google.common.collect.Maps;

/**
 * 用户表 管理控制器
 *
 * @author zhangpu
 * @date 2022-10-05 23:03:16
 */
@Controller
@RequestMapping(value = "/manage/rbac/user")
public class RbacUserManagerController extends AbstractJsonEntityController<RbacUser, RbacUserService> {

	private static Map<Integer, String> allUserTypes = Maps.newLinkedHashMap();
	static {
		allUserTypes.put(1, "管理员");
		allUserTypes.put(2, "操作员");
	}

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private RbacUserService rbacUserService;






	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("allUserTypes", allUserTypes);
		model.put("allStatuss", UserStatus.mapping());
	}

}

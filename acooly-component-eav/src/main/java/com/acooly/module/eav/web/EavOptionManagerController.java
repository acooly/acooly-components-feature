/*
* acooly.cn Inc.
* Copyright (c) 2019 All Rights Reserved.
* create by zhangpu
* date:2019-03-05
*/
package com.acooly.module.eav.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.module.eav.entity.EavOption;
import com.acooly.module.eav.service.EavOptionService;

import com.google.common.collect.Maps;

/**
 * 属性选项 管理控制器
 * 
 * @author zhangpu
 * Date: 2019-03-05 18:52:41
 */
@Controller
@RequestMapping(value = "/manage/module/eav/eavOption")
public class EavOptionManagerController extends AbstractJQueryEntityController<EavOption, EavOptionService> {
	

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private EavOptionService eavOptionService;

	

}

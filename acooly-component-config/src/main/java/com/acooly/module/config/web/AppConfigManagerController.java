/*
* acooly.cn Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by shuijing
* date:2018-06-19
*/
package com.acooly.module.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.module.config.entity.AppConfig;
import com.acooly.module.config.service.AppConfigService;

/**
 * sys_app_config 管理控制器
 * 
 * @author shuijing
 * Date: 2018-06-19 21:52:29
 */
@Controller
@RequestMapping(value = "/manage/module/config/appConfig")
public class AppConfigManagerController extends AbstractJQueryEntityController<AppConfig, AppConfigService> {
	

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private AppConfigService appConfigService;

	

}

/*
* acooly.cn Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by acooly
* date:2018-10-10
*/
package com.acooly.module.chart.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.module.chart.entity.ChartItems;
import com.acooly.module.chart.service.ChartItemsService;
import com.acooly.module.chart.enums.TypeEnum;
import com.acooly.module.chart.enums.StatusEnum;

import com.google.common.collect.Maps;

/**
 * 图表-图表选项 管理控制器
 * 
 * @author acooly
 * Date: 2018-10-10 11:15:20
 */
@Controller
@RequestMapping(value = "/manage/module/chart/chartItems")
public class ChartItemsManagerController extends AbstractJQueryEntityController<ChartItems, ChartItemsService> {
	

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private ChartItemsService chartItemsService;

	
	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("allTypes", TypeEnum.mapping());
		model.put("allStatuss", StatusEnum.mapping());
	}

}

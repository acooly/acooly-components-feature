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
import com.acooly.module.chart.entity.ChartData;
import com.acooly.module.chart.service.ChartDataService;

import com.google.common.collect.Maps;

/**
 * 图表-数据项 管理控制器
 * 
 * @author acooly
 * Date: 2018-10-10 11:15:20
 */
@Controller
@RequestMapping(value = "/manage/module/chart/chartData")
public class ChartDataManagerController extends AbstractJQueryEntityController<ChartData, ChartDataService> {
	

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private ChartDataService chartDataService;

	

}

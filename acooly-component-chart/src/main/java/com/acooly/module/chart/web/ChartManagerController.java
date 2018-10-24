/*
* acooly.cn Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by acooly
* date:2018-10-10
*/
package com.acooly.module.chart.web;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.acooly.core.common.web.MappingMethod;
import com.acooly.core.common.web.support.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.module.chart.entity.Chart;
import com.acooly.module.chart.service.ChartService;
import com.acooly.module.chart.enums.StatusEnum;

import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 图表-主题 管理控制器
 * 
 * @author acooly
 * Date: 2018-10-10 11:15:20
 */
@Controller
@RequestMapping(value = "/manage/module/chart/chart")
public class ChartManagerController extends AbstractJQueryEntityController<Chart, ChartService> {
	

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private ChartService chartService;

	
	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("allStatuss", StatusEnum.mapping());
	}


	@Override
	protected void doRemove(HttpServletRequest request, HttpServletResponse response, Model model, Serializable... ids) throws Exception {
		if(ids != null && ids.length != 0) {
			if(ids.length == 1) {
				chartService.removeChartAndItemsAndData((Long)ids[0]);
			} else {
				for (int i=0;i<ids.length;i++){
					chartService.removeChartAndItemsAndData((Long)ids[i]);
				}
			}

		} else {
			throw new IllegalArgumentException("请求参数中没有指定需要删除的实体Id");
		}
	}

}

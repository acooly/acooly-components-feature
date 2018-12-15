/*
* acooly.cn Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by acooly
* date:2018-10-10
*/
package com.acooly.module.chart.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import com.acooly.core.common.domain.Entityable;
import com.acooly.core.common.web.MappingMethod;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.ObjectUtils;
import com.acooly.core.utils.Servlets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.module.chart.entity.ChartData;
import com.acooly.module.chart.service.ChartDataService;

import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.ResponseBody;

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

	@Override
	@RequestMapping(value = "/create")
	public String create(HttpServletRequest request, HttpServletResponse response, Model model) {
		allow(request, response, MappingMethod.create);
		try {
			String chartId = Servlets.getParameter(request,"chartId");
			String itemsId = Servlets.getParameter(request,"itemsId");
			model.addAttribute("action", ACTION_CREATE);
			onCreate(request, response, model);
			model.addAllAttributes(referenceData(request));
			model.addAttribute("chartId", chartId);
			model.addAttribute("itemsId", itemsId);
		} catch (Exception e) {
			handleException("新建", e, request);
		}
		return getEditView();
	}

	@Override
	@RequestMapping({"edit"})
	public String edit(HttpServletRequest request, HttpServletResponse response, Model model) {
		this.allow(request, response, MappingMethod.update);

		try {
			model.addAllAttributes(this.referenceData(request));
			String chartId = Servlets.getParameter(request,"chartId");
			Long itemsId = Servlets.getParameter(request,"itemsId",Long.class);
			ChartData chartData = chartDataService.findChartDataByItemsId(itemsId);
			model.addAttribute("action", "edit");
			model.addAttribute(this.getEntityName(), chartData);
			model.addAttribute("chartId", chartId);
			model.addAttribute("itemsId", itemsId);
			this.onEdit(request, response, model, chartData);
		} catch (Exception var5) {
			handleException("新建", var5, request);
		}

		return this.getEditView();
	}

	@RequestMapping(value = "/checkSaveOrEdit")
	@ResponseBody
	public JsonResult checkSaveOrEdit(HttpServletRequest request, HttpServletResponse response){
		JsonResult result = new JsonResult();
		Boolean isSave = true;
		try {
			Long itemsId = Servlets.getParameter(request,"itemsId",Long.class);
			ChartData chartData = chartDataService.findChartDataByItemsId(itemsId);
			if (!ObjectUtils.isEmpty(chartData)){
				isSave = false;
			}
			result.appendData("isSave",isSave);
		}catch (Exception e){
			handleException("检查保存或修改", e, request);
		}
		return  result;
	}

	@Override
	protected ChartData doSave(HttpServletRequest request, HttpServletResponse response, Model model, boolean isCreate) throws Exception {
		ChartData entity = this.loadEntity(request);
		if(entity == null) {
			this.allow(request, response, MappingMethod.create);
			entity = (ChartData)this.getEntityClass().newInstance();
		} else {
			this.allow(request, response, MappingMethod.update);
		}

		this.doDataBinding(request, entity);
		this.onSave(request, response, model, entity, isCreate);
		if(isCreate) {
			this.getEntityService().save(entity);
		} else {
			this.getEntityService().update(entity);
		}

		return entity;
	}
}

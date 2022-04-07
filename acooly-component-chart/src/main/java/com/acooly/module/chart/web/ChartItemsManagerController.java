/*
* acooly.cn Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by acooly
* date:2018-10-10
*/
package com.acooly.module.chart.web;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.MappingMethod;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.Strings;
import com.acooly.module.chart.entity.ChartData;
import com.acooly.module.chart.entity.ChartItems;
import com.acooly.module.chart.entity.dto.WhereDataDto;
import com.acooly.module.chart.enums.ChartItemsIsShowEnum;
import com.acooly.module.chart.enums.StatusEnum;
import com.acooly.module.chart.enums.TypeEnum;
import com.acooly.module.chart.enums.WhereTypeEnum;
import com.acooly.module.chart.service.ChartDataService;
import com.acooly.module.chart.service.ChartItemsService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 图表-图表选项 管理控制器
 * 
 * @author acooly Date: 2018-10-10 11:15:20
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

	@Autowired
	private ChartDataService chartDataService;

	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("allTypes", TypeEnum.mapping());
		model.put("allStatuss", StatusEnum.mapping());
		model.put("allIsShows", ChartItemsIsShowEnum.mapping());
		model.put("allIsDataListShows", ChartItemsIsShowEnum.mapping());
		model.put("allWhereTypes", WhereTypeEnum.mapping());
	}

	@Override
	@RequestMapping(value = "/create")
	public String create(HttpServletRequest request, HttpServletResponse response, Model model) {
		allow(request, response, MappingMethod.create);
		try {
			String chartId = Servlets.getParameter(request, "chartId");
			model.addAllAttributes(referenceData(request));
			onCreate(request, response, model);
			model.addAttribute("action", ACTION_CREATE);
			model.addAttribute("chartId", chartId);
			model.addAttribute("chartData", chartDataService.findChartDataByItemsId(Long.parseLong(chartId)));
		} catch (Exception e) {
			handleException("新建", e, request);
		}
		return getEditView();
	}

	@Override
	protected PageInfo<ChartItems> doList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		Map<String, Boolean> sortMap = Maps.newLinkedHashMap();
		sortMap.put("orderTime", Boolean.valueOf(false));
		return this.getEntityService().query(this.getPageInfo(request), this.getSearchParams(request), sortMap);
	}

	@RequestMapping("moveUp")
	@ResponseBody
	public JsonResult moveUp(HttpServletRequest request, HttpServletResponse response) {
		JsonResult result = new JsonResult();
		try {
			Long id = Servlets.getParameter(request, "id", Long.class);
			ChartItems chartItems = chartItemsService.get(id);
			Long sortTime = chartItems.getOrderTime().getTime();
			ChartItems flag = null;
			Map<String, Boolean> sortMap = Maps.newLinkedHashMap();
			sortMap.put("orderTime", Boolean.valueOf(false));
			Map<String, Object> searchMap = Maps.newHashMap();
			searchMap.put("GT_orderTime", new Date(sortTime));
			searchMap.put("EQ_chartId", chartItems.getChartId());
			List<ChartItems> list = chartItemsService.query(searchMap, sortMap);
			if (list != null && list.size() > 0) {
				flag = list.get(list.size() - 1);
			}
			if (flag != null) {
				chartItems.setOrderTime(flag.getOrderTime());
				flag.setOrderTime(new Date(sortTime));
				chartItemsService.update(flag);
			} else {
				chartItems.setOrderTime(new Date());
			}
			chartItemsService.update(chartItems);
			result.appendData("chartId", chartItems.getChartId());
			result.setMessage("上移成功");

		} catch (Exception e) {
			this.handleException(result, "上移", e);
		}
		return result;
	}

	@Override
	protected List<ChartItems> doQuery(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		map.put("EQ_chartId", request.getParameter("search_EQ_chartId"));
		Map<String, Boolean> sortMap = Maps.newLinkedHashMap();
		sortMap.put("orderTime", Boolean.valueOf(false));
		return this.getEntityService().query(map, sortMap);
	}

	@Override
	protected ChartItems doSave(HttpServletRequest request, HttpServletResponse response, Model model, boolean isCreate)
			throws Exception {
		ChartItems entity = this.loadEntity(request);
		if (entity == null) {
			this.allow(request, response, MappingMethod.create);
			entity = (ChartItems) this.getEntityClass().newInstance();
		} else {
			this.allow(request, response, MappingMethod.update);
		}

		this.doDataBinding(request, entity);
		this.onSave(request, response, model, entity, isCreate);
		if (entity.getLoopTime() != null) {
			entity.setLoopTime(entity.getLoopTime() * 1000L);
		}

		Map<String, String[]> parameterNames = request.getParameterMap();
		long parameterNameSize = parameterNames.size();
		List<WhereDataDto> whereDataDtoList = Lists.newArrayList();
		for (int i = 1; i < parameterNameSize; i++) {
			String whereDataName = request.getParameter("whereDataName_" + i);
			if (Strings.isBlank(whereDataName)) {
				break;
			}
			WhereDataDto dto = new WhereDataDto();
			dto.setName(whereDataName);
			dto.setConditParam(request.getParameter("whereDataConditParam_" + i));
			dto.setDataType(request.getParameter("whereDataDataType_" + i));
			dto.setDefaultValue(request.getParameter("whereDataDefaultValue_" + i));
			whereDataDtoList.add(dto);
		}
		entity.setWhereDataJson("" + JSON.toJSON(whereDataDtoList));

		if (isCreate) {
			chartItemsService.saveOrUpdateChartItemsAndChartData(entity, true);
		} else {
			chartItemsService.saveOrUpdateChartItemsAndChartData(entity, false);
		}

		return entity;
	}

	@Override
	protected void onEdit(HttpServletRequest request, HttpServletResponse response, Model model, ChartItems entity) {
		ChartData chartData = chartDataService.findChartDataByItemsId(entity.getId());
		entity.setSqlData(chartData.getSqlData());
		entity.setFieldMapped(chartData.getFieldMapped());
		String fielMapped = chartData.getFieldMapped();
		if (Strings.isNotBlank(fielMapped)) {
			JSONObject fielMappedJson = JSONObject.parseObject(fielMapped);
			Map<String, Object> map = (Map<String, Object>) fielMappedJson;
			entity.setFieldMappedMap(map);
			entity.setFieldMappedJson(fielMappedJson);
		}
		model.addAttribute("chartData", chartData);
		model.addAttribute("chartItems", entity);
	}

	@Override
	protected void doRemove(HttpServletRequest request, HttpServletResponse response, Model model, Serializable... ids)
			throws Exception {
		if (ids != null && ids.length != 0) {
			if (ids.length == 1) {
				chartItemsService.removeChartItemsAndChartDataById((Long) ids[0]);
			} else {
				for (int i = 0; i < ids.length; i++) {
					chartItemsService.removeChartItemsAndChartDataById((Long) ids[i]);
				}
			}

		} else {
			throw new IllegalArgumentException("请求参数中没有指定需要删除的实体Id");
		}
	}

}

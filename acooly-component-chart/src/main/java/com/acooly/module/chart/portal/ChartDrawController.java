/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-10
 */
package com.acooly.module.chart.portal;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Asserts;
import com.acooly.module.chart.entity.Chart;
import com.acooly.module.chart.entity.ChartData;
import com.acooly.module.chart.entity.ChartItems;
import com.acooly.module.chart.enums.StatusEnum;
import com.acooly.module.chart.handle.ChartUtils;
import com.acooly.module.chart.handle.analyse.ChartDataAnalyseService;
import com.acooly.module.chart.handle.analyse.dto.ShaftDataDto;
import com.acooly.module.chart.handle.data.ChartDataQueryService;
import com.acooly.module.chart.service.ChartDataService;
import com.acooly.module.chart.service.ChartItemsService;
import com.acooly.module.chart.service.ChartService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * 图表-主题 管理控制器
 *
 * @author acooly Date: 2018-10-10 11:15:20
 */
@Slf4j
@Controller
@RequestMapping(value = "/manage/module/echarts")
public class ChartDrawController extends AbstractJsonEntityController {

	@Autowired
	private ChartService chartService;

	@Autowired
	private ChartItemsService chartItemsService;

	@Autowired
	private ChartDataService chartDataService;

	@Autowired
	private ChartDataQueryService chartDataQueryService;

	@Autowired
	private ChartDataAnalyseService chartDataAnalyseService;

	@RequestMapping(value = "index_{id}")
	public String index(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		if (StringUtils.isBlank(id)) {
			throw new RuntimeException("chartId不能为空,访问格式/index_chartId.html");
		}

		Chart chart = chartService.get(Long.parseLong(id));
		if (chart == null) {
			throw new RuntimeException("chartId不存在的图表");
		}

		if (chart.getStatus() == StatusEnum.disable) {
			throw new RuntimeException("图表主题无效");
		}

		List<ChartItems> chartItemsList = chartItemsService.findByChartIdAndStatus(chart.getId(), StatusEnum.enable);
		model.addAttribute("chart", chart);
		model.addAttribute("chartItemsList", chartItemsList);

		return "/manage/module/echarts/home/index";
	}

	/**
	 * 解析图形列表
	 * 
	 * /manage/module/echarts/chartItem_line_2.html
	 *
	 * @param type
	 * @param chartItemId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "chartItem_{type}_{chartItemId}")
	public String chartItem(@PathVariable("type") String type, @PathVariable("chartItemId") String chartItemId,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		ChartItems chartItem = getChartItemData(chartItemId, model);
		return "/manage/module/echarts/" + type;
	}

	/**
	 * 解析图形列表
	 * 
	 * /manage/module/echarts/chartItem_line_2.html
	 *
	 * @param type
	 * @param chartItemId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "chartItemOne_{chartItemId}")
	public String chartItemOne(@PathVariable("chartItemId") String chartItemId, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		ChartItems chartItem = getChartItemData(chartItemId, model);
		return "/manage/module/echarts/" + chartItem.getType().code();
	}

	/**
	 * ChartItemData数据
	 * 
	 * @param chartItemId
	 * @param model
	 * @return
	 */
	private ChartItems getChartItemData(String chartItemId, Model model) {
		if (StringUtils.isBlank(chartItemId)) {
			throw new RuntimeException("chartItemId不能为空,访问格式/chartItem_type_chartItemId.html");
		}
		ChartItems chartItem = chartItemsService.get(Long.parseLong(chartItemId));
		if (chartItem == null) {
			throw new RuntimeException("chartItem不存在的图表");
		}

		ChartData chartData = chartDataService.findChartDataByItemsId(Long.parseLong(chartItemId));
		model.addAttribute("whereData", chartData.getWhereDataList());

		model.addAttribute("chartItemId", chartItemId);
		model.addAttribute("chartItem", chartItem);
		model.addAttribute("loopTime", chartItem.getLoopTime());
		return chartItem;
	}

	/**
	 * include模式使用：参考/jsp/manage/module/echarts/line_include.jsp 查询条件
	 * 
	 * @param chartItemId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "chartSearch_{chartItemId}")
	public String chartSearch(@PathVariable("chartItemId") String chartItemId, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		if (StringUtils.isBlank(chartItemId)) {
			throw new RuntimeException("chartItemId不能为空,访问格式/chartItem_type_chartItemId.html");
		}
		ChartData chartData = chartDataService.findChartDataByItemsId(Long.parseLong(chartItemId));
		model.addAttribute("whereData", chartData.getWhereData());
		return "/manage/module/echarts/common/include";
	}

	/**
	 * 线形图表,柱形图表,饼图
	 *
	 * @param chartItemId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "charData_line_{chartItemId}", "charData_bar_{chartItemId}", "charData_pie_{chartItemId}",
			"charData_bar_stack_{chartItemId}", "charData_map_China_{chartItemId}" })
	@ResponseBody
	public JsonResult charData(@PathVariable("chartItemId") String chartItemId, HttpServletRequest request,
			HttpServletResponse response, Model model) {

		JsonResult result = new JsonResult();
		if (StringUtils.isBlank(chartItemId)) {
			result.setMessage("chartItemId不能为空");
			result.setSuccess(false);
			return result;
		}

		ChartItems chartItems = chartItemsService.get(Long.parseLong(chartItemId));
		ChartData chartData = chartDataService.findChartDataByItemsId(chartItems.getId());

		String sql = chartData.getSqlData();

		// 动态拼装sql
		sql = assembleSql(request, sql);

		List<Map<String, Object>> dbData = chartDataQueryService.querySql(sql);

		// x轴，y轴 赋值
		ShaftDataDto shaftDataDto = chartDataAnalyseService.shaftDataAnalyse(chartItems.getTitle(),
				chartItems.getxShaft(), chartItems.getyShaft(), dbData);
		result.appendData("shaftData", JSON.toJSON(shaftDataDto));

		// 图表参数设置
		Map<String, Object> params = Maps.newHashMap();
		params.put("isShow", chartItems.getIsShow().code());
		result.appendData("chartItemsParams", JSON.toJSON(params));

		// 元数据
		model.addAttribute("dataList", dbData);
		return result;
	}

	/**
	 * 组装sql语句
	 * 
	 * @param request
	 * @param sql
	 */
	private String assembleSql(HttpServletRequest request, String sql) {
		log.debug("组装前sql语句:\n{}", sql);
		Set<String> parameterMapKey = request.getParameterMap().keySet();
		for (String parameterKey : parameterMapKey) {
			String parameterValue = request.getParameter(parameterKey);
			// 验证sql的合法性
			ChartUtils.checkSql(parameterValue.toUpperCase());
			// 替换sql关键值
			if (parameterKey.contains("$")) {
				if (Strings.isNotBlank(parameterValue)) {
					String conditName = parameterKey.split("\\$")[1];
					sql = sql.replace("$" + conditName + "$", parameterValue);
				} else {
					sql = sql.replace(parameterKey, " 1=1 ");
				}
			}
		}
		log.debug("组装后sql语句:\n{}", sql);
		return sql;
	}

	@RequestMapping("list_{chartItemId}")
	@ResponseBody
	public JsonResult listMeta(@PathVariable("chartItemId") Long chartItemId, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		JsonResult result = new JsonResult();
		try {
			Asserts.notNull(chartItemId, "chartItemId");
			ChartItems chartItems = chartItemsService.get(chartItemId);
			ChartData chartData = chartDataService.findChartDataByItemsId(chartItems.getId());
			String sql = chartData.getSqlData();
			List<Map<String, Object>> dbData = chartDataQueryService.querySql(sql);
			String fieldMappedJson = chartData.getFieldMapped();
			Map<String, String> meta = JSON.parseObject(fieldMappedJson, Map.class);
			result.appendData("listMeta", meta);
			result.appendData("listData", dbData);
			result.appendData("item", chartItems);
		} catch (Exception e) {
			handleException(result, "列表", e);
		}
		return result;
	}

}

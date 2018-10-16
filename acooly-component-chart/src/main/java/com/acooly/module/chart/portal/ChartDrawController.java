/*
* acooly.cn Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by acooly
* date:2018-10-10
*/
package com.acooly.module.chart.portal;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.web.support.JsonResult;
import com.acooly.module.chart.entity.Chart;
import com.acooly.module.chart.entity.ChartData;
import com.acooly.module.chart.entity.ChartItems;
import com.acooly.module.chart.enums.StatusEnum;
import com.acooly.module.chart.handle.analyse.ChartDataAnalyseService;
import com.acooly.module.chart.handle.analyse.dto.ShaftDataDto;
import com.acooly.module.chart.handle.data.ChartDataQueryService;
import com.acooly.module.chart.service.ChartDataService;
import com.acooly.module.chart.service.ChartItemsService;
import com.acooly.module.chart.service.ChartService;
import com.alibaba.fastjson.JSON;

/**
 * 图表-主题 管理控制器
 * 
 * @author acooly Date: 2018-10-10 11:15:20
 */
@Controller
@RequestMapping(value = "/manage/module/echarts")
public class ChartDrawController {

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
		model.addAttribute("chartItemsList", chartItemsList);

		return "/manage/module/echarts/home/index";
	}

	/**
	 * 解析图形列表
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
		if (StringUtils.isBlank(chartItemId)) {
			throw new RuntimeException("chartItemId不能为空,访问格式/chartItem_type_chartItemId.html");
		}
		ChartItems chartItem = chartItemsService.get(Long.parseLong(chartItemId));
		if (chartItem == null) {
			throw new RuntimeException("chartItem不存在的图表");
		}
		model.addAttribute("chartItemId", chartItemId);
		model.addAttribute("loopTime", chartItem.getLoopTime());
		return "/manage/module/echarts/" + type;
	}

	/**
	 * 线形图表,柱形图表
	 * 
	 * @param chartItemId
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"chart_line_{chartItemId}","chart_bar_{chartItemId}"})
	@ResponseBody
	public JsonResult chart(@PathVariable("chartItemId") String chartItemId, HttpServletRequest request,
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
		List<Map<String, Object>> dbData = chartDataQueryService.querySql(sql);
		ShaftDataDto shaftDataDto = chartDataAnalyseService.shaftDataAnalyse(chartItems.getTitle(),
				chartItems.getxShaft(), chartItems.getyShaft(), dbData);

		result.appendData("shaftData", JSON.toJSON(shaftDataDto));
		System.out.println(JSON.toJSON(shaftDataDto));

		return result;
	}

	@RequestMapping(value = "bar")
	public String bar(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/manage/module/echarts/bar";
	}

	@RequestMapping(value = "pie")
	public String pie(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/manage/module/echarts/pie";
	}
}

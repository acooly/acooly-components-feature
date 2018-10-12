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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.module.chart.entity.Chart;
import com.acooly.module.chart.entity.ChartData;
import com.acooly.module.chart.entity.ChartItems;
import com.acooly.module.chart.enums.StatusEnum;
import com.acooly.module.chart.handle.data.DataQueryService;
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
@RequestMapping(value = "/test/chart")
public class ChartTestController {

	@Autowired
	private ChartService chartService;

	@Autowired
	private ChartItemsService chartItemsService;

	@Autowired
	private ChartDataService chartDataService;

	@Autowired
	private DataQueryService dataQueryService;

	@RequestMapping(value = "line")
	public String line(HttpServletRequest request, HttpServletResponse response, Model model) {

		Chart chart = chartService.get(1L);
		List<ChartItems> chartItemsList = chartItemsService.findByChartIdAndStatus(chart.getId(), StatusEnum.enable);
		for (ChartItems chartItems : chartItemsList) {
			ChartData chartData = chartDataService.findChartDataByItemsId(chartItems.getId());

			String sql = chartData.getSqlData();
			String jsonString = chartData.getFieldMapped();
			Map<String, Object> maps = (Map<String, Object>) JSON.parseObject(jsonString);

			dataQueryService.querySql(sql);

			System.out.println(sql);
			System.out.println(maps);

			System.out.println(chartItems);
		}

		return "/manage/module/echarts/line";
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

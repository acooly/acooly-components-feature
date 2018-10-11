/*
* acooly.cn Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by acooly
* date:2018-10-10
*/
package com.acooly.module.chart.portal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 图表-主题 管理控制器
 * 
 * @author acooly Date: 2018-10-10 11:15:20
 */
@Controller
@RequestMapping(value = "/test/chart")
public class ChartTestController {

	@RequestMapping(value = "line")
	public String line(HttpServletRequest request, HttpServletResponse response, Model model) {
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

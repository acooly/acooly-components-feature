/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-10
 *
 */
package com.acooly.module.chart.enums;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.acooly.core.utils.enums.Messageable;

/**
 * 图表-图表选项 TypeEnum 枚举定义
 * 
 * @author acooly
 * Date: 2018-10-10 11:15:20
 */
public enum ChartTypeEnum implements Messageable {

	line("line", "折线图"),

	bar("bar", "柱状图"),
	
	bar_stack("bar_stack", "柱状图-堆叠"),

	pie("pie", "饼图"),

	annular("annular", "环形图"),

	map_China("map_China", "中国地图"),

	map_world("map_world", "世界地图"),

	list_roll("list_roll", "列表数据滚动"),


	;

	private final String code;
	private final String message;

	private ChartTypeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String code() {
		return code;
	}

	public String message() {
		return message;
	}

	public static Map<String, String> mapping() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (ChartTypeEnum type : values()) {
			map.put(type.getCode(), type.getMessage());
		}
		return map;
	}

	/**
	 * 通过枚举值码查找枚举值。
	 * 
	 * @param code
	 *            查找枚举值的枚举值码。
	 * @return 枚举值码对应的枚举值。
	 * @throws IllegalArgumentException
	 *             如果 code 没有对应的 Status 。
	 */
	public static ChartTypeEnum find(String code) {
		for (ChartTypeEnum status : values()) {
			if (status.getCode().equals(code)) {
				return status;
			}
		}
		return null;
	}

	/**
	 * 获取全部枚举值。
	 * 
	 * @return 全部枚举值。
	 */
	public static List<ChartTypeEnum> getAll() {
		List<ChartTypeEnum> list = new ArrayList<ChartTypeEnum>();
		for (ChartTypeEnum status : values()) {
			list.add(status);
		}
		return list;
	}

	/**
	 * 获取全部枚举值码。
	 * 
	 * @return 全部枚举值码。
	 */
	public static List<String> getAllCode() {
		List<String> list = new ArrayList<String>();
		for (ChartTypeEnum status : values()) {
			list.add(status.code());
		}
		return list;
	}

}

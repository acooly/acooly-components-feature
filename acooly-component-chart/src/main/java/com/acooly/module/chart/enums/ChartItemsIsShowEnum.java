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
 * 图表-图表选项 StatusEnum 枚举定义
 * 
 * @author acooly
 * Date: 2018-10-10 11:15:20
 */
public enum ChartItemsIsShowEnum implements Messageable {

	YES("YES", "是"),

	NO("NO", "否"),

	;

	private final String code;
	private final String message;

	private ChartItemsIsShowEnum(String code, String message) {
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
		for (ChartItemsIsShowEnum type : values()) {
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
	public static ChartItemsIsShowEnum find(String code) {
		for (ChartItemsIsShowEnum status : values()) {
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
	public static List<ChartItemsIsShowEnum> getAll() {
		List<ChartItemsIsShowEnum> list = new ArrayList<ChartItemsIsShowEnum>();
		for (ChartItemsIsShowEnum status : values()) {
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
		for (ChartItemsIsShowEnum status : values()) {
			list.add(status.code());
		}
		return list;
	}

}

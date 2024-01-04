/*
 * acooly.cn Inc.
 * Copyright (c) 2023 All Rights Reserved.
 * create by acooly
 * date:2023-05-06
 *
 */
package com.acooly.module.syncdata.common.enums;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.acooly.core.utils.enums.Messageable;

/**
 * 同步表数据信息 QueryColumnTypeEnum 枚举定义
 * 
 * @author acooly
 * @date 2023-05-06 08:59:00
 */
public enum QueryColumnTypeEnum implements Messageable {

	type_long("type_long", "long类型"),
	type_date("type_date", "时间类型"),
	;

	private final String code;
	private final String message;

	private QueryColumnTypeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}


	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String code() {
		return code;
	}

	@Override
	public String message() {
		return message;
	}

	public static Map<String, String> mapping() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (QueryColumnTypeEnum type : values()) {
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
	public static QueryColumnTypeEnum find(String code) {
		for (QueryColumnTypeEnum status : values()) {
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
	public static List<QueryColumnTypeEnum> getAll() {
		List<QueryColumnTypeEnum> list = new ArrayList<QueryColumnTypeEnum>();
		for (QueryColumnTypeEnum status : values()) {
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
		for (QueryColumnTypeEnum status : values()) {
			list.add(status.code());
		}
		return list;
	}

}

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
 * 同步表数据信息 QueryTypeEnum 枚举定义
 * 
 * @author acooly
 * @date 2023-05-06 08:59:00
 */
public enum QueryTypeEnum implements Messageable {


	EQ("EQ", "等于", "="),
	GTE("GTE", "大于等于", ">="),
	LTE("LTE", "小于等于", "<="),
	;

	private final String code;
	private final String message;

	private final String operatorCode;

	private QueryTypeEnum(String code, String message, String operatorCode) {
		this.code = code;
		this.message = message;
		this.operatorCode = operatorCode;
	}


	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getOperatorCode() {
		return operatorCode;
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
		for (QueryTypeEnum type : values()) {
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
	public static QueryTypeEnum find(String code) {
		for (QueryTypeEnum status : values()) {
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
	public static List<QueryTypeEnum> getAll() {
		List<QueryTypeEnum> list = new ArrayList<QueryTypeEnum>();
		for (QueryTypeEnum status : values()) {
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
		for (QueryTypeEnum status : values()) {
			list.add(status.code());
		}
		return list;
	}

}

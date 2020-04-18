/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by zhangpu
 * date:2020-04-06
 *
 */
package com.acooly.module.test.security.enums;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.acooly.core.utils.enums.Messageable;

/**
 * acoolycoder测试 CustomerTypeEnum 枚举定义
 * 
 * @author zhangpu
 * Date: 2020-04-06 23:23:46
 */
public enum CustomerTypeEnum implements Messageable {

	normal("normal", "普通"),
	vip("vip", "重要"),
	sepc("sepc", "特别");

	private final String code;
	private final String message;

	private CustomerTypeEnum(String code, String message) {
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
		for (CustomerTypeEnum type : values()) {
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
	public static CustomerTypeEnum find(String code) {
		for (CustomerTypeEnum status : values()) {
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
	public static List<CustomerTypeEnum> getAll() {
		List<CustomerTypeEnum> list = new ArrayList<CustomerTypeEnum>();
		for (CustomerTypeEnum status : values()) {
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
		for (CustomerTypeEnum status : values()) {
			list.add(status.code());
		}
		return list;
	}

}

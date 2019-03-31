/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-10
 *
 */
package com.acooly.module.wechat.oauth.mini.enums;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.acooly.core.utils.enums.Messageable;

/**
 * 微信接口 枚举类
 * 
 * @author CuiFuQ
 *
 */
public enum WechatMiniClientEnum implements Messageable {

	/** 登录凭证校验 */
	sns_jscode2session("/sns/jscode2session", "登录凭证校验"),

	/** 获取access_token **/
	cgi_bin_token("/cgi-bin/token", "获取access_token"),

	/** 获取小程序码 **/
	wxa_getwxacodeunlimit("/wxa/getwxacodeunlimit", "获取小程序码（无限制数量）"),

	;

	private final String code;
	private final String message;

	private WechatMiniClientEnum(String code, String message) {
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
		for (WechatMiniClientEnum type : values()) {
			map.put(type.getCode(), type.getMessage());
		}
		return map;
	}

	/**
	 * 通过枚举值码查找枚举值。
	 * 
	 * @param code 查找枚举值的枚举值码。
	 * @return 枚举值码对应的枚举值。
	 * @throws IllegalArgumentException 如果 code 没有对应的 Status 。
	 */
	public static WechatMiniClientEnum find(String code) {
		for (WechatMiniClientEnum status : values()) {
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
	public static List<WechatMiniClientEnum> getAll() {
		List<WechatMiniClientEnum> list = new ArrayList<WechatMiniClientEnum>();
		for (WechatMiniClientEnum status : values()) {
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
		for (WechatMiniClientEnum status : values()) {
			list.add(status.code());
		}
		return list;
	}

}

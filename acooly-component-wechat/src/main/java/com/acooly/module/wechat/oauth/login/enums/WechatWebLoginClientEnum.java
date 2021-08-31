/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-10
 *
 */
package com.acooly.module.wechat.oauth.login.enums;

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
public enum WechatWebLoginClientEnum implements Messageable {

	/** 第二步：通过code换取网页授权access_token */
	sns_oauth2_access_token("/sns/oauth2/access_token", "通过code换取网页授权access_token"),

	/** 第三步：刷新access_token（如果需要） */
	sns_oauth2_refresh_token("/sns/oauth2/refresh_token", "刷新access_token（如果需要）"),

	/** 第四步：拉取用户基本信息(需scope为 snsapi_userinfo) */
	sns_userinfo("/sns/userinfo", "拉取用户信息(需scope为 snsapi_userinfo)"),

	/** 检验授权凭证（access_token）是否有效 */
	sns_auth("/sns/auth", "检验授权凭证（access_token）是否有效"),

	/** 通过OpenID来获取用户基本信息 */
	cgi_bin_user_info("/cgi-bin/user/info", "获取用户基本信息（包括UnionID机制）"),

	;

	private final String code;
	private final String message;

	private WechatWebLoginClientEnum(String code, String message) {
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
		for (WechatWebLoginClientEnum type : values()) {
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
	public static WechatWebLoginClientEnum find(String code) {
		for (WechatWebLoginClientEnum status : values()) {
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
	public static List<WechatWebLoginClientEnum> getAll() {
		List<WechatWebLoginClientEnum> list = new ArrayList<WechatWebLoginClientEnum>();
		for (WechatWebLoginClientEnum status : values()) {
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
		for (WechatWebLoginClientEnum status : values()) {
			list.add(status.code());
		}
		return list;
	}

}

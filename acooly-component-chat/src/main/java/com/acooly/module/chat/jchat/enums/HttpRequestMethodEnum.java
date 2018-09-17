package com.acooly.module.chat.jchat.enums;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * request method
 */
public enum HttpRequestMethodEnum {

	/**
	 * 'GET' request method
	 */
	METHOD_GET("GET", "request GET"),

	/**
	 * 'POST' request method
	 */
	METHOD_POST("POST", "request POST"),

	/***
	 * 'PUT' request method
	 */
	METHOD_PUT("PUT", "request PUT"),

	/***
	 * 'DELETE' request method
	 */
	METHOD_DELETE("DELETE", "request DELETE"),;

	private String code;
	private String message;

	private HttpRequestMethodEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public static Map<String, String> mapping() {
		Map<String, String> map = Maps.newLinkedHashMap();
		for (HttpRequestMethodEnum type : values()) {
			map.put(type.getCode(), type.getMessage());
		}
		return map;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return this.code + " : " + this.message;
	}
}

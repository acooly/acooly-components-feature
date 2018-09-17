package com.acooly.module.chat.jchat.enums;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * IM请求地址
 */
public enum JChatRequestUrlEnum {

	/**
	 * post /v1/users/
	 */
	user_register("/v1/users/", "用户注册"),

	/**
	 * post /v1/admins/
	 */
	admin_register("/v1/admins/", "管理员注册"),
	
	/**
	 * put /v1/users/{username}/password
	 */
	password_modify ("/v1/users/", "修改密码"),
	
	/**
	 * PUT /v1/users/{username}/forbidden?disable={disable}
	 */
	user_status_modify ("/v1/users/", "禁用用户"),
	
	/***
	 * PUT /v1/users/{username}
	 */
	user_info_modify("/v1/users/", "用户信息变更"),

	/**
	 * post /v1/users/userstat
	 */
	batch_users_status("/v1/users/userstat", "批量用户在线状态"),

	/**
	 * post /v1/messages
	 */
	send_messages("/v1/messages", "发送消息"),

	//
	;

	private String code;
	private String message;

	private JChatRequestUrlEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public static Map<String, String> mapping() {
		Map<String, String> map = Maps.newLinkedHashMap();
		for (JChatRequestUrlEnum type : values()) {
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

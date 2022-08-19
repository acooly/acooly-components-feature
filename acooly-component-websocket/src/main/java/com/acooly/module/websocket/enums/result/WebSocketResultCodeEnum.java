package com.acooly.module.websocket.enums.result;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.acooly.core.utils.enums.Messageable;

/**
 * 返回码定义 枚举定义
 * 
 * @author cuifuq Date: 2019-03-05 18:18:09
 */
public enum WebSocketResultCodeEnum implements Messageable {

	/** session不存在或者过期 **/
	SESSION_NOT_EXISTING("SESSION_NOT_EXISTING", "session不存在或者过期"),

	/** 功能关闭 **/
	FUNCTION_COLSE("FUNCTION_COLSE", "请设置acooly.websocket.enable=true"),;

	private final String code;
	private final String message;

	private WebSocketResultCodeEnum(String code, String message) {
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
		for (WebSocketResultCodeEnum type : values()) {
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
	public static WebSocketResultCodeEnum find(String code) {
		for (WebSocketResultCodeEnum status : values()) {
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
	public static List<WebSocketResultCodeEnum> getAll() {
		List<WebSocketResultCodeEnum> list = new ArrayList<WebSocketResultCodeEnum>();
		for (WebSocketResultCodeEnum status : values()) {
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
		for (WebSocketResultCodeEnum status : values()) {
			list.add(status.code());
		}
		return list;
	}

}

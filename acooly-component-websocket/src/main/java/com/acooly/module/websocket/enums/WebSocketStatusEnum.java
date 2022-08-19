package com.acooly.module.websocket.enums;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.acooly.core.utils.enums.Messageable;

/**
 * WebSocketStatusEnum
 * 
 * @author cuifuq Date: 2019-03-05 18:18:09
 */
public enum WebSocketStatusEnum implements Messageable {

	/** WebSocket 连接成功 **/
	ON_OPEN("ON_OPEN", "WebSocket客户端-连接成功"),

	/** WebSocket 关闭 **/
	ON_CLOSE("ON_CLOSE", "WebSocket客户端-关闭"),

	/** WebSocket客户端 消息 **/
	ON_MESSAGE("ON_MESSAGE", "WebSocket客户端-消息"),

	;

	private final String code;
	private final String message;

	private WebSocketStatusEnum(String code, String message) {
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
		for (WebSocketStatusEnum type : values()) {
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
	public static WebSocketStatusEnum find(String code) {
		for (WebSocketStatusEnum status : values()) {
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
	public static List<WebSocketStatusEnum> getAll() {
		List<WebSocketStatusEnum> list = new ArrayList<WebSocketStatusEnum>();
		for (WebSocketStatusEnum status : values()) {
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
		for (WebSocketStatusEnum status : values()) {
			list.add(status.code());
		}
		return list;
	}

}

package com.acooly.module.websocket;

import static com.acooly.module.websocket.WebSocketProperties.PREFIX;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author cuifuq
 */
@ConfigurationProperties(prefix = PREFIX)
@Data
public class WebSocketProperties {

	public static final String PREFIX = "acooly.websocket";

	/**
	 * 开关（默认 打开）
	 */
	private Boolean enable = true;

	/**
	 * websocket 消息订阅渠道
	 * <li>1.用于消息同步key
	 * 
	 */
	private String subscribeKey = "websocket_subscribe_channel";

	/**
	 * 是否支持群发消息（默认关闭）
	 */
	private Boolean groupMessage = false;

	/**
	 * webSocket连接过期时间（小时）
	 */
	private Long timeOut = 2L;

}

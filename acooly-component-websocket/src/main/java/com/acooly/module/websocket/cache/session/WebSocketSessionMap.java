package com.acooly.module.websocket.cache.session;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.websocket.Session;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WebSocketSessionMap {

	/**
	 * 用于存所有的连接服务的客户端，这个对象存储是安全的
	 */
	public static ConcurrentMap<String, Session> webSocketMap = new ConcurrentHashMap<String, Session>();

	public static long getSessionSize() {
		return webSocketMap.size();
	}
}

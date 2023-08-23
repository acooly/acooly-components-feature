package com.acooly.module.websocket.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.acooly.module.event.EventHandler;
import com.acooly.module.websocket.WebSocketProperties;
import com.acooly.module.websocket.event.message.TextServerMessage;

import lombok.extern.slf4j.Slf4j;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Invoke;

@Slf4j
@EventHandler
public class TextServerMessageHandle {

	@Autowired
	private WebSocketProperties webSocketProperties;

	@Autowired
	private RedisTemplate redisTemplate;

	// 异步事件处理器
	@Handler(delivery = Invoke.Asynchronously)
	public void textServerMessageHandleAsyn(TextServerMessage message) {
		try {
			log.info("-WebSocket组件-发布发布redis订阅事件:{}", message);
			redisTemplate.convertAndSend(webSocketProperties.getSubscribeKey(), message);
		} catch (Exception e) {
			log.error("服务端消息发送失败{}", e);
		}
	}

}

package com.acooly.module.websocket.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.acooly.module.event.EventHandler;
import com.acooly.module.websocket.WebSocketProperties;
import com.acooly.module.websocket.event.message.TextClientMessage;
import com.acooly.module.websocket.event.message.TextServerMessage;

import lombok.extern.slf4j.Slf4j;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Invoke;

@Slf4j
@EventHandler
public class TextClientMessageHandle {

	@Autowired
	private WebSocketProperties webSocketProperties;

	@Autowired
	private RedisTemplate redisTemplate;

	// 异步事件处理器
	@Handler(delivery = Invoke.Asynchronously)
	@Transactional
	public void textClientMessageHandleAsyn(TextClientMessage message) {
		log.info("WebSocket组件-客户端消息处理:{}", message);
		try {
			TextServerMessage serverMessage = new TextServerMessage();
//			serverMessage.setSessionId(message.getSessionId());
			serverMessage.setBusinessKey(message.getBusinessKey());
			serverMessage.setBusinessType(message.getBusinessType());
			serverMessage.setMessage(message.getMessage());

			// 发布 redis消息
//			redisTemplate.convertAndSend(webSocketProperties.getSubscribeKey(), serverMessage);
		} catch (Exception e) {
			log.error("客户端消息发送失败{}", e);
		}
	}

}

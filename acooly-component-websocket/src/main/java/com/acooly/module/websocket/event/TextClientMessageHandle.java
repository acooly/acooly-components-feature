package com.acooly.module.websocket.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.utils.Strings;
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

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	// 异步事件处理器
	@Handler(delivery = Invoke.Asynchronously)
	public void textClientMessageHandleAsyn(TextClientMessage message) {
		try {
			String profileActive = System.getProperty(Apps.SPRING_PROFILE_ACTIVE);
			if (!profileActive.equals("online")) {
				TextServerMessage serverMessage = new TextServerMessage();
				serverMessage.setBusinessKey(message.getBusinessKey());
				serverMessage.setBusinessType(message.getBusinessType());
				serverMessage.setMessage(message.getMessage());

				// 发布 redis消息
				if (message.getMessage() != null) {
					redisTemplate.convertAndSend(webSocketProperties.getSubscribeKey(), serverMessage);
				}

			}
		} catch (Exception e) {
			log.error("客户端消息发送失败{}", e);
		}
	}

}

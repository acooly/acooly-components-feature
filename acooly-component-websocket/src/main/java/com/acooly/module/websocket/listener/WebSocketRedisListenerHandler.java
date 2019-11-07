package com.acooly.module.websocket.listener;

import java.util.Set;

import javax.websocket.Session;

import org.apache.curator.shaded.com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import com.acooly.core.utils.Strings;
import com.acooly.module.websocket.cache.WebSocketCacheDataService;
import com.acooly.module.websocket.cache.session.WebSocketSessionMap;
import com.acooly.module.websocket.event.message.TextServerMessage;
import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("webSocketRedisListenerHandler")
public class WebSocketRedisListenerHandler implements MessageListener {

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	protected WebSocketCacheDataService webSocketCacheDataService;

	@SuppressWarnings("unchecked")
	@Override
	public void onMessage(Message message, byte[] pattern) {
		try {
			RedisSerializer<TextServerMessage> valueSerializer = redisTemplate.getValueSerializer();
			TextServerMessage textServerMessage = valueSerializer.deserialize(message.getBody());
			log.info("-WebSocket组件-接收发布redis订阅事件message:{}", textServerMessage);
			String businessKey = textServerMessage.getBusinessKey();
			String businessType = textServerMessage.getBusinessType();
			String sessionIdStr = textServerMessage.getSessionId();

			Set<String> sessionIdSet = Sets.newHashSet();
			if (Strings.isNotBlank(sessionIdStr)) {
				sessionIdSet.add(sessionIdStr);
			} else {
				sessionIdSet = webSocketCacheDataService.getWebSocketSessionId(businessKey, businessType);
			}

			for (String sessionId : sessionIdSet) {
				Session session = WebSocketSessionMap.webSocketMap.get(sessionId);
				if (session != null) {
					String text = JSON.toJSONString(textServerMessage.getMessage());
					session.getBasicRemote().sendText(text);
					log.info("-WebSocket组件-发送消息到客户端成功 message:{}", textServerMessage);
				}
			}
		} catch (Exception e) {
			log.error("服务端消息发送失败{}", e);
		}
	}

}

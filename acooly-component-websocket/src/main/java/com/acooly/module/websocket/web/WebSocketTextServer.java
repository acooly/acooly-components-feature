package com.acooly.module.websocket.web;

import java.util.Map;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.acooly.module.websocket.event.message.TextClientMessage;
import com.acooly.module.websocket.web.base.AbstractWebSocketServer;
import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ServerEndpoint("/websocket/text/{businessKey}/{businessType}")
public class WebSocketTextServer extends AbstractWebSocketServer {

	@OnMessage
	public void onMessage(Session session, String message) {
		Map<String, String> map = session.getPathParameters();
		String businessKey = map.get("businessKey");
		String businessType = map.get("businessType");
		String webSocketKey = businessKey + businessType;

		log.info("[websocket] 客户端上传消息成功,webSocketKey:{}", webSocketKey);
		Map<String, Object> messageMap = JSON.parseObject(message);
		TextClientMessage event = new TextClientMessage();
		event.setSessionId(session.getId());
		event.setBusinessKey(businessKey);
		event.setBusinessType(businessType);
		event.setMessage(messageMap);
		eventBus.publishAfterTransactionCommitted(event);
	}

}

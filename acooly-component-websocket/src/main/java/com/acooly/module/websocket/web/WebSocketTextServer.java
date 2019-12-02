package com.acooly.module.websocket.web;

import java.util.Map;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.acooly.core.utils.Strings;
import com.acooly.module.websocket.enums.WebSocketStatusEnum;
import com.acooly.module.websocket.web.base.AbstractWebSocketServer;
import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ServerEndpoint("/websocket/text/{businessType}/{businessKey}")
public class WebSocketTextServer extends AbstractWebSocketServer {

	@OnMessage
	public void onMessage(Session session, String message) {
		if (Strings.isBlank(message)) {
			return;
		}
		log.info("[websocket] 客户端上传消息成功,sessionId:{}", session.getId());
		Map<String, Object> messageMap = JSON.parseObject(message);
		webSocketStatusEvent(session, WebSocketStatusEnum.ON_MESSAGE, messageMap);
	}

}

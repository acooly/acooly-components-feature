package com.acooly.module.websocket.web.base;

import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.module.event.EventBus;
import com.acooly.module.websocket.WebSocketProperties;
import com.acooly.module.websocket.cache.WebSocketCacheDataService;
import com.acooly.module.websocket.cache.session.WebSocketSessionMap;
import com.acooly.module.websocket.enums.WebSocketStatusEnum;
import com.acooly.module.websocket.enums.result.WebSocketResultCodeEnum;
import com.acooly.module.websocket.event.message.TextClientMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AbstractWebSocketServer {

	protected ApplicationContext applicationContext;

	protected WebSocketProperties webSocketProperties;

	protected WebSocketCacheDataService webSocketCacheDataService;

	@SuppressWarnings("rawtypes")
	protected EventBus eventBus;

	public AbstractWebSocketServer() {
		this.applicationContext = Apps.getApplicationContext();
		this.webSocketProperties = applicationContext.getBean(WebSocketProperties.class);
		this.webSocketCacheDataService = applicationContext.getBean(WebSocketCacheDataService.class);
		this.eventBus = applicationContext.getBean(EventBus.class);
	}

	@OnOpen
	public void onOpen(Session session) {
		// webSocket开关
		if (!webSocketProperties.getEnable()) {
			throw new BusinessException(WebSocketResultCodeEnum.FUNCTION_COLSE.message(),
					WebSocketResultCodeEnum.FUNCTION_COLSE.code());
		}
		WebSocketSessionMap.webSocketMap.put(session.getId(), session);
		webSocketCacheDataService.setWebSocketSession(session);
		long sessionSize = WebSocketSessionMap.getSessionSize();
		log.info("[websocket] 连接成功,当前连接数:{}, sessionId:{}", sessionSize, session.getId());

		// WebSocket 连接成功
		webSocketStatusEvent(session, WebSocketStatusEnum.ON_OPEN, null);
	}

	@OnClose
	public void onClose(Session session) {
		String sessionId = session.getId();
		WebSocketSessionMap.webSocketMap.remove(sessionId);
		webSocketCacheDataService.deleteWebSocketSession(session);
		log.info("[websocket] 退出成功,webSocketKey:{}", sessionId);

		// WebSocket 关闭
		webSocketStatusEvent(session, WebSocketStatusEnum.ON_CLOSE, null);
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		log.error("发生错误" + throwable.getMessage(), throwable);
		onClose(session);
	}

	/**
	 * webSocket 状态+消息 事件
	 * 
	 * @param session
	 * @param webSocketStatus
	 */
	protected void webSocketStatusEvent(Session session, WebSocketStatusEnum status, Map<String, Object> message) {
		Map<String, String> map = session.getPathParameters();
		String businessKey = map.get("businessKey");
		String businessType = map.get("businessType");

		TextClientMessage event = new TextClientMessage();
		event.setSessionId(session.getId());
		event.setBusinessKey(businessKey);
		event.setBusinessType(businessType);
		event.setStatus(status);
		event.setMessage(message);
		eventBus.publishAfterTransactionCommitted(event);
	}

}

package com.acooly.module.websocket.event.message;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TextClientMessage implements Serializable {

	/***
	 * sessionId
	 */
	private String sessionId;

	/**
	 * 业务key(全局唯一)
	 */
	private String businessKey;

	/**
	 * 业务类型
	 */
	private String businessType;

	/**
	 * 客户端消息
	 */
	private Map<String, Object> message;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}

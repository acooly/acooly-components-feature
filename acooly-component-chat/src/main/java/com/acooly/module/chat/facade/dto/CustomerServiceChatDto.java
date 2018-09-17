package com.acooly.module.chat.facade.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerServiceChatDto {

	/**
	 * 接收者 -用户名
	 */
	private String targetUserName;

	/**
	 * 发送者 -用户名
	 */
	private String formUserName;

	/**
	 * 发送内容
	 */
	private String sendContent;

}

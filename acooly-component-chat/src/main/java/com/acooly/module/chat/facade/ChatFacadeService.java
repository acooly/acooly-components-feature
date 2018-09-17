/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-08-30
 *
 */
package com.acooly.module.chat.facade;

import com.acooly.module.chat.facade.dto.CustomerServiceChatDto;

/**
 * IM聊天-
 *
 * Date: 2018-08-30 17:57:10
 * 
 * @author acooly
 *
 */
public interface ChatFacadeService {

	/**
	 * 自动客服聊天
	 * 
	 * @param targetUserName
	 * 
	 *            (消息接收者 -用户名)
	 * @return
	 */
	public CustomerServiceChatDto autoCustomerService(String targetUserName);
}

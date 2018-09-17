/*
* acooly.cn Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by acooly
* date:2018-08-31
*/
package com.acooly.module.chat.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.module.chat.entity.ChatInfoTemplate;
import com.acooly.module.chat.service.ChatInfoTemplateService;

import com.google.common.collect.Maps;

/**
 * IM聊天-客服信息模板 管理控制器
 * 
 * @author acooly
 * Date: 2018-08-31 18:27:44
 */
@Controller
@RequestMapping(value = "/manage/component/chat/chatInfoTemplate")
public class ChatInfoTemplateManagerController extends AbstractJQueryEntityController<ChatInfoTemplate, ChatInfoTemplateService> {
	

	{
		allowMapping = "*";
	}

	@SuppressWarnings("unused")
	@Autowired
	private ChatInfoTemplateService chatInfoTemplateService;

	

}

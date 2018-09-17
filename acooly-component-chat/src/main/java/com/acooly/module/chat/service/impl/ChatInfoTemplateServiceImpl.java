/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-08-31
 */
package com.acooly.module.chat.service.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.chat.dao.ChatInfoTemplateDao;
import com.acooly.module.chat.entity.ChatInfoTemplate;
import com.acooly.module.chat.service.ChatInfoTemplateService;

/**
 * IM聊天-客服信息模板 Service实现
 *
 * Date: 2018-08-31 18:27:45
 *
 * @author acooly
 *
 */
@Service("chatInfoTemplateService")
public class ChatInfoTemplateServiceImpl extends EntityServiceImpl<ChatInfoTemplate, ChatInfoTemplateDao>
		implements ChatInfoTemplateService {

	@Override
	public ChatInfoTemplate findByMaxId() {
		return getEntityDao().findByMaxId();
	}

}

/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-08-31
 *
 */
package com.acooly.module.chat.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.chat.entity.ChatInfoTemplate;

/**
 * IM聊天-客服信息模板 Service接口
 *
 * Date: 2018-08-31 18:27:44
 * 
 * @author acooly
 *
 */
public interface ChatInfoTemplateService extends EntityService<ChatInfoTemplate> {

	ChatInfoTemplate findByMaxId();

}

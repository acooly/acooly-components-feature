/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-08-30
 *
 */
package com.acooly.module.chat.service;

import java.util.List;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.chat.entity.ChatUser;

/**
 * IM聊天-用户名 Service接口
 *
 * Date: 2018-08-30 17:57:10
 * 
 * @author acooly
 *
 */
public interface ChatUserService extends EntityService<ChatUser> {

	List<ChatUser> findAdminUserNames();

	ChatUser findAdminByUserName(String formUserName);

}

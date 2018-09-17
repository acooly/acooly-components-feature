/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-08-30
 */
package com.acooly.module.chat.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.enums.AbleStatus;
import com.acooly.module.chat.service.ChatUserService;
import com.acooly.module.chat.dao.ChatUserDao;
import com.acooly.module.chat.entity.ChatUser;
import com.acooly.module.chat.enums.TypeEnum;

/**
 * IM聊天-用户名 Service实现
 *
 * Date: 2018-08-30 17:57:10
 *
 * @author acooly
 *
 */
@Service("chatUserService")
public class ChatUserServiceImpl extends EntityServiceImpl<ChatUser, ChatUserDao> implements ChatUserService {

	@Override
	public List<ChatUser> findAdminUserNames() {
		List<ChatUser> chatUserList = getEntityDao().findByTypeAndStatus(TypeEnum.admin.code(),
				AbleStatus.enable.code());
		return chatUserList;
	}

	@Override
	public ChatUser findAdminByUserName(String formUserName) {
		return getEntityDao().findByUserName(formUserName, TypeEnum.admin.code(), AbleStatus.enable.code());
	}

}

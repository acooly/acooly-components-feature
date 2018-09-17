/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-08-31
 */
 package com.acooly.module.chat.dao;

import com.acooly.module.mybatis.EntityMybatisDao;

import org.apache.ibatis.annotations.Select;

import com.acooly.module.chat.entity.ChatInfoTemplate;

/**
 * IM聊天-客服信息模板 Mybatis Dao
 *
 * Date: 2018-08-31 18:27:45
 * @author acooly
 */
public interface ChatInfoTemplateDao extends EntityMybatisDao<ChatInfoTemplate> {

	@Select("select * from im_chat_info_template where id=(select max(id) from im_chat_info_template)")
	ChatInfoTemplate findByMaxId();

}

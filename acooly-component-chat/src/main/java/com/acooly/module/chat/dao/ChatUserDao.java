/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-08-30
 */
package com.acooly.module.chat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.acooly.module.chat.entity.ChatUser;
import com.acooly.module.mybatis.EntityMybatisDao;

/**
 * IM聊天-用户名 Mybatis Dao
 *
 * Date: 2018-08-30 17:57:10
 * 
 * @author acooly
 */
public interface ChatUserDao extends EntityMybatisDao<ChatUser> {

	@Select("select * from im_chat_user where  type = #{type} and status = #{status}")
	List<ChatUser> findByTypeAndStatus(@Param("type") String type, @Param("status") String status);

	@Select("select * from im_chat_user where user_name = #{userName} and type = #{type} and status = #{status}")
	ChatUser findByUserName(@Param("userName") String userName, @Param("type") String type,
			@Param("status") String status);

}

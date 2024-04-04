/*
 * acooly.cn Inc.
 * Copyright (c) 2024 All Rights Reserved.
 * create by acooly
 * date:2024-04-02
 */
package com.acooly.module.mail.dao;

import com.acooly.module.mail.entity.EmailSender;
import com.acooly.module.mybatis.EntityMybatisDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * email_sender Mybatis Dao
 *
 * @author acooly
 * @date 2024-04-02 09:47:58
 */
public interface EmailSenderDao extends EntityMybatisDao<EmailSender> {

    @Select("select * from email_sender where sys_user_id = #{sysUserId}")
    EmailSender findBySysUserId(@Param("sysUserId") long sysUserId);

}

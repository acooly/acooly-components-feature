/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-04-27
 */
package com.acooly.module.mail.dao;

import com.acooly.module.jpa.EntityJpaDao;
import com.acooly.module.mail.entity.EmailTemplate;
import com.acooly.module.mybatis.EntityMybatisDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author shuijing
 * @author zhangpu for 调整DAO实现为MyBatis
 */
public interface EmailTemplateDao extends EntityMybatisDao<EmailTemplate> {


    /**
     * 根据模板名称(唯一关键字)查询
     *
     * @param name
     * @return
     */
    @Select("select * from email_template where name = #{name}")
    EmailTemplate findByName(@Param("name") String name);


    /**
     * 根据模板名称(唯一关键字)删除
     *
     * @param name
     */
    @Select("delete from email_template where name = #{name}")
    void deleteByName(@Param("name") String name);

}

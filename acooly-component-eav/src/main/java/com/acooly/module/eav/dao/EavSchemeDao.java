/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiubo
 * date:2018-06-26
 */
package com.acooly.module.eav.dao;

import com.acooly.module.eav.entity.EavScheme;
import com.acooly.module.mybatis.EntityMybatisDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * eav_schema Mybatis Dao
 * <p>
 * Date: 2018-06-26 21:51:37
 *
 * @author qiubo
 */
public interface EavSchemeDao extends EntityMybatisDao<EavScheme> {

    @Select("select * from eav_scheme where name = #{name}")
    EavScheme findBySchemeName(@Param("name") String name);

}

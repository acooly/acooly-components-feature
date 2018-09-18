/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by shuijing
 * date:2018-06-19
 */
package com.acooly.module.config.dao;

import com.acooly.module.config.entity.AppConfig;
import com.acooly.module.mybatis.EntityMybatisDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * sys_app_config Mybatis Dao
 * <p>
 * Date: 2018-06-19 21:52:29
 *
 * @author shuijing
 */
public interface AppConfigDao extends EntityMybatisDao<AppConfig> {
    @Select("select * from sys_app_config  where  config_name=#{name}")
    AppConfig findAppConfigByName( @Param("name") String name);
    @Select("select * from sys_app_config")
    List<AppConfig> findAll();
    @Select("DELETE FROM sys_app_config WHERE config_name = #{name} ")
    void deleteByName( @Param("name") String name);
}

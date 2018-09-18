/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-03-20
 */
package com.acooly.module.portlet.dao;

import com.acooly.module.mybatis.EntityMybatisDao;
import com.acooly.module.portlet.entity.SiteConfig;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * portlet_site_config Mybatis Dao
 *
 * <p>Date: 2017-03-20 23:36:29
 *
 * @author acooly
 */
public interface SiteConfigDao extends EntityMybatisDao<SiteConfig> {

    @Select("select * from portlet_site_config where type = #{type} order by id")
    List<SiteConfig> findByType(@Param("type") String type);

    @Select("select * from portlet_site_config where type = #{type} and name = #{name}")
    SiteConfig findUnique(@Param("type") String type, @Param("name") String name);
}

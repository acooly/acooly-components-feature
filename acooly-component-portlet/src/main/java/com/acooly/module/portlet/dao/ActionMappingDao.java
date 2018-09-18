/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-03-21
 */
package com.acooly.module.portlet.dao;

import com.acooly.module.mybatis.EntityMybatisDao;
import com.acooly.module.portlet.entity.ActionMapping;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 访问映射 Mybatis Dao
 *
 * <p>Date: 2017-03-21 00:34:47
 *
 * @author acooly
 */
public interface ActionMappingDao extends EntityMybatisDao<ActionMapping> {

    @Select("select * from portlet_action_mapping where url like \"%\"#{url}")
    List<ActionMapping> findByLikeUrl(@Param("url") String url);
}

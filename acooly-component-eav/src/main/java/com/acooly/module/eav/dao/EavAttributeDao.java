/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiubo
 * date:2018-06-26
 */
package com.acooly.module.eav.dao;

import com.acooly.module.eav.entity.EavAttribute;
import com.acooly.module.mybatis.EntityMybatisDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * eav_attribute Mybatis Dao
 * <p>
 * Date: 2018-06-26 21:51:37
 *
 * @author qiubo
 */
public interface EavAttributeDao extends EntityMybatisDao<EavAttribute> {

    @Select("select * from eav_attribute  where  scheme_id=#{schemeId}")
    List<EavAttribute> findAttributesBySchemaId(@Param("schemeId") Long schemeId);
}

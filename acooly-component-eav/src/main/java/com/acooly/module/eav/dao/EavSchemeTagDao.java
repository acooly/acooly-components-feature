/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by zhangpu
 * date:2019-03-05
 */
package com.acooly.module.eav.dao;

import com.acooly.module.eav.entity.EavSchemeTag;
import com.acooly.module.mybatis.EntityMybatisDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 方案标签 Mybatis Dao
 * <p>
 * Date: 2019-03-05 18:02:36
 *
 * @author zhangpu
 */
public interface EavSchemeTagDao extends EntityMybatisDao<EavSchemeTag> {

    /**
     * 通过scheme查询tags
     *
     * @param schemeId
     * @return
     */
    @Select("select * from eav_scheme_tag where schemeId = #{schemeId}")
    List<EavSchemeTag> findBySchemeId(@Param("schemeId") Long schemeId);

}

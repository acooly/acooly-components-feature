/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by zhangpu
 * date:2019-03-05
 */
package com.acooly.module.eav.dao;

import com.acooly.module.eav.entity.EavOption;
import com.acooly.module.mybatis.EntityMybatisDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 属性选项 Mybatis Dao
 * <p>
 * Date: 2019-03-05 18:02:36
 *
 * @author zhangpu
 */
public interface EavOptionDao extends EntityMybatisDao<EavOption> {

    @Select("select * from eav_option where parent_id is null order by sort_time")
    List<EavOption> findTops();

    @Select("select * from eav_option where parent_id = #{parentId} order by sort_time")
    List<EavOption> findByParentId(@Param("parentId") Long parentId);

    @Select("select * from eav_option where path like #{path}\"%\" order by sort_time")
    List<EavOption> findByPath(@Param("path") String path);


    @Select("select * from eav_option where code = #{code} order by sort_time")
    List<EavOption> findByCode(@Param("code") String code);
}

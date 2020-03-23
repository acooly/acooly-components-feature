/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by zhangpu
 * date:2019-11-03
 */
package com.acooly.module.treetype.dao;

import com.acooly.module.mybatis.EntityMybatisDao;
import com.acooly.module.treetype.entity.TreeType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 树形分类 Mybatis Dao
 * <p>
 * Date: 2019-11-03 08:46:48
 *
 * @author zhangpu
 */
public interface TreeTypeDao extends EntityMybatisDao<TreeType> {

    /**
     * 逻辑唯一查询
     * 标志：theme+code
     *
     * @param theme
     * @param code
     * @return
     */
    @Select("select * from ac_tree_type where theme = #{theme} and code = #{code}")
    TreeType findByThemeAndCode(@Param("theme") String theme, @Param("code") String code);

}

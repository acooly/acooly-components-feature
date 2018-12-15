/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-01-04 17:53 创建
 */
package com.acooly.core.test.dao;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.test.domain.City1;
import com.acooly.module.mybatis.EntityMybatisDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author qiubo@yiji.com
 */
public interface City1MybatisDao extends EntityMybatisDao<City1> {
    @Select("select * from City1")
    List<City1> selectAll();

    /**
     * 分页查询
     *
     * @param pageInfo
     * @return
     */
    @Select("select * from City1")
    List<City1> selectAllByPage(PageInfo<City1> pageInfo);


    @Select("select * from City1 where id in #{ids}")
    List<City1> selectByIn(@Param("ids") List<String> ids);

    @Select("select * from City1  where id=#{id} for update")
    City1 selectById(@Param("id") String id);

    PageInfo<City1> selectAllByPage1(PageInfo pageInfo);

    List<City1> selectByIn1(@Param("ids") List<String> ids);

    @Select("select * from City1 where id in (#{id1},#{id2})")
    List<City1> selectByIn2(@Param("id1") String id1, @Param("id2") String id2);
}

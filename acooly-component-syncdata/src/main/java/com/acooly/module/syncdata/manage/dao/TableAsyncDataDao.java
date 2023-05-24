/*
 * acooly.cn Inc.
 * Copyright (c) 2023 All Rights Reserved.
 * create by acooly
 * date:2023-05-06
 */
 package com.acooly.module.syncdata.manage.dao;

import com.acooly.module.mybatis.EntityMybatisDao;
import com.acooly.module.syncdata.manage.entity.TableAsyncData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 同步表数据信息 Mybatis Dao
 *
 * @author acooly
 * @date 2023-05-06 08:59:00
 */
public interface TableAsyncDataDao extends EntityMybatisDao<TableAsyncData> {


    @Select("select * from table_async_data where type=#{type} order by sort_time desc")
    List<TableAsyncData> findByType(@Param("type") String type);
}

/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-10
 */
 package com.acooly.module.chart.dao;

import com.acooly.module.mybatis.EntityMybatisDao;
import com.acooly.module.chart.entity.ChartData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 图表-数据项 Mybatis Dao
 *
 * Date: 2018-10-10 11:15:20
 * @author acooly
 */
public interface ChartDataDao extends EntityMybatisDao<ChartData> {

 @Select("SELECT * FROM c_chart_data as cd WHERE cd.items_id = #{id}")
 ChartData findChartDataByItemsId (@Param("id") Long itemsId);
}

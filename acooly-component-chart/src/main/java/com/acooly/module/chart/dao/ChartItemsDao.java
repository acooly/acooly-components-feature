/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-10
 */
package com.acooly.module.chart.dao;

import com.acooly.module.chart.entity.ChartItems;
import com.acooly.module.mybatis.EntityMybatisDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 图表-图表选项 Mybatis Dao
 * <p>
 * Date: 2018-10-10 11:15:20
 *
 * @author acooly
 */
public interface ChartItemsDao extends EntityMybatisDao<ChartItems> {

    @Select("SELECT * FROM c_chart_items as cd WHERE cd.chart_id = #{chartId}")
    List<ChartItems> findByChartId(@Param("chartId") Long chartId);

    @Select("SELECT * FROM c_chart_items cd WHERE cd.chart_id = #{chartId} and cd.status=#{status} order by order_time desc")
    List<ChartItems> findByChartIdAndStatus(@Param("chartId") Long chartId, @Param("status") String status);
}

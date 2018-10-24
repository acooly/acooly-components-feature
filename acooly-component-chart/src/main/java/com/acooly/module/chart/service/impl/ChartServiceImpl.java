/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-10
 */
package com.acooly.module.chart.service.impl;

import com.acooly.module.chart.entity.ChartItems;
import com.acooly.module.chart.service.ChartItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.chart.service.ChartService;
import com.acooly.module.chart.dao.ChartDao;
import com.acooly.module.chart.entity.Chart;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 图表-主题 Service实现
 *
 * Date: 2018-10-10 11:15:20
 *
 * @author acooly
 *
 */
@Service("chartService")
public class ChartServiceImpl extends EntityServiceImpl<Chart, ChartDao> implements ChartService {
    @Autowired
    private ChartItemsService chartItemsService;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void removeChartAndItemsAndData(Long chartId){
        Chart chart = this.getEntityDao().get(chartId);
        if (chart!=null){
            List<ChartItems> chartItemsList = chartItemsService.findByChartId(chart.getId());
            for (ChartItems chartItems :chartItemsList ){
                chartItemsService.removeChartItemsAndChartDataById(chartItems.getId());
            }
            this.getEntityDao().removeById(chart.getId());
        }

    }

}

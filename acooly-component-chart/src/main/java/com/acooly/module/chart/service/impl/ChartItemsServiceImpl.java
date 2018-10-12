/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-10
 */
package com.acooly.module.chart.service.impl;

import com.acooly.module.chart.entity.ChartData;
import com.acooly.module.chart.service.ChartDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.chart.service.ChartItemsService;
import com.acooly.module.chart.dao.ChartItemsDao;
import com.acooly.module.chart.entity.ChartItems;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 图表-图表选项 Service实现
 *
 * Date: 2018-10-10 11:15:20
 *
 * @author acooly
 *
 */
@Service("chartItemsService")
public class ChartItemsServiceImpl extends EntityServiceImpl<ChartItems, ChartItemsDao> implements ChartItemsService {

    @Autowired
    private ChartDataService chartDataService;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void saveOrUpdateChartItemsAndChartData(ChartItems entity,Boolean isSave){
        if (isSave){
            entity.setOrderTime(new Date());
            this.getEntityDao().create(entity);
            ChartData chartData = new ChartData();
            BeanUtils.copyProperties(entity,chartData);
            chartData.setItemsId(entity.getId());
            chartDataService.save(chartData);
        }else {
            this.getEntityDao().update(entity);
           ChartData chartData = chartDataService.findChartDataByItemsId(entity.getId());
           BeanUtils.copyProperties(entity,chartData);
            chartDataService.update(chartData);
        }


    }


}

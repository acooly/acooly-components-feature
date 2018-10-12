/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-10
 *
 */
package com.acooly.module.chart.service;

import java.util.List;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.chart.entity.ChartItems;
import com.acooly.module.chart.enums.StatusEnum;

/**
 * 图表-图表选项 Service接口
 *
 * Date: 2018-10-10 11:15:20
 *
 * @author acooly
 *
 */
public interface ChartItemsService extends EntityService<ChartItems> {

    void saveOrUpdateChartItemsAndChartData(ChartItems chartItems,Boolean isSave);

	List<ChartItems> findByChartId(Long chartId);

	List<ChartItems> findByChartIdAndStatus(Long chartId, StatusEnum status);

}

/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-10
 *
 */
package com.acooly.module.chart.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.chart.entity.Chart;

/**
 * 图表-主题 Service接口
 *
 * Date: 2018-10-10 11:15:20
 * @author acooly
 *
 */
public interface ChartService extends EntityService<Chart> {

    void removeChartAndItemsAndData(Long chartId);

}

/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-10
 */
package com.acooly.module.chart.service.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.chart.service.ChartDataService;
import com.acooly.module.chart.dao.ChartDataDao;
import com.acooly.module.chart.entity.ChartData;

/**
 * 图表-数据项 Service实现
 *
 * Date: 2018-10-10 11:15:20
 *
 * @author acooly
 *
 */
@Service("chartDataService")
public class ChartDataServiceImpl extends EntityServiceImpl<ChartData, ChartDataDao> implements ChartDataService {

}

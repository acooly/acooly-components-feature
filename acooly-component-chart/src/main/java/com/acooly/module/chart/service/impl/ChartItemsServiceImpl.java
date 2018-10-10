/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-10
 */
package com.acooly.module.chart.service.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.chart.service.ChartItemsService;
import com.acooly.module.chart.dao.ChartItemsDao;
import com.acooly.module.chart.entity.ChartItems;

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

}

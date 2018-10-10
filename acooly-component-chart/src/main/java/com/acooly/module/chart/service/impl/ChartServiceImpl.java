/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-10
 */
package com.acooly.module.chart.service.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.chart.service.ChartService;
import com.acooly.module.chart.dao.ChartDao;
import com.acooly.module.chart.entity.Chart;

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

}

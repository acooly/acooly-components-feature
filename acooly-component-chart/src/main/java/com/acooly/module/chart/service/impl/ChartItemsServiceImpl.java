/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-10
 */
package com.acooly.module.chart.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.chart.dao.ChartItemsDao;
import com.acooly.module.chart.entity.ChartData;
import com.acooly.module.chart.entity.ChartItems;
import com.acooly.module.chart.enums.StatusEnum;
import com.acooly.module.chart.handle.ChartUtils;
import com.acooly.module.chart.service.ChartDataService;
import com.acooly.module.chart.service.ChartItemsService;

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
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdateChartItemsAndChartData(ChartItems entity, Boolean isSave) {
		String sqlData = entity.getSqlData();
		String sqlDataUpperCase = sqlData.toUpperCase();

		ChartUtils.checkSql(entity.getWhereDataJson().toUpperCase());
		ChartUtils.checkSql(sqlDataUpperCase);

		if (isSave) {
			entity.setOrderTime(new Date());
			this.getEntityDao().create(entity);
			ChartData chartData = new ChartData();
			chartData.setWhereData(entity.getWhereDataJson());
			chartData.setSqlData(sqlData);
			chartData.setFieldMapped(entity.getFieldMapped());
			chartData.setItemsId(entity.getId());
			chartData.setChartId(entity.getChartId());
			chartData.setItemsId(entity.getId());
			chartDataService.save(chartData);
		} else {
			this.getEntityDao().update(entity);
			ChartData chartData = chartDataService.findChartDataByItemsId(entity.getId());
			chartData.setWhereData(entity.getWhereDataJson());
			chartData.setSqlData(sqlData);
			chartData.setFieldMapped(entity.getFieldMapped());
			chartDataService.update(chartData);
		}

	}

	@Override
	public List<ChartItems> findByChartId(Long chartId) {
		return getEntityDao().findByChartId(chartId);
	}

	@Override
	public List<ChartItems> findByChartIdAndStatus(Long chartId, StatusEnum status) {
		return getEntityDao().findByChartIdAndStatus(chartId, status.code());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void removeChartItemsAndChartDataById(Long chartItemsId) {
		ChartItems chartItems = this.getEntityDao().get(chartItemsId);
		if (chartItems != null) {
			ChartData chartData = chartDataService.findChartDataByItemsId(chartItemsId);
			if (chartData != null) {
				chartDataService.removeById(chartData.getId());
			}

			this.getEntityDao().removeById(chartItems.getId());
		}

	}

}

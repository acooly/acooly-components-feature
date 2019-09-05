package com.acooly.module.chart;

import org.springframework.context.ConfigurableApplicationContext;

import com.acooly.core.common.boot.component.ComponentInitializer;

/**
 * @author cuifuqiang
 */
public class ChartComponentInitializer implements ComponentInitializer {

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		setPropertyIfMissing("acooly.security.xss.exclusions.chart", "/manage/module/chart/chartItems/*.html");

		setPropertyIfMissing("acooly.ds.dbPatchs.c_chart_items[0].columnName", "is_show");
		setPropertyIfMissing("acooly.ds.dbPatchs.c_chart_items[0].patchSql",
				" ALTER TABLE `c_chart_items`  ADD COLUMN `is_show`  varchar(255) NULL DEFAULT 'NO' COMMENT '是否显示数据值' AFTER `loop_time`;");

	}
}

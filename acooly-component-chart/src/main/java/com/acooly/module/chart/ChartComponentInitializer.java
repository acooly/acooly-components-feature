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

	}
}

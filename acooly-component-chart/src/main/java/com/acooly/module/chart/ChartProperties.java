package com.acooly.module.chart;

import static com.acooly.module.chart.ChartProperties.PREFIX;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author cuifuq
 */
@ConfigurationProperties(prefix = PREFIX)
@Data
public class ChartProperties {
	public static final String PREFIX = "acooly.chart";
	private Boolean enable = true;
	private String storagePath = "chart";

}

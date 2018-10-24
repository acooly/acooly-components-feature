package com.acooly.module.chart.handle.analyse.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * x，y轴数据解析
 * 
 * @author cuifuq
 *
 */
public class ShaftDataDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String title;

	private Map<String, Object> xShaft;

	private List<Map<String, Object>> yShafts;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Map<String, Object> getxShaft() {
		return xShaft;
	}

	public void setxShaft(Map<String, Object> xShaft) {
		this.xShaft = xShaft;
	}

	public List<Map<String, Object>> getyShafts() {
		return yShafts;
	}

	public void setyShafts(List<Map<String, Object>> yShafts) {
		this.yShafts = yShafts;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}

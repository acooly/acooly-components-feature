/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-10
 */
package com.acooly.module.chart.entity;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.chart.enums.ChartItemsIsShowEnum;
import com.acooly.module.chart.enums.StatusEnum;
import com.acooly.module.chart.enums.TypeEnum;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Map;

/**
 * 图表-图表选项 Entity
 *
 * @author acooly Date: 2018-10-10 11:15:20
 */
@Entity
@Table(name = "c_chart_items")
@Getter
@Setter
public class ChartItems extends AbstractEntity {

	/**
	 * 主题id
	 */
	@NotNull
	private Long chartId;

	/**
	 * 标题
	 */
	@NotBlank
	@Size(max = 64)
	private String title;

	/**
	 * 图表类型
	 */
	@Enumerated(EnumType.STRING)
	@NotNull
	private TypeEnum type;

	/**
	 * 状态
	 */
	@Enumerated(EnumType.STRING)
	@NotNull
	private StatusEnum status;

	/**
	 * 是否显示数据值
	 */
	@Enumerated(EnumType.STRING)
	private ChartItemsIsShowEnum isShow;

	/**
	 * 列表数据和下载
	 */
	@Enumerated(EnumType.STRING)
	private ChartItemsIsShowEnum isDataListShow;

	/**
	 * 高
	 */
	@NotNull
	private Long height = 50L;

	/**
	 * 宽
	 */
	@NotNull
	private Long width = 50L;

	/**
	 * 循环时间
	 */
	@NotNull
	private Long loopTime = 0L;

	/**
	 * x轴
	 */
	@NotBlank
	private String xShaft;

	/**
	 * y轴
	 */
	@NotBlank
	private String yShaft;

	/**
	 * 排序
	 */
	private Date orderTime;

	/**
	 * 备注
	 */
	private String comments;

	@Transient
	private String sqlData;

	@Transient
	private String fieldMapped;

	@Transient
	private JSONObject fieldMappedJson;

	@Transient
	private Map<String, Object> fieldMappedMap;

	/**
	 * 查询条件-json to String
	 */
	@Transient
	private String whereDataJson;

	public String getxShaft() {
		return xShaft;
	}

	public void setxShaft(String xShaft) {
		this.xShaft = xShaft;
	}

	public String getyShaft() {
		return yShaft;
	}

	public void setyShaft(String yShaft) {
		this.yShaft = yShaft;
	}
}

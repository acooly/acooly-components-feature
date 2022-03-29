/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-10
 */
package com.acooly.module.chart.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.util.Strings;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.chart.entity.dto.WhereDataDto;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 图表-数据项 Entity
 *
 * @author acooly Date: 2018-10-10 11:15:20
 */
@Slf4j
@Entity
@Table(name = "c_chart_data")
@Getter
@Setter
public class ChartData extends AbstractEntity {

	/**
	 * 主题id
	 */
	@NotNull
	private Long chartId;

	/**
	 * 图表选项id
	 */
	@NotNull
	private Long itemsId;

	/**
	 * where条件数据
	 */
	private String whereData;

	/**
	 * sql表达式
	 */
	@NotBlank
	private String sqlData;

	/**
	 * 数据字段
	 */
	@NotBlank
	private String fieldMapped;

	/**
	 * 备注
	 */
	private String comments;

	/**
	 * 
	 */
	@Transient
	private List<WhereDataDto> whereDataList;

	public List<WhereDataDto> getWhereDataList() {
		String whereDataStr = getWhereData();
		try {
			if (Strings.isNotBlank(whereDataStr)) {
				List<WhereDataDto> list = JSON.parseObject(whereDataStr, List.class);
				return list;
			}
		} catch (Exception e) {
			log.info("图表组件-解析whereData失败:{}",e);
		}
		return Lists.newArrayList();
	}

}

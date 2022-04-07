/*
* acooly.cn Inc.
* Copyright (c) 2018 All Rights Reserved.
* create by acooly
* date:2018-10-10
*/
package com.acooly.module.chart.entity;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.chart.enums.StatusEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * 图表-主题 Entity
 *
 * @author acooly
 * Date: 2018-10-10 11:15:20
 */
@Entity
@Table(name = "c_chart")
@Getter
@Setter
public class Chart extends AbstractEntity {

	/** 标题 */
	@Size(max=64)
    private String title;

	/** 状态 */
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

	/** 备注 */
    private String comments;

}

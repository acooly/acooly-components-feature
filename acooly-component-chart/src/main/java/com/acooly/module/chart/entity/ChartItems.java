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

import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.chart.enums.StatusEnum;
import java.util.Date;
import com.acooly.module.chart.enums.TypeEnum;

/**
 * 图表-图表选项 Entity
 *
 * @author acooly
 * Date: 2018-10-10 11:15:20
 */
@Entity
@Table(name = "c_chart_items")
@Getter
@Setter
public class ChartItems extends AbstractEntity {

	/** 主题id */
	@NotNull
    private Long chartId;

	/** 标题 */
	@NotEmpty
	@Size(max=64)
    private String title;

	/** 图表类型 */
    @Enumerated(EnumType.STRING)
	@NotNull
    private TypeEnum type;

	/** 状态 */
    @Enumerated(EnumType.STRING)
	@NotNull
    private StatusEnum status;

	/** 循环时间 */
	@NotNull
    private Long loopTime = 0l;

	/** x轴 */
	@NotEmpty
	@Size(max=128)
    private String xShaft;

	/** y轴 */
	@NotEmpty
	@Size(max=128)
    private String yShaft;

	/** 排序 */
	@NotNull
    private Date orderTime;

	/** 备注 */
	@Size(max=255)
    private String comments;

}

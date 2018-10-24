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
	@Size(max=255)
    private String comments;

}

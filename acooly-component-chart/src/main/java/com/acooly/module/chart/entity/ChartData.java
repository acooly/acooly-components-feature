/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by acooly
 * date:2018-10-10
 */
package com.acooly.module.chart.entity;


import com.acooly.core.common.domain.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 图表-数据项 Entity
 *
 * @author acooly
 * Date: 2018-10-10 11:15:20
 */
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
     * sql表达式
     */
    @NotBlank
    @Size(max = 2048)
    private String sqlData;

    /**
     * 数据字段
     */
    @NotBlank
    @Size(max = 512)
    private String fieldMapped;

    /**
     * 备注
     */
    @Size(max = 255)
    private String comments;

}

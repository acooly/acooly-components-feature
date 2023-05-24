/*
* acooly.cn Inc.
* Copyright (c) 2023 All Rights Reserved.
* create by acooly
* date:2023-05-06
*/
package com.acooly.module.syncdata.manage.entity;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import javax.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;
import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.syncdata.common.enums.QueryTypeEnum;
import com.acooly.module.syncdata.common.enums.QueryColumnTypeEnum;
import java.util.Date;

/**
 * 同步表数据信息 Entity
 *
 * @author acooly
 * @date 2023-05-06 08:59:00
 */
@Entity
@Table(name = "table_async_data")
@Getter
@Setter
public class TableAsyncData extends AbstractEntity {

    /**
     * 类型
     */
	@Size(max = 32)
    private String type;

    /**
     * 表名
     */
	@Size(max = 64)
    private String tableName;

    /**
     * 查询类型
     */
    @Enumerated(EnumType.STRING)
    private QueryTypeEnum queryType;

    /**
     * 字段类型
     */
    @Enumerated(EnumType.STRING)
    private QueryColumnTypeEnum queryColumnType;

    /**
     * 字段名称
     */
	@Size(max = 64)
    private String queryColumnName;

    /**
     * 字段值
     */
	@Size(max = 64)
    private String queryColumnValue;

    /**
     * 查询条数
     */
    private int queryRows=20;

    /**
     * 排序时间
     */
    private Date sortTime;

}

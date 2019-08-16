/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiubo
 * date:2018-06-26
 */
package com.acooly.module.eav.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.common.type.DBMap;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * eav_entity Entity
 *
 * @author qiubo
 * @author zhangpu
 * Date: 2018-06-26 21:51:37
 */
@Data
@Entity
@Table(name = "eav_entity")
public class EavEntity extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 方案id
     */
    @NotNull
    private Long schemeId;

    /**
     * 方案标题
     * （表中文名，label）
     */
    @NotBlank
    @Size(max = 128)
    private String schemeTitle;

    /**
     * 方案名称（表名）
     */
    @NotBlank
    @Size(max = 128)
    private String schemeName;


    /**
     * 备注
     */
    @Size(max = 128)
    private String memo;


    /**
     * 内容
     */
    @NotBlank
    private DBMap value;


}

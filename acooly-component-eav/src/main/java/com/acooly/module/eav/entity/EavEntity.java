/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiubo
 * date:2018-06-26
 */
package com.acooly.module.eav.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.common.type.DBMap;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * eav_entity Entity
 *
 * @author qiubo
 * Date: 2018-06-26 21:51:37
 */
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
    private Long schemaId;

    /**
     * 名称
     */
    @NotEmpty
    @Size(max = 128)
    private String name;

    /**
     * 备注
     */
    @Size(max = 128)
    private String comments;


    /**
     * 内容
     */
    @NotEmpty
    private DBMap value;

    public Long getSchemaId() {
        return this.schemaId;
    }

    public void setSchemaId(Long schemaId) {
        this.schemaId = schemaId;
    }

    public DBMap getValue() {
        return this.value;
    }

    public void setValue(DBMap value) {
        this.value = value;
    }
}

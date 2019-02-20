/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiubo
 * date:2018-06-27
 */
package com.acooly.module.eav.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.common.exception.OrderCheckException;
import com.acooly.module.eav.enums.AttributeTypeEnum;
import com.google.common.base.Strings;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * eav_attribute Entity
 *
 * @author qiubo
 * Date: 2018-06-27 14:36:41
 */
@Entity
@Table(name = "eav_attribute")
public class EavAttribute extends AbstractEntity {
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
     * 字段名称
     */
    @NotEmpty
    @Size(max = 128)
    private String name;

    /**
     * 展示名称
     */
    @NotEmpty
    @Size(max = 128)
    private String displayName;


    /**
     * 是否可以为空
     */
    private Boolean nullable;

    /**
     * 最小值
     */
    private Long minimum;

    /**
     * 最大值
     */
    private Long maximum;

    /**
     * 最小长度
     */
    private Integer minLength;

    /**
     * 最大长度
     */
    private Integer maxLength;

    /**
     * 正则表达式
     */
    @Size(max = 30)
    private String regex;

    /**
     * 枚举值
     */
    @Size(max = 128)
    private String enumValue;


    /**
     * 备注
     */
    @Size(max = 128)
    private String memo;

    /**
     * 是否逻辑唯一键
     */
    private boolean key = false;

    /**
     * 属性类型
     */
    @Enumerated(EnumType.STRING)
    private AttributeTypeEnum attributeType;

    public void createCheck() {
        OrderCheckException.throwIf(!Strings.isNullOrEmpty(name), "name", "名称不能为空");
        OrderCheckException.throwIf(!Strings.isNullOrEmpty(displayName), "displayName", "展示名称不能为空");
        OrderCheckException.throwIf(schemaId != null, "schemaId", "schemaId不能为空");
    }


    public Long getSchemaId() {
        return this.schemaId;
    }

    public void setSchemaId(Long schemaId) {
        this.schemaId = schemaId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Boolean getNullable() {
        return this.nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public Long getMinimum() {
        return this.minimum;
    }

    public void setMinimum(Long minimum) {
        this.minimum = minimum;
    }

    public Long getMaximum() {
        return this.maximum;
    }

    public void setMaximum(Long maximum) {
        this.maximum = maximum;
    }

    public Integer getMinLength() {
        return this.minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return this.maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public String getRegex() {
        return this.regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getEnumValue() {
        return this.enumValue;
    }

    public void setEnumValue(String enumValue) {
        this.enumValue = enumValue;
    }

    public AttributeTypeEnum getAttributeType() {
        return this.attributeType;
    }

    public void setAttributeType(AttributeTypeEnum attributeType) {
        this.attributeType = attributeType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isKey() {
        return key;
    }

    public void setKey(boolean key) {
        this.key = key;
    }
}

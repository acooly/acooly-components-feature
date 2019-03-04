/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiubo
 * date:2018-06-27
 */
package com.acooly.module.eav.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.common.exception.OrderCheckException;
import com.acooly.core.utils.arithmetic.tree.TreeNode;
import com.acooly.core.utils.enums.WhetherStatus;
import com.acooly.module.eav.enums.AttributeFormatEnum;
import com.acooly.module.eav.enums.AttributeShowTypeEnum;
import com.acooly.module.eav.enums.AttributeTypeEnum;
import com.google.common.base.Strings;
import lombok.Data;
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
@Data
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
    private Long schemeId;

    /**
     * 方案名称
     */
    @NotEmpty
    @Size(max = 128)
    private String schemeName;

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
     * 是否为逻辑键
     */
    @Enumerated(EnumType.STRING)
    private WhetherStatus keyable = WhetherStatus.no;


    /**
     * 是否可以为空
     */
    @Enumerated(EnumType.STRING)
    private WhetherStatus nullable = WhetherStatus.yes;

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
     * 正则验证消息
     */
    private String regexMessage;


    /**
     * 枚举值
     */
    @Size(max = 128)
    private String enumValue;

    /**
     * 显示类型
     * 1：列表，2：创建, 3:编辑,4:详情
     */
    private int showType = AttributeShowTypeEnum.getAllValue();

    /**
     * 显示格式
     */
    @Enumerated(EnumType.STRING)
    private AttributeFormatEnum showFormat = AttributeFormatEnum.NORMAL;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 备注
     */
    @Size(max = 128)
    private String memo;


    /**
     * 属性类型
     */
    @Enumerated(EnumType.STRING)
    private AttributeTypeEnum attributeType;
    

    public void createCheck() {
        OrderCheckException.throwIf(!Strings.isNullOrEmpty(name), "name", "名称不能为空");
        OrderCheckException.throwIf(!Strings.isNullOrEmpty(displayName), "displayName", "展示名称不能为空");
        OrderCheckException.throwIf(schemeId != null, "schemaId", "schemaId不能为空");
    }

}

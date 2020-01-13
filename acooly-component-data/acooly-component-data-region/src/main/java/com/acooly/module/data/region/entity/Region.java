/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by zhangpu
 * date:2019-05-06
 */
package com.acooly.module.data.region.entity;


import com.acooly.core.common.domain.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 省市区编码表 Entity
 *
 * @author zhangpu
 * Date: 2019-05-06 18:32:21
 */
@Entity
@Table(name = "data_region")
@Getter
@Setter
public class Region extends AbstractEntity {

    public static final Long ROOT_ID = 100000L;


    /**
     * 区域父编码
     */
    @NotNull
    private Long parentId;

    /**
     * 区域名称
     */
    @NotBlank
    @Size(max = 64)
    private String name;

    /**
     * 首字母拼音
     */
    @Size(max = 32)
    private String pinyin;

    /**
     * 排序值
     */
    private Long sortTime;

    /**
     * 备注
     */
    @Size(max = 128)
    private String comments;

}

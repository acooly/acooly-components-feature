/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by zhangpu
 * date:2019-03-05
 */
package com.acooly.module.eav.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.utils.enums.AbleStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 属性选项 Entity
 *
 * @author zhangpu
 * Date: 2019-03-05 18:52:41
 */
@Entity
@Table(name = "eav_option")
@Getter
@Setter
public class EavOption extends AbstractEntity {

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * path
     */
    @Size(max = 255)
    private String path;

    /**
     * 编码
     */
    @NotBlank
    @Size(max = 64)
    private String code;

    /**
     * 名称
     */
    @NotBlank
    @Size(max = 64)
    private String name;

    /**
     * 排序值
     */
    private Long sortTime = System.currentTimeMillis();

    /**
     * 状态
     */
    @NotBlank
    @Size(max = 16)
    private String status = AbleStatus.enable.code();

    /**
     * 备注
     */
    @Size(max = 255)
    private String memo;

}

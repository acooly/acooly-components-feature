/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by zhangpu
 * date:2019-03-05
 */
package com.acooly.module.eav.entity;


import com.acooly.core.common.domain.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 方案标签 Entity
 *
 * @author zhangpu
 * Date: 2019-03-05 18:02:36
 */
@Entity
@Table(name = "eav_scheme_tag")
@Getter
@Setter
public class EavSchemeTag extends AbstractEntity {

    /**
     * 方案ID
     */
    @NotNull
    private Long schemeId;

    /**
     * 标签
     */
    @NotBlank
    @Size(max = 64)
    private String tag;

    /**
     * 备注
     */
    @Size(max = 255)
    private String memo;

    public EavSchemeTag() {
    }

    public EavSchemeTag(@NotNull Long schemeId, String tag) {
        this.schemeId = schemeId;
        this.tag = tag;
    }
}

/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-04-26
 */
package com.acooly.module.cms.domain;

import com.acooly.core.common.domain.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * 编码 Entity
 *
 * @author acooly Date: 2017-04-26 17:16:38
 */
@Entity
@Table(name = "cms_code")
@Getter
@Setter
public class CmsCode extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 二级编码
     */
    @NotBlank
    private String keycode;

    /**
     * 类型编码，关联CMS_CONTENT_TYPE
     */
    private String typeCode;
    /**
     * 描述
     */
    private String descn;
    /**
     * 状态
     */
    private Integer status = 1;
}

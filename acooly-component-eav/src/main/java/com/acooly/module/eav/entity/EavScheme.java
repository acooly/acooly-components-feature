/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiubo
 * date:2018-06-26
 */
package com.acooly.module.eav.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.eav.EavConstants;
import com.acooly.module.eav.enums.AttributePermissionEnum;
import com.acooly.module.eav.enums.SchemePermissionEnum;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * EAV实体方案
 *
 * @author qiubo
 * @author zhangpu
 * Date: 2018-06-26 21:51:37
 */
@Data
@Entity
@Table(name = "eav_scheme")
public class EavScheme extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 名称（表名）
     */
    @NotEmpty
    @Size(max = 128)
    private String name;

    /**
     * 方案标题（中文名）
     */
    @NotEmpty
    @Size(max = 128)
    private String title;


    private int panelWidth = EavConstants.PANEL_WIDTH_DEFAULT;

    private int panelHeight = EavConstants.PANEL_HEIGHT_DEFAULT;

    private int permission = SchemePermissionEnum.LIST.getCode();

    /**
     * 备注
     */
    @Size(max = 128)
    private String memo;

}

/*
* acooly.cn Inc.
* Copyright (c) 2022 All Rights Reserved.
* create by zhangpu
* date:2022-10-05
*/
package cn.acooly.component.rbac.entity;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import com.acooly.core.common.domain.AbstractEntity;
import cn.acooly.component.rbac.enums.ResourceShowMode;
import cn.acooly.component.rbac.enums.ResourceType;
import com.acooly.core.utils.enums.WhetherStatus;

/**
 * 权限资源表 Entity
 *
 * @author zhangpu
 * @date 2022-10-05 23:03:16
 */
@Entity
@Table(name = "rbac_resource")
@Getter
@Setter
public class RbacResource extends AbstractEntity {

    /**
     * 父主键
     */
    private Long parentId;
    
    /**
     * 唯一编码
     */
    @Size(max = 128)
    private String code;

    /**
     * 资源名称
     */
	@NotBlank
	@Size(max = 64)
    private String name;

    /**
     * 资源值
     */
	@Size(max = 256)
    private String value;

    /**
     * 资源类型
     */
    @Enumerated(EnumType.STRING)
	@NotNull
    private ResourceType type;

    /**
     * 是否显示
     */
    @Enumerated(EnumType.STRING)
	@NotNull
    private WhetherStatus showStatus;

    /**
     * 加载方式
     */
    @Enumerated(EnumType.STRING)
    private ResourceShowMode showMode;

    /**
     * 排序时间
     */
	@NotNull
    private Long orderTime;

    /**
     * 图标
     */
	@Size(max = 64)
    private String icon;

    /**
     * 描述
     */
	@Size(max = 256)
    private String memo;

}

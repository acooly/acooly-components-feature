/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2023-07-25 11:04
 */
package com.acooly.module.security.dto;

import com.acooly.core.common.facade.InfoBase;
import lombok.Getter;
import lombok.Setter;

/**
 * 组织结构对应的负责人信息
 *
 * @author zhangpu
 * @date 2023-07-25 11:04
 */
@Getter
@Setter
public class OrgManager extends InfoBase {

    /**
     * 组织结构ID
     */
    private Long id;

    /**
     * 组织结构名称
     */
    private String name;

    /**
     * 组织结构的负责人用户名
     */
    private String username;

}

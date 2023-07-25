/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2023-07-25 11:16
 */
package com.acooly.module.security.dto;

import com.acooly.core.common.facade.InfoBase;
import lombok.Getter;
import lombok.Setter;

/**
 * 多级组织结构的负责人信息
 * @author zhangpu
 * @date 2023-07-25 11:16
 */
@Getter
@Setter
public class OrgManagers extends InfoBase {

    /**
     * 当前组织结构的负责人
     */
    private OrgManager current;

    /**
     * 父级组织结构的负责人
     */
    private OrgManager parent;

}

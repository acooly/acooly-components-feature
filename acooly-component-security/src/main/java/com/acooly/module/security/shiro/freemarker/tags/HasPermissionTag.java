/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-12-07 22:05
 */
package com.acooly.module.security.shiro.freemarker.tags;

import lombok.extern.slf4j.Slf4j;

/**
 * 有权限标签
 *
 * @author zhangpu
 * @date 2020-12-07 22:05
 */
@Slf4j
public class HasPermissionTag extends PermissionTag {

    @Override
    protected boolean showTagBody(String p) {
        return isPermitted(p);
    }
}

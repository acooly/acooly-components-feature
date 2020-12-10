/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-12-11 00:39
 */
package com.acooly.module.security.shiro.freemarker.tags;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangpu
 * @date 2020-12-11 00:39
 */
@Slf4j
public class LacksRoleTag extends RoleTag {
    @Override
    protected boolean showTagBody(String roleName) {
        return !hasAnyRole(roleName);
    }
}

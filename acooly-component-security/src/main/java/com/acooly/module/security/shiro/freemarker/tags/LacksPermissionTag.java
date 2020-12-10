/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-12-11 00:26
 */
package com.acooly.module.security.shiro.freemarker.tags;

import lombok.extern.slf4j.Slf4j;

/**
 * 判断没有指定权限
 *
 * @author zhangpu
 * @date 2020-12-11 00:26
 */
@Slf4j
public class LacksPermissionTag extends PermissionTag {
    @Override
    protected boolean showTagBody(String p) {
        return !isPermitted(p);
    }
}

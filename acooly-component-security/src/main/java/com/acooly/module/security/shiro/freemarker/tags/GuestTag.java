/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-12-11 00:40
 */
package com.acooly.module.security.shiro.freemarker.tags;

import com.acooly.module.security.shiro.freemarker.SecurityBaseTag;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

/**
 * @author zhangpu
 * @date 2020-12-11 00:40
 */
@Slf4j
public class GuestTag extends SecurityBaseTag {
    @Override
    public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
        if (getSubject() == null || getSubject().getPrincipal() == null) {
            renderBody(env, body);
        }
    }
}

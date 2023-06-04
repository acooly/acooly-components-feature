/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-12-07 21:56
 */
package com.acooly.module.security.shiro.freemarker;

import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.Strings;
import freemarker.core.Environment;
import freemarker.template.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.io.IOException;
import java.util.Map;

/**
 * shiro-freemarker-tag 基类
 *
 * @author zhangpu
 * @date 2020-12-07 21:56
 */
@Slf4j
public abstract class SecurityBaseTag implements TemplateDirectiveModel {

    public static int TIME_OUT = 15 * 1000;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        verifyParameters(params);
        render(env, params, body);
    }

    /**
     * 抽象模板方法：渲染标签显示
     *
     * @param env
     * @param params
     * @param body
     * @throws IOException
     * @throws TemplateException
     */
    public abstract void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException;

    protected String getParameter(Map params, String name) {
        Object value = params.get(name);
        if (value instanceof SimpleScalar) {
            return ((SimpleScalar) value).getAsString();
        }
        return null;
    }

    /**
     * 获取本地Subject
     *
     * @return
     */
    protected Subject getSubject() {
        return SecurityUtils.getSubject();
    }


    protected void verifyParameters(Map params) throws TemplateModelException {
    }

    protected void renderBody(Environment env, TemplateDirectiveBody body) throws IOException, TemplateException {
        if (body != null) {
            body.render(env.getOut());
        }
    }

    /**
     * 解耦方式判断是否开启SSO
     *
     * @return
     */
    protected boolean ssoEnable() {
        String ssoEnable = System.getProperty("acooly.sso.freemarker.include");
        return Strings.equalsIgnoreCase(ssoEnable, "true");
    }

    protected String getSsoRoot() {
        String ssoServerUrl = System.getProperty("acooly.sso.ssoServerUrl");
        if (Strings.isBlank(ssoServerUrl)) {
            log.error("未获取到`ssoServerUrl`参数，请确认该参数已配置");
            return null;
        }
        return Servlets.getServerRoot(ssoServerUrl);
    }

}

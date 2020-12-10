/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-12-07 22:01
 */
package com.acooly.module.security.shiro.freemarker.tags;

import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.security.JWTUtils;
import com.acooly.module.security.domain.User;
import com.acooly.module.security.shiro.freemarker.SecurityBaseTag;
import com.acooly.module.security.utils.ShiroUtils;
import com.github.kevinsawicki.http.HttpRequest;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * 权限判断Tag基类
 *
 * @author zhangpu
 * @date 2020-12-07 22:01
 */
@Slf4j
public abstract class PermissionTag extends SecurityBaseTag {

    String getName(Map params) {
        return getParameter(params, "name");
    }

    @Override
    protected void verifyParameters(Map params) throws TemplateModelException {
        String permission = getName(params);
        if (permission == null || permission.length() == 0) {
            throw new TemplateModelException("标签必须设置`name`属性，`name`属性是需要判断的权限值");
        }
    }

    @Override
    public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
        String p = getName(params);
        boolean show = showTagBody(p);
        if (show) {
            renderBody(env, body);
        }
    }

    /**
     * 根据父类的subject判断当前传入的权限值是否有权限
     *
     * @param p
     * @return
     */
    protected boolean isPermitted(String p) {
        if (ssoEnable()) {
            return isSsoPermitted(p);
        } else {
            return isLocalPermitted(p);
        }
    }


    /**
     * 根据本地subject判断当前传入的权限值是否有权限
     *
     * @param p
     * @return
     */
    protected boolean isLocalPermitted(String p) {
        return getSubject() != null && getSubject().isPermitted(p);
    }


    /**
     * SSO远程判断是否有权限
     *
     * @param p
     * @return
     */
    protected boolean isSsoPermitted(String p) {
        String permittedPath = getSsoRoot() + "/role/facade/isPermitted";
        User user = ShiroUtils.getCurrentUser();
        String username = user.getUsername();
        String body = null;
        try {
            body = HttpRequest.post(permittedPath.trim())
                    .form("uri", p)
                    .form("username", username)
                    .readTimeout(TIME_OUT).connectTimeout(TIME_OUT)
                    .trustAllCerts().trustAllHosts()
                    .body();
        } catch (Exception e) {
            log.error("sso hasPermission 用户:{} 权限校验出错。权限：{},请求路径为：{}", username, p, e);
            return false;
        }
        if ((Strings.equalsIgnoreCase(body, Boolean.TRUE.toString()) || Strings.equalsIgnoreCase(body, Boolean.FALSE.toString()))) {
            Boolean permitted = Boolean.valueOf(body);
            log.debug("sso hasPermission 用户:{} 权限校验结果:{}, 权限:{} ", username, permitted, p);
            return Boolean.valueOf(body);
        }
        log.debug("sso hasPermission 用户:{} 权限校验结果:{}, 权限:{} ", username, false, p);
        return false;
    }


    /**
     * 抽象模板:渲染显示标签内的内容
     *
     * @param p
     * @return
     */
    protected abstract boolean showTagBody(String p);

}

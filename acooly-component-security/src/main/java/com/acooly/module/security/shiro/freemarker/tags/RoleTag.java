/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-12-07 23:49
 */
package com.acooly.module.security.shiro.freemarker.tags;

import com.acooly.core.utils.Strings;
import com.acooly.module.security.domain.User;
import com.acooly.module.security.shiro.freemarker.SecurityBaseTag;
import com.acooly.module.security.utils.ShiroUtils;
import com.github.kevinsawicki.http.HttpRequest;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;

import java.io.IOException;
import java.util.Map;

/**
 * 角色判断Tag基类
 *
 * @author zhangpu
 * @date 2020-12-07 23:49
 */
@Slf4j
public abstract class RoleTag extends SecurityBaseTag {
    private static final String ROLE_NAMES_DELIMETER = ",";

    String getName(Map params) {
        return getParameter(params, "name");
    }

    @Override
    public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
        if (showTagBody(getName(params))) {
            renderBody(env, body);
        }
    }

    /**
     * 判断是否有角色
     *
     * @param roleName
     * @return
     */
    protected abstract boolean showTagBody(String roleName);

    protected boolean hasAnyRole(String roleName) {
        if (ssoEnable()) {
            return ssoHasAnyRole(roleName);
        } else {
            return localHasAnyRole(roleName);
        }
    }

    protected boolean localHasAnyRole(String roleName) {
        Subject subject = getSubject();
        if (subject == null) {
            return false;
        }
        if (!Strings.contains(roleName, ROLE_NAMES_DELIMETER)) {
            return subject.hasRole(Strings.trim(roleName));
        }
        boolean hasAnyRole = false;
        for (String role : roleName.split(ROLE_NAMES_DELIMETER)) {
            if (subject.hasRole(role.trim())) {
                hasAnyRole = true;
                break;
            }
        }
        return hasAnyRole;
    }

    protected boolean ssoHasAnyRole(String roleName) {
        String permittedPath = getSsoRoot() + "/role/facade/hasAnyRoles";
        User user = ShiroUtils.getCurrentUser();
        String username = user.getUsername();
        String body = null;
        try {
            body = HttpRequest.post(permittedPath.trim())
                    .form("roleNames", roleName)
                    .form("username", username)
                    .readTimeout(TIME_OUT).connectTimeout(TIME_OUT)
                    .trustAllCerts().trustAllHosts()
                    .body();
        } catch (Exception e) {
            log.error("sso hasAnyRole 用户:{} 角色校验出错。角色：{},请求路径为：{}", username, roleName, e);
            return false;
        }
        if ((Strings.equalsIgnoreCase(body, Boolean.TRUE.toString()) || Strings.equalsIgnoreCase(body, Boolean.FALSE.toString()))) {
            Boolean hasRole = Boolean.valueOf(body);
            log.debug("sso hasAnyRole 用户:{} 角色校验结果:{}, 角色:{} ", username, hasRole, roleName);
            return Boolean.valueOf(body);
        }
        log.debug("sso hasAnyRole 用户:{} 角色校验结果:{}, 角色:{} ", username, false, roleName);
        return false;
    }


}

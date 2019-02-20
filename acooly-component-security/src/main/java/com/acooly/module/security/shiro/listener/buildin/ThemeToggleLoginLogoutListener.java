/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2019-02-17 17:19
 */
package com.acooly.module.security.shiro.listener.buildin;

import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.Strings;
import com.acooly.module.security.shiro.listener.LoginLogoutListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新旧版本的主题保持
 *
 * @author zhangpu
 * @date 2019-02-17 17:19
 */
@Slf4j
@Component
public class ThemeToggleLoginLogoutListener implements LoginLogoutListener {

    @Override
    public void beforeLogout(HttpServletRequest request, HttpServletResponse response, Subject subject) {
        // do nothing
    }

    @Override
    public void afterLogin(AuthenticationToken token, AuthenticationException e, HttpServletRequest request, HttpServletResponse response) {
        // 成功等后，重定向到主页控制器前，保持请求的主题到session
        if (e != null) {
            // 如果登录失败，则跳过
            return;
        }

        String acoolyTheme = Servlets.getParameter(request, "acoolyTheme");
        if (Strings.isNoneBlank(acoolyTheme)) {
            request.getSession().setAttribute("acoolyTheme", acoolyTheme);
        }
    }
}

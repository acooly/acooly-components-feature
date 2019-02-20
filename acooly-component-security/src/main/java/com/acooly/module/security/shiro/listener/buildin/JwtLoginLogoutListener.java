package com.acooly.module.security.shiro.listener.buildin;

import com.acooly.core.utils.security.JWTUtils;
import com.acooly.module.security.shiro.listener.LoginLogoutListener;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shuijing
 */
@Component
public class JwtLoginLogoutListener implements LoginLogoutListener {
    @Override
    public void beforeLogout(
            HttpServletRequest request, HttpServletResponse response, Subject subject) {
        JWTUtils.removeCookie(JWTUtils.TYPE_JWT, JWTUtils.getDomainName());
        SecurityUtils.getSubject().logout();
    }

    @Override
    public void afterLogin(
            AuthenticationToken token,
            AuthenticationException e,
            HttpServletRequest request,
            HttpServletResponse response) {
    }
}

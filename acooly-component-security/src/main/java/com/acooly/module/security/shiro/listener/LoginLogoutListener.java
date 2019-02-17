package com.acooly.module.security.shiro.listener;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录注销监听
 *
 * @author zhangpu
 */
public interface LoginLogoutListener {

    /**
     * 注销前回调
     *
     * @param request
     * @param response
     * @param subject
     */
    void beforeLogout(HttpServletRequest request, HttpServletResponse response, Subject subject);

    /**
     * 登录后回调
     *
     * @param request
     * @param response
     * @param subject
     * @throws AuthenticationException
     */
    void afterLogin(
            AuthenticationToken token,
            AuthenticationException e,
            HttpServletRequest request,
            HttpServletResponse response);
}

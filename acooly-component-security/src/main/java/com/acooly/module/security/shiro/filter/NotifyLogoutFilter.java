package com.acooly.module.security.shiro.filter;

import com.acooly.module.security.shiro.listener.ShireLoginLogoutSubject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 扩展注销，提供注销回调，用于业务系统扩展并与Shire框架解耦
 *
 * @author zhangpu
 */
public class NotifyLogoutFilter extends LogoutFilter {

    /**
     * 监听处理
     */
    private ShireLoginLogoutSubject shireLoginLogoutSubject;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        shireLoginLogoutSubject.beforeLogout(
                (HttpServletRequest) request,
                (HttpServletResponse) response,
                getSubject(request, response));
        return super.preHandle(request, response);
    }

    public void setShireLoginLogoutSubject(ShireLoginLogoutSubject shireLoginLogoutSubject) {
        this.shireLoginLogoutSubject = shireLoginLogoutSubject;
    }
}

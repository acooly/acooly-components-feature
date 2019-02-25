package com.acooly.module.security.shiro.filter;

import com.acooly.module.security.shiro.realm.PathMatchPermission;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UrlResourceAuthorizationFilter extends AuthorizationFilter {

    public static final String UNAUTHORIZATION_ERROR_KEY = "UNAUTHORIZATION_ERROR_KEY";

    @Override
    protected boolean isAccessAllowed(
            ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        // WebDelegatingSubject
        Subject subject = getSubject(request, response);
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String resource = httpRequest.getRequestURI();
        // 对资源的所有操作
        String permission = "do" + PathMatchPermission.PART_DIVIDER_TOKEN + resource;
        boolean isPermitted = subject.isPermitted(permission);
        return isPermitted;
    }

    @Override
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response)
            throws IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (httpRequest.getHeader("x-requested-with") != null
                && httpRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            httpResponse.setHeader("SessionState", "1");
            PrintWriter printWriter = response.getWriter();
            printWriter.flush();
            printWriter.close();
        } else {
            super.saveRequestAndRedirectToLogin(request, response);
        }
    }
}

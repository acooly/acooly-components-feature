/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * kuli@yiji.com 2017-03-21 00:48 创建
 */
package com.acooly.module.portlet.integration;

import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.Strings;
import com.acooly.module.portlet.service.ActionLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问日志 通用收集filter
 *
 * @author acooly
 */
@Slf4j
public class PortalActionLogFilter extends OncePerRequestFilter {

    PathMatcher pathMatcher = new AntPathMatcher();
    @Resource
    private ActionLogService actionLogService;
    private String sessionUserKey;
    private String filterUrlPatterns;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        doActionLog(request);
        filterChain.doFilter(request, response);
    }

    protected void doActionLog(HttpServletRequest request) {
        if (!allow(request)) {
            return;
        }
        try {
            actionLogService.logger(request, getSessionUserName(request));
        } catch (Exception e) {
            log.warn("PortalActionLogFilter记录访问日志失败: {}", e.getMessage());
        }
    }

    protected boolean allow(HttpServletRequest request) {
        String requestPath = Servlets.getRequestPath(request);
        for (String allowPattern : Strings.split(filterUrlPatterns, ",")) {
            if (pathMatcher.match(allowPattern, requestPath)) {
                return true;
            }
        }
        return false;
    }

    protected String getSessionUserName(HttpServletRequest request) {
        Object object = request.getSession().getAttribute(sessionUserKey);
        if (object == null) {
            return null;
        } else {
            return object.toString();
        }
    }

    public void setActionLogService(ActionLogService actionLogService) {
        this.actionLogService = actionLogService;
    }

    public void setSessionUserKey(String sessionUserKey) {
        this.sessionUserKey = sessionUserKey;
    }

    public void setFilterUrlPatterns(String filterUrlPatterns) {
        this.filterUrlPatterns = filterUrlPatterns;
    }
}

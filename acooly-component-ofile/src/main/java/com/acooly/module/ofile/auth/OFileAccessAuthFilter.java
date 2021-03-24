/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-03-01 13:22
 */
package com.acooly.module.ofile.auth;

import com.acooly.core.utils.Servlets;
import com.acooly.integration.web.AcoolyPortalAuthenticateFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 静态资源访问认证
 *
 * @author zhangpu
 * @date 2021-03-01 13:22
 */
@Slf4j
public class OFileAccessAuthFilter extends AcoolyPortalAuthenticateFilter {

    @Resource(name = "ofileUploadAuthenticateSpringProxy")
    private OFileUploadAuthenticate ofileUploadAuthenticate;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // url忽略或已认证
        if (!this.enable || isIgnore(request) || !requiresAuthentication(request, response)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            doAuthentication(request);
        } catch (Exception e) {
            log.debug("访问认证 [失败] url:{}, {}", Servlets.getRequestPath(request), e.getMessage());
            response.setCharacterEncoding("UTF-8");
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "非法访问");
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    protected Object doAuthentication(HttpServletRequest request) {
        ofileUploadAuthenticate.authenticate(request);
        return null;
    }
}

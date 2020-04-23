/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2016-12-23 21:39 创建
 */
package com.acooly.module.security.captche;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author qiubo
 */
@Slf4j
public class CaptchaServlet extends HttpServlet {


    private SecurityCaptchaManager securityCaptchaManager;

    /**
     * 回调执行
     */
    @Override
    protected void doGet(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException, IOException {
        try {
            byte[] imageBytes = securityCaptchaManager.generateImage(httpServletRequest);
            ServletOutputStream out = httpServletResponse.getOutputStream();
            out.write(imageBytes);
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error("生成验证码错误", e);
        }
    }

    public void setSecurityCaptchaManager(SecurityCaptchaManager securityCaptchaManager) {
        this.securityCaptchaManager = securityCaptchaManager;
    }
}

package com.acooly.module.security.health;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author qiubo@yiji.com
 */
public class HealthCheckServlet extends HttpServlet {

    protected void doGet(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException, IOException {
        new Date();
        httpServletResponse.getWriter().write("ok");
    }
}

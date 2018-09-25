/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-03-29 15:40 创建
 */
package com.acooly.core.test.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author qiubo@yiji.com
 */
@Controller
public class FreemarkerController {
    @Autowired(required = false)
    private ServletContext servletContext;

    @RequestMapping("/testFtl")
    public String testFtl(HttpServletRequest request, ModelMap modelMap) {
        servletContext.setAttribute("xxx", "aaa");
        modelMap.put("name", "na");
        modelMap.put("message", "hi");
        request.getSession().setAttribute("valueInSession", "xx");
        request.setAttribute("valueInRequest", "valueInRequest");
        return "test";
    }

    @RequestMapping("/testInclude")
    public String testInclude(ModelMap modelMap) {
        modelMap.put("where", "out");
        return "testInclude";
    }
}

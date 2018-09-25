/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-16 14:35 创建
 */
package com.acooly.core.test.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author qiubo@yiji.com
 */
@Controller
@RequestMapping(value = "/test/jsp")
public class JSPController {
    @RequestMapping("/upload")
    public String testFtl(ModelMap modelMap) {
        return "upload";
    }
}

/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * kuli@yiji.com 2017-05-16 02:26 创建
 */
package com.acooly.module.portlet.test;

import com.acooly.core.common.web.support.JsonResult;
import com.acooly.module.portlet.entity.SiteConfig;
import com.acooly.module.portlet.enums.FeedbackTypeEnum;
import com.acooly.module.portlet.service.FeedbackService;
import com.acooly.module.portlet.service.SiteConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author acooly
 */
//@Controller
//@RequestMapping("/portlet")
public class PortletTestController {

    @Autowired
    private SiteConfigService siteConfigService;
    @Autowired
    private FeedbackService feedbackService;

    @RequestMapping("siteConfig")
    @ResponseBody
    public SiteConfig getSiteConfig(HttpServletRequest request) {
        String name = request.getParameter("name");
        return siteConfigService.findUnique(name);
    }

    @RequestMapping("feedback")
    @ResponseBody
    public JsonResult feedback(HttpServletRequest request) {
        JsonResult result = new JsonResult();
        try {
            String contactInfo = "pu.zhang@foxmail.com";
            String userName = "zhangpu";
            String address = "重庆 渝北";
            String telephone = "13896177630";
            FeedbackTypeEnum type = FeedbackTypeEnum.complaint;
            String content = "这就是一个客户反馈的测试内容";
            String title = "测试反馈建议";

            feedbackService.submit(type, content);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}

/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by zhangpu
 * date:2022-01-11
 */
package com.acooly.module.mail.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.acooly.core.common.exception.AppConfigException;
import com.acooly.core.common.web.MappingMethod;
import com.acooly.core.common.web.support.JsonEntityResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.module.mail.entity.EmailRecord;
import com.acooly.module.mail.service.EmailRecordService;

import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 邮件发送记录 管理控制器
 *
 * @author zhangpu
 * @date 2022-01-11 09:34:49
 */
@Controller
@RequestMapping(value = "/manage/module/mail/emailRecord")
public class EmailRecordManagerController extends AbstractJsonEntityController<EmailRecord, EmailRecordService> {


    {
        allowMapping = "*";
    }

    @SuppressWarnings("unused")
    @Autowired
    private EmailRecordService emailRecordService;


    @RequestMapping(value = "showContent")
    public String showContent(HttpServletRequest request, HttpServletResponse response, Model model) {
        allow(request, response, MappingMethod.show);
        try {
            model.addAllAttributes(referenceData(request));
            EmailRecord entity = loadEntity(request);
            if (entity == null) {
                throw new AppConfigException("LoadEntity failure.");
            }
            onShow(request, response, model, entity);
            model.addAttribute(getEntityName(), entity);
        } catch (Exception e) {
            handleException("查看", e, request);
        }
        return "/manage/module/mail/emailRecordShowContent";
    }
}

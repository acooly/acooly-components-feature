/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-04-27
 */
package com.acooly.module.mail.web;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.module.mail.entity.EmailTemplate;
import com.acooly.module.mail.service.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author shuijing
 */
@Controller
@RequestMapping(value = "/manage/module/mail/emailTemplate")
public class EmailTemplateManagerController
        extends AbstractJQueryEntityController<EmailTemplate, EmailTemplateService> {

    @SuppressWarnings("unused")
    @Autowired
    private EmailTemplateService emailTemplateService;

    {
        allowMapping = "*";
    }
}

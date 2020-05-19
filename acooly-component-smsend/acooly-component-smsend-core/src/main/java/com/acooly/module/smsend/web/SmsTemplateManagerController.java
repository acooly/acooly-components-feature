/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by acooly
 * date:2020-05-19
 */
package com.acooly.module.smsend.web;

import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.core.utils.Strings;
import com.acooly.module.smsend.entity.SmsTemplate;
import com.acooly.module.smsend.manage.SmsAppService;
import com.acooly.module.smsend.manage.SmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 短信模板 管理控制器
 *
 * @author acooly
 * @date 2020-05-19 15:03:05
 */
@Controller
@RequestMapping(value = "/manage/smsend/smsTemplate")
public class SmsTemplateManagerController extends AbstractJsonEntityController<SmsTemplate, SmsTemplateService> {


    {
        allowMapping = "*";
    }

    @Autowired
    private SmsTemplateService smsTemplateService;

    @Autowired
    private SmsAppService smsAppService;


	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		model.put("allApps",smsAppService.getAvailables());
	}
}

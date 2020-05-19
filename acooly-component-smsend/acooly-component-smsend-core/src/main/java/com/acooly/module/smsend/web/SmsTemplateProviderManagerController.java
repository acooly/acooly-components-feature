/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by acooly
 * date:2020-05-19
 */
package com.acooly.module.smsend.web;

import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.module.smsend.common.enums.SmsProvider;
import com.acooly.module.smsend.entity.SmsTemplateProvider;
import com.acooly.module.smsend.manage.SmsTemplateProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 模板渠道 管理控制器
 *
 * @author acooly
 * @date 2020-05-19 15:03:06
 */
@Controller
@RequestMapping(value = "/manage/smsend/smsTemplateProvider")
public class SmsTemplateProviderManagerController extends AbstractJsonEntityController<SmsTemplateProvider, SmsTemplateProviderService> {


    {
        allowMapping = "*";
    }

    @SuppressWarnings("unused")
    @Autowired
    private SmsTemplateProviderService smsTemplateProviderService;


    @Override
    protected SmsTemplateProvider onSave(HttpServletRequest request, HttpServletResponse response, Model model, SmsTemplateProvider entity, boolean isCreate) throws Exception {
        return super.onSave(request, response, model, entity, isCreate);
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allProviders", SmsProvider.mapping());
    }
}

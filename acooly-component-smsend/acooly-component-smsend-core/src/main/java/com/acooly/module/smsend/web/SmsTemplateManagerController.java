/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by acooly
 * date:2020-05-19
 */
package com.acooly.module.smsend.web;

import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Collections3;
import com.acooly.module.smsend.entity.SmsTemplate;
import com.acooly.module.smsend.manage.SmsAppService;
import com.acooly.module.smsend.manage.SmsTemplateProviderService;
import com.acooly.module.smsend.manage.SmsTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Map;

/**
 * 短信模板 管理控制器
 *
 * @author acooly
 * @date 2020-05-19 15:03:05
 */
@Slf4j
@Controller
@RequestMapping(value = "/manage/smsend/smsTemplate")
public class SmsTemplateManagerController extends AbstractJsonEntityController<SmsTemplate, SmsTemplateService> {


    {
        allowMapping = "*";
    }

    @Autowired
    private SmsTemplateService smsTemplateService;
    @Autowired
    private SmsTemplateProviderService smsTemplateProviderService;
    @Autowired
    private SmsAppService smsAppService;

    @Override
    public JsonResult deleteJson(HttpServletRequest request, HttpServletResponse response) {
        return super.deleteJson(request, response);
    }

    @Override
    protected void onRemove(HttpServletRequest request, HttpServletResponse response, Model model, Serializable... ids) throws Exception {
        SmsTemplate smsTemplate = null;
        for (Serializable id : ids) {
            smsTemplate = smsTemplateService.get(id);
            if (Collections3.isNotEmpty(smsTemplateProviderService.findByTemplateCode(smsTemplate.getTemplateCode()))) {
                log.warn("模板（{}）下存在已定义好的渠道模板编码，不能直接删除。", smsTemplate.getTemplateCode());
                throw new RuntimeException("模板(" + smsTemplate.getTemplateCode() + ")下存在已定义的渠道模板，不能直接删除");
            }
        }
        super.onRemove(request, response, model, ids);
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allApps", smsAppService.getAvailables());
    }
}

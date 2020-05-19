/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by acooly
 * date:2020-05-19
 */
package com.acooly.module.smsend.web;

import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.core.utils.Ids;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.enums.SimpleStatus;
import com.acooly.module.smsend.entity.SmsApp;
import com.acooly.module.smsend.manage.SmsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 短信发送应用 管理控制器
 *
 * @author acooly
 * @date 2020-05-19 15:03:06
 */
@Controller
@RequestMapping(value = "/manage/smsend/smsApp")
public class SmsAppManagerController extends AbstractJsonEntityController<SmsApp, SmsAppService> {

    {
        allowMapping = "*";
    }

    @Autowired
    private SmsAppService smsAppService;

    @Override
    protected SmsApp onSave(HttpServletRequest request, HttpServletResponse response, Model model, SmsApp entity, boolean isCreate) throws Exception {
        if (isCreate && Strings.isBlank(entity.getAppId())) {
            entity.setAppId(Ids.getDid());
        }
        return entity;
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allStatuss", SimpleStatus.mapping());
    }
}

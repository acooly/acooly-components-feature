/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by acooly
 * date:2020-05-08
 */
package com.acooly.module.smsend.web;

import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.module.smsend.entity.SmsSendLog;
import com.acooly.module.smsend.common.enums.SmsProvider;
import com.acooly.module.smsend.common.enums.SmsSendStatus;
import com.acooly.module.smsend.common.enums.SmsSendType;
import com.acooly.module.smsend.manage.SmsSendLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 短信发送记录 管理控制器
 *
 * @author acooly
 * @date 2020-05-08 14:32:47
 */
@Controller
@RequestMapping(value = "/manage/smsend/smsSendLog")
public class SmsSendLogManagerController extends AbstractJsonEntityController<SmsSendLog, SmsSendLogService> {

    {
        allowMapping = "*";
    }

    @SuppressWarnings("unused")
    @Autowired
    private SmsSendLogService smsSendLogService;


    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allSendTypes", SmsSendType.mapping());
        model.put("allProviders", SmsProvider.mapping());
        model.put("allStatuss", SmsSendStatus.mapping());
    }

}

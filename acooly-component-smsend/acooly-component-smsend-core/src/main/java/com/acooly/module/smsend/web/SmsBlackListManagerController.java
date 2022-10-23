/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by shuijing
 * date:2017-08-01
 */
package com.acooly.module.smsend.web;

import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.core.utils.enums.SimpleStatus;
import com.acooly.module.smsend.entity.SmsBlackList;
import com.acooly.module.smsend.manage.BlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 短信黑名单 管理控制器
 *
 * @author shuijing
 * Date: 2017-08-01 17:28:24
 */
@Controller
@RequestMapping(value = "/manage/smsend/smsBlackList")
public class SmsBlackListManagerController extends AbstractJsonEntityController<SmsBlackList, BlackListService> {

    @SuppressWarnings("unused")
    @Autowired
    private BlackListService blackListService;

    {
        allowMapping = "*";
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allStatuss", SimpleStatus.mapping());
    }

}

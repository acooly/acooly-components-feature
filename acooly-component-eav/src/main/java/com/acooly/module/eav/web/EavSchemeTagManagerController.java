/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by zhangpu
 * date:2019-03-05
 */
package com.acooly.module.eav.web;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.module.eav.entity.EavSchemeTag;
import com.acooly.module.eav.service.EavSchemeTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 方案标签 管理控制器
 *
 * @author zhangpu
 * Date: 2019-03-05 18:52:41
 */
@Controller
@RequestMapping(value = "/manage/module/eav/eavSchemeTag")
public class EavSchemeTagManagerController extends AbstractJQueryEntityController<EavSchemeTag, EavSchemeTagService> {


    {
        allowMapping = "*";
    }

    @SuppressWarnings("unused")
    @Autowired
    private EavSchemeTagService eavSchemeTagService;




}

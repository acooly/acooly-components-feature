/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-03-21
 */
package com.acooly.module.portlet.web;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.module.portlet.entity.ActionMapping;
import com.acooly.module.portlet.service.ActionMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 访问映射 管理控制器
 *
 * @author acooly Date: 2017-03-21 00:34:47
 */
@Controller
@RequestMapping(value = "/manage/module/portlet/actionMapping")
public class ActionMappingManagerController
        extends AbstractJQueryEntityController<ActionMapping, ActionMappingService> {

    @SuppressWarnings("unused")
    @Autowired
    private ActionMappingService actionMappingService;

    {
        allowMapping = "*";
    }
}

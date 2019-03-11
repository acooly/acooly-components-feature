/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiubo
 * date:2018-06-27
 */
package com.acooly.module.eav.web;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.module.eav.entity.EavScheme;
import com.acooly.module.eav.enums.SchemePermissionEnum;
import com.acooly.module.eav.service.EavSchemeEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * eav_schema 管理控制器
 *
 * @author qiubo
 * Date: 2018-06-27 14:04:57
 */
@Controller
@RequestMapping(value = "/manage/module/eav/eavScheme")
public class EavSchemeManagerController extends AbstractJQueryEntityController<EavScheme, EavSchemeEntityService> {


    {
        allowMapping = "*";
    }

    @SuppressWarnings("unused")
    @Autowired
    private EavSchemeEntityService eavSchemeEntityService;

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allPermissions", SchemePermissionEnum.mapping());
    }

    @Override
    protected EavScheme onSave(HttpServletRequest request, HttpServletResponse response, Model model, EavScheme entity, boolean isCreate) throws Exception {
        // 处理checkbox
        int permission = 0;
        String[] permissions = request.getParameterValues("permission");
        if (permissions != null && permissions.length > 0) {
            for (String perm : permissions) {
                permission = permission + Integer.parseInt(perm);
            }
        }
        entity.setPermission(permission);
        return entity;
    }
}

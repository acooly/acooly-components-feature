/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiubo
 * date:2018-06-27
 */
package com.acooly.module.eav.web;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.enums.WhetherStatus;
import com.acooly.module.eav.entity.EavAttribute;
import com.acooly.module.eav.entity.EavScheme;
import com.acooly.module.eav.enums.AttributeFormatEnum;
import com.acooly.module.eav.enums.AttributeShowTypeEnum;
import com.acooly.module.eav.enums.AttributeTypeEnum;
import com.acooly.module.eav.service.EavAttributeEntityService;
import com.acooly.module.eav.service.EavSchemeEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * eav_attribute 管理控制器
 *
 * @author qiubo
 * Date: 2018-06-27 14:04:57
 */
@Controller
@RequestMapping(value = "/manage/module/eav/eavAttribute")
public class EavAttributeManagerController extends AbstractJQueryEntityController<EavAttribute, EavAttributeEntityService> {

    {
        allowMapping = "*";
    }


    @SuppressWarnings("unused")
    @Autowired
    private EavAttributeEntityService eavAttributeEntityService;

    @Autowired
    private EavSchemeEntityService eavSchemeEntityService;

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allAttributeTypes", AttributeTypeEnum.mapping());
        model.put("allAttributeShowTypes", AttributeShowTypeEnum.mapping());
        model.put("allAttributeFormats", AttributeFormatEnum.mapping());
        model.put("allWhetherStatus", WhetherStatus.mapping());
        model.put("allSchemes", eavSchemeEntityService.getAll());
    }


    @Override
    protected void onCreate(HttpServletRequest request, HttpServletResponse response, Model model) {
        String schemeId = Servlets.getParameter("schemeId");
        if (Strings.isNoneBlank(schemeId)) {
            EavScheme eavScheme = eavSchemeEntityService.get(Long.valueOf(schemeId));
            model.addAttribute("eavScheme", eavScheme);
        }

        super.onCreate(request, response, model);
    }

    @Override
    protected void onEdit(HttpServletRequest request, HttpServletResponse response, Model model, EavAttribute entity) {
        EavScheme eavScheme = eavSchemeEntityService.get(entity.getSchemeId());
        model.addAttribute("eavScheme", eavScheme);
        super.onEdit(request, response, model, entity);
    }

    @Override
    protected EavAttribute doSave(HttpServletRequest request, HttpServletResponse response, Model model, boolean isCreate) throws Exception {
        try {
            return super.doSave(request, response, model, isCreate);
        } catch (DuplicateKeyException e) {
            throw new BusinessException("同一个方案中属性名称和展示名称都不能重复", false);
        }
    }

    @Override
    protected EavAttribute onSave(HttpServletRequest request, HttpServletResponse response, Model model, EavAttribute entity, boolean isCreate) throws Exception {
        // 简单参数验证
        entity.createCheck();
        // 处理checkbox
        int showTypeValue = 0;
        String[] showTypes = request.getParameterValues("showType");
        if (showTypes != null && showTypes.length > 0) {
            for (String showType : showTypes) {
                showTypeValue = showTypeValue + Integer.parseInt(showType);
            }
        }
        if (showTypeValue == 0) {
            showTypeValue = AttributeShowTypeEnum.getAllValue();
        }
        entity.setShowType(showTypeValue);
        return entity;
    }

    @Override
    protected Map<String, Boolean> getSortMap(HttpServletRequest request) {
        Map<String, Boolean> sort = super.getSortMap(request);
        if (sort.size() == 0) {
            sort.put("id", true);
        }
        return sort;
    }
}

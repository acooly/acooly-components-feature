/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiubo
 * date:2018-06-27
 */
package com.acooly.module.eav.web;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.module.eav.entity.EavAttribute;
import com.acooly.module.eav.enums.AttributeTypeEnum;
import com.acooly.module.eav.service.EavAttributeEntityService;
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


    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allAttributeTypes", AttributeTypeEnum.mapping());
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
        if (isCreate) {
            entity.createCheck();
        }
        return entity;
    }
}

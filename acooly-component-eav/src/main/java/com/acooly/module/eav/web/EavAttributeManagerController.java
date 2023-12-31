/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiubo
 * date:2018-06-27
 */
package com.acooly.module.eav.web;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.enums.WhetherStatus;
import com.acooly.module.eav.entity.EavAttribute;
import com.acooly.module.eav.entity.EavOption;
import com.acooly.module.eav.entity.EavScheme;
import com.acooly.module.eav.entity.EavSchemeTag;
import com.acooly.module.eav.enums.AttributeFormatEnum;
import com.acooly.module.eav.enums.AttributePermissionEnum;
import com.acooly.module.eav.enums.AttributeTypeEnum;
import com.acooly.module.eav.service.EavAttributeEntityService;
import com.acooly.module.eav.service.EavOptionService;
import com.acooly.module.eav.service.EavSchemeEntityService;
import com.acooly.module.eav.service.EavSchemeTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
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

    @Autowired
    private EavOptionService eavOptionService;

    @Autowired
    private EavSchemeTagService eavSchemeTagService;

    enum MoveType {
        up, top;
    }

    @RequestMapping(value = "moveTop")
    @ResponseBody
    public JsonResult moveTop(HttpServletRequest request, HttpServletResponse response) {

        JsonResult result = new JsonResult();
        try {
            doMove(request, MoveType.top);
            result.setMessage("置顶成功");
        } catch (Exception e) {
            handleException(result, "置顶", e);
        }
        return result;
    }

    @RequestMapping(value = "moveUp")
    @ResponseBody
    public JsonResult moveUp(HttpServletRequest request, HttpServletResponse response) {

        JsonResult result = new JsonResult();
        try {
            doMove(request, MoveType.up);
            result.setMessage("上移成功");
        } catch (Exception e) {
            handleException(result, "上移", e);
        }
        return result;
    }

    protected void doMove(HttpServletRequest request, MoveType moveType) {
        Long id = Servlets.getLongParameter(request, getEntityIdName());
        if (moveType == MoveType.up) {
            getEntityService().moveUp(id);
        } else {
            getEntityService().moveTop(id);
        }

    }


    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allAttributeTypes", AttributeTypeEnum.mapping());
        model.put("allAttributeShowTypes", AttributePermissionEnum.mapping());
        model.put("allAttributeFormats", AttributeFormatEnum.mapping());
        model.put("allWhetherStatus", WhetherStatus.mapping());
        model.put("allSchemes", eavSchemeEntityService.getAll());
        model.put("options", loadOptions());
    }


    @Override
    protected void onCreate(HttpServletRequest request, HttpServletResponse response, Model model) {
        Long schemeId = Servlets.getLongParameter("schemeId");
        if (schemeId != null) {
            EavScheme eavScheme = eavSchemeEntityService.get(schemeId);
            model.addAttribute("eavScheme", eavScheme);
            model.addAttribute("tags", loadSchemeTags(schemeId));
        }
        super.onCreate(request, response, model);
    }

    @Override
    protected void onEdit(HttpServletRequest request, HttpServletResponse response, Model model, EavAttribute entity) {
        EavScheme eavScheme = eavSchemeEntityService.get(entity.getSchemeId());
        model.addAttribute("eavScheme", eavScheme);
        model.addAttribute("tags", loadSchemeTags(entity.getSchemeId()));
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
        entity.setShowType(showTypeValue);

        // 处理tag
        String tag = Servlets.getParameter("tag");
        if (Strings.isNotBlank(tag)) {
            eavSchemeTagService.save(entity.getSchemeId(), tag);
        }

        // 根据attributeType处理
        AttributeTypeEnum attributeType = entity.getAttributeType();
        if (attributeType == AttributeTypeEnum.NUMBER_DECIMAL
                || attributeType == AttributeTypeEnum.NUMBER_INTEGER
                || attributeType == AttributeTypeEnum.NUMBER_MONEY) {
            entity.setMinLength(0);
            entity.setMaxLength(0);
            entity.setEnumValue("");
        } else if (attributeType == AttributeTypeEnum.STRING
                || attributeType == AttributeTypeEnum.DATE) {
            entity.setMaximum(0L);
            entity.setMinimum(0L);
            entity.setEnumValue("");
        } else if (attributeType == AttributeTypeEnum.ENUM) {
            entity.setMaximum(0L);
            entity.setMinimum(0L);
            entity.setMinLength(0);
            entity.setMaxLength(0);
        }

        return entity;
    }


    @Override
    protected Map<String, Object> getSearchParams(HttpServletRequest request) {
        Map<String, Object> map = super.getSearchParams(request);
        // 如果没有传入schemeId，则设置一个不存在的schemeId
        if (map.get("EQ_schemeId") == null) {
            map.put("EQ_schemeId", 0);
        }
        return map;
    }

    @Override
    protected Map<String, Boolean> getSortMap(HttpServletRequest request) {
        Map<String, Boolean> sort = super.getSortMap(request);
        sort.clear();
        sort.put("sortTime", true);
        return sort;
    }

    protected Map<String, String> loadOptions() {
        List<EavOption> eavOptions = eavOptionService.listTop();
        return Collections3.extractToMap(eavOptions, "code", "name");
    }

    protected List<EavSchemeTag> loadSchemeTags(Long schemeId) {
        return eavSchemeTagService.list(schemeId);
    }


}

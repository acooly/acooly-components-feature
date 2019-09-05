/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by zhangpu
 * date:2019-03-05
 */
package com.acooly.module.eav.web;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.Strings;
import com.acooly.module.eav.entity.EavOption;
import com.acooly.module.eav.service.EavOptionService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * 属性选项 管理控制器
 *
 * @author zhangpu
 * Date: 2019-03-05 18:52:41
 */
@Slf4j
@Controller
@RequestMapping(value = "/manage/module/eav/eavOption")
public class EavOptionManagerController extends AbstractJQueryEntityController<EavOption, EavOptionService> {


    {
        allowMapping = "*";
    }

    @SuppressWarnings("unused")
    @Autowired
    private EavOptionService eavOptionService;

    @Override
    protected EavOption doSave(HttpServletRequest request, HttpServletResponse response, Model model, boolean isCreate) throws Exception {
        try {
            return super.doSave(request, response, model, isCreate);
        } catch (DuplicateKeyException e) {
            throw new BusinessException("选项编码不能重复", false);
        }
    }

    /**
     * 查询顶层
     *
     * @param request
     * @return
     */
    @Override
    protected Map<String, Object> getSearchParams(HttpServletRequest request) {
        Map<String, Object> searchParams = super.getSearchParams(request);
        searchParams.put("NULL_parentId", null);
        return searchParams;
    }

    @RequestMapping("editBatch")
    public String editBatch(HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            Long parentId = Servlets.getLongParameter("parentId");
            EavOption parent = getEntityService().get(parentId);
            List<EavOption> eavOptions = eavOptionService.listByParentId(parentId);
            model.addAttribute("action", "edit");
            model.addAttribute("eavOptions", eavOptions);
            model.addAttribute("parent", parent);
            model.addAttribute("parentId", parentId);
            model.addAllAttributes(this.referenceData(request));
        } catch (Exception var5) {
            log.warn(this.getExceptionMessage("editBatch", var5), var5);
            this.handleException("批量编辑", var5, request);
        }
        return "/manage/module/eav/eavOptionEditBatch";
    }

    @RequestMapping("saveBatch")
    @ResponseBody
    public JsonEntityResult<EavOption> saveBatch(HttpServletRequest request, HttpServletResponse response) {
        JsonEntityResult<EavOption> result = new JsonEntityResult();
        try {
            Long parentId = Servlets.getLongParameter("parentId");

            Map<String, EavOption> datas = Maps.newLinkedHashMap();
            EavOption eavOption = null;
            Enumeration paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = (String) paramNames.nextElement();
                String[] paramNameParts = Strings.split(paramName, "_");
                if (paramNameParts == null || paramNameParts.length != 2) {
                    continue;
                }
                String id = paramNameParts[1];

                if (datas.get(id) == null) {
                    eavOption = new EavOption();
                    if (Strings.isNumeric(id)) {
                        eavOption.setId(Long.valueOf(id));
                    }
                    eavOption.setParentId(parentId);
                    datas.put(id, eavOption);
                }
                String value = request.getParameter(paramName);
                if (Strings.startsWith(paramName, "name")) {
                    datas.get(id).setName(value);
                } else if (Strings.startsWith(paramName, "code")) {
                    datas.get(id).setCode(value);
                }
            }

            getEntityService().saves(Lists.newArrayList(datas.values()));
            result.setEntity(getEntityService().get(parentId));
            result.setMessage("保存成功");
        } catch (Exception var5) {
            this.handleException(result, "批量保存", var5);
        }

        return result;
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

    enum MoveType {
        up, top;
    }


}

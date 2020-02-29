/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by zhangpu
 * date:2019-11-03
 */
package com.acooly.module.treetype.web;

import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.core.common.web.support.JsonListResult;
import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.Strings;
import com.acooly.module.treetype.entity.TreeType;
import com.acooly.module.treetype.service.TreeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 树形分类 管理控制器
 *
 * @author zhangpu
 * Date: 2019-11-03 08:46:48
 */
@Controller
@RequestMapping(value = "/manage/module/treeType/treeType")
public class TreeTypeManagerController extends AbstractJsonEntityController<TreeType, TreeTypeService> {


    {
        allowMapping = "*";
    }

    @Autowired
    private TreeTypeService treeTypeService;


    @RequestMapping(value = "queryTree")
    @ResponseBody
    public JsonListResult<TreeType> queryTree(HttpServletRequest request, HttpServletResponse response) {
        JsonListResult<TreeType> result = new JsonListResult<>();
        try {
            result.appendData(referenceData(request));
            Long parentId = Servlets.getLongParameter(request, "id");
            String theme = Servlets.getParameter(request, "theme");
            List<TreeType> entities = treeTypeService.level(theme, parentId);
            result.setTotal((long) entities.size());
            result.setRows(entities);
        } catch (Exception e) {
            handleException(result, "列表查询", e);
        }
        return result;
    }

    @RequestMapping(value = "loadTree")
    @ResponseBody
    public JsonListResult<TreeType> loadTree(HttpServletRequest request, HttpServletResponse response) {
        JsonListResult<TreeType> result = new JsonListResult<>();
        try {
            String theme = Servlets.getParameter(request, "theme");
            Long parentId = Servlets.getLongParameter(request, "parentId");
            result.appendData(referenceData(request));
            List<TreeType> entities = Strings.isBlank(theme) ? treeTypeService.tree(parentId) :
                    treeTypeService.tree(theme, parentId);
            result.setTotal((long) entities.size());
            result.setRows(entities);
        } catch (Exception e) {
            handleException(result, "加载数据树", e);
        }
        return result;
    }

    @Override
    protected void onCreate(HttpServletRequest request, HttpServletResponse response, Model model) {
        Long parentId = Servlets.getLongParameter(request, "parentId");
        String theme = Servlets.getParameter(request, "theme");
        model.addAttribute("theme", theme);
        model.addAttribute("parent", getParent(parentId));
        super.onCreate(request, response, model);
    }

    @Override
    protected void onEdit(HttpServletRequest request, HttpServletResponse response, Model model, TreeType entity) {
        if (!TreeType.TOP_PARENT_ID.equals(entity.getParentId())) {
            model.addAttribute("parent", getParent(entity.getParentId()));
        }
        model.addAttribute("theme", entity.getTheme());
        super.onEdit(request, response, model, entity);
    }

    /**
     * up操作排序
     */
    @Override
    protected void customUpQuery(HttpServletRequest request, Long id, Map<String, Object> map, Map<String, Boolean> sortMap) {
        TreeType workTaskType = loadEntity(request);
        map.put("GT_sortTime", workTaskType.getSortTime());
        map.put("EQ_parentId", workTaskType.getParentId());
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        super.referenceData(request, model);
        String theme = Servlets.getParameter(request, "theme");
        if (Strings.isBlank(theme)) {
            theme = TreeType.DEFAULT_THEME;
        }
        request.setAttribute("theme", theme);
    }

    private TreeType getParent(Long parentId) {
        if (parentId != null) {
            return treeTypeService.get(parentId);
        } else {
            return null;
        }
    }


}

/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiubo
 * date:2018-06-27
 */
package com.acooly.module.eav.web;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.type.DBMap;
import com.acooly.core.common.view.ViewResult;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.common.web.support.JsonListResult;
import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Servlets;
import com.acooly.module.eav.entity.EavAttribute;
import com.acooly.module.eav.entity.EavEntity;
import com.acooly.module.eav.entity.EavScheme;
import com.acooly.module.eav.enums.AttributeTypeEnum;
import com.acooly.module.eav.service.EavAttributeEntityService;
import com.acooly.module.eav.service.EavEntityEntityService;
import com.acooly.module.eav.service.EavSchemeEntityService;
import com.acooly.module.eav.service.impl.EavEntityService;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * eav_entity 管理控制器
 *
 * @author zhangpu
 * Date: 2018-06-27 14:04:57
 */
@Controller
@RequestMapping(value = "/manage/module/eav/eavEntity")
public class EavEntityManagerController extends AbstractJQueryEntityController<EavEntity, EavEntityEntityService> {


    {
        allowMapping = "*";
    }

    @SuppressWarnings("unused")
    @Autowired
    private EavEntityEntityService eavEntityEntityService;
    @Autowired
    private EavEntityService eavEntityService;
    @Autowired
    private EavAttributeEntityService eavAttributeEntityService;
    @Autowired
    private EavSchemeEntityService eavSchemeEntityService;

    /**
     * 首页
     */
    @Override
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        Long schemeId = Servlets.getLongParameter(request, "schemeId");

        EavScheme eavScheme = null;
        if (schemeId != null) {
            eavScheme = eavSchemeEntityService.get(schemeId);
        } else {
            String schemeName = Servlets.getParameter(request, "schemeName");
            eavScheme = eavSchemeEntityService.getScheme(schemeName);
        }
        if (eavScheme != null) {
            model.addAttribute("schemeId", eavScheme.getId());
            model.addAttribute("eavScheme", eavScheme);
        }
        return super.index(request, response, model);
    }

    /**
     * 分页查询
     */
    @Override
    public JsonListResult listJson(HttpServletRequest request, HttpServletResponse response) {

        Long schemeId = Servlets.getLongParameter(request, "search_EQ_schemeId");
        if (schemeId == null) {
            return new JsonListResult();
        }

        Map searchParam = getSearchParams(request);
        searchParam.remove("search_EQ_schemeId");
        PageInfo pageInfo = getPageInfo(request);
        Iterator it = searchParam.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry next = (Map.Entry) it.next();
            if (next.getValue() == null || Strings.isNullOrEmpty(next.getValue().toString())) {
                it.remove();
            }
        }
        pageInfo = eavEntityService.query(schemeId, pageInfo, searchParam, getSortMap(request));
        JsonListResult eavEntityJsonListResult = new JsonListResult();
        eavEntityJsonListResult.setTotal(pageInfo.getTotalCount());
        eavEntityJsonListResult.setRows(pageInfo.getPageResults());
        List<Map<String, Object>> list = Lists.newArrayList();
        eavEntityJsonListResult.getRows().forEach(value -> {
            list.add(convertEavEntity((EavEntity) value));
        });
        eavEntityJsonListResult.setRows(list);

        // referenceData
        List<EavAttribute> eavAttributes = eavAttributeEntityService.loadEavAttribute(schemeId);
        Map mapping = Maps.newHashMap();
        for (EavAttribute attribute : eavAttributes) {
            if (attribute.getAttributeType() == AttributeTypeEnum.ENUM) {
                mapping.put("all" + StringUtils.capitalize(attribute.getName()) + "s"
                        , Collections3.extractToMap(attribute.getOptions(), "code", "name"));
            }
        }
        eavEntityJsonListResult.appendData(mapping);

        return eavEntityJsonListResult;
    }

    @Override
    public String show(HttpServletRequest request, HttpServletResponse response, Model model) {
        return super.show(request, response, model);
    }

    @Override
    protected void onShow(HttpServletRequest request, HttpServletResponse response, Model model, EavEntity entity) throws Exception {
        model.addAttribute("eavEntityInfo", eavEntityService.loadEavEntity(entity.getId()));
    }


    @Override
    protected void onCreate(HttpServletRequest request, HttpServletResponse response, Model model) {
        super.onCreate(request, response, model);
        model.addAttribute("single", Servlets.getParameter("single"));
        model.addAttribute("schemeId", Servlets.getParameter("schemeId"));
    }

    @Override
    public JsonEntityResult<EavEntity> saveJson(HttpServletRequest request, HttpServletResponse response) {
        JsonEntityResult result = new JsonEntityResult<>();
        try {
            Long schemaId = Servlets.getLongParameter("schemeId");
            Map<String, String> parameters = Servlets.getParameters(request);
            parameters.remove("schemeId");
            parameters.remove("eavEntityId");
            EavEntity eavEntity = eavEntityService.save(schemaId, parameters);
            result.setEntity(convertEavEntity(eavEntity));
            result.setMessage("新增成功");
        } catch (Exception e) {
            handleException(result, "新增", e);
        }
        return result;
    }


    @Override
    protected void onEdit(HttpServletRequest request, HttpServletResponse response, Model model, EavEntity entity) {
        model.addAttribute("entityJson", entity.getValue().toJson());
        model.addAttribute("entityId", entity.getId());
        model.addAttribute("schemeId", entity.getSchemeId());
        model.addAttribute("single", Servlets.getParameter("single"));
    }

    @Override
    public JsonEntityResult<EavEntity> updateJson(HttpServletRequest request, HttpServletResponse response) {
        JsonEntityResult result = new JsonEntityResult<>();
        try {
            Long schemaId = Servlets.getLongParameter("schemeId");
            Map<String, String> parameters = Servlets.getParameters(request);
            EavEntity eavEntity = eavEntityService.save(schemaId, parameters);
            result.setEntity(convertEavEntity(eavEntity));
            result.setMessage("更新成功");
        } catch (Exception e) {
            handleException(result, "更新", e);
        }
        return result;
    }


    /**
     * 加载所有方案基本信息
     */
    @RequestMapping("getEavSchemas")
    @ResponseBody
    public ViewResult getEavSchemas() {
        return ViewResult.success(eavSchemeEntityService.getAll());
    }

    /**
     * 查询单个方案及子数据
     */
    @RequestMapping("getEavScheme")
    @ResponseBody
    public JsonEntityResult getEavSchema(Long id, HttpServletRequest request) {
        JsonEntityResult result = new JsonEntityResult();
        try {
            result.setEntity(eavEntityService.findEavSchemaDto(id));
            result.appendData(referenceData(request));
        } catch (Exception e) {
            handleException(result, "加载方案", e);
        }
        return result;
    }


    @Override
    public void initBinder(WebDataBinder binder) {
        super.initBinder(binder);
        binder.registerCustomEditor(DBMap.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(DBMap.fromJson(text));
            }

            @Override
            public String getAsText() {
                return ((DBMap) getValue()).toJson();
            }
        });

    }


    protected Map convertEavEntity(EavEntity eavEntity) {
        Map<String, Object> map = Maps.newHashMap();
        map.putAll(eavEntity.getValue());
        map.put("id", eavEntity.getId());
        map.put("schemeId", eavEntity.getSchemeId());
        map.put("createTime", eavEntity.getCreateTime());
        map.put("updateTime", eavEntity.getUpdateTime());
        return map;
    }
}

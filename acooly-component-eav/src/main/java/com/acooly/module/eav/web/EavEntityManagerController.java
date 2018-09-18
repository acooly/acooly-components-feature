/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiubo
 * date:2018-06-27
 */
package com.acooly.module.eav.web;

import com.acooly.core.common.type.DBMap;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.common.web.support.JsonListResult;
import com.acooly.core.utils.Servlets;
import com.acooly.module.eav.dto.EavPageInfo;
import com.acooly.module.eav.entity.EavEntity;
import com.acooly.module.eav.service.EavEntityEntityService;
import com.acooly.module.eav.service.impl.EavEntityService;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * eav_entity 管理控制器
 *
 * @author qiubo
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

    @Override
    public JsonListResult listJson(HttpServletRequest request, HttpServletResponse response) {
        Map searchParam = Servlets.getParameters(request);
        if (searchParam.get("search_EQ_schemaId") == null) {
            return new JsonListResult();
        }

        Long sechemaId = Long.valueOf((String) searchParam.get("search_EQ_schemaId"));
        searchParam.remove("search_EQ_schemaId");

        EavPageInfo pageinfo = new EavPageInfo();
        pageinfo.setCurrentPage(Integer.parseInt((String) searchParam.get("page")));
        searchParam.remove("page");

        pageinfo.setCountOfCurrentPage(Integer.parseInt((String) searchParam.get("rows")));
        searchParam.remove("rows");

        pageinfo.setEavSort((String) searchParam.get("sort"));
        searchParam.remove("sort");

        pageinfo.setEavOrder((String) searchParam.get("order"));
        searchParam.remove("order");
        Iterator it = searchParam.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry next = (Map.Entry) it.next();
            if (next.getValue() == null || Strings.isNullOrEmpty(next.getValue().toString())) {
                it.remove();
            }
        }
        eavEntityService.queryByPage(sechemaId, searchParam, pageinfo);

        JsonListResult eavEntityJsonListResult = new JsonListResult();
        eavEntityJsonListResult.setTotal(pageinfo.getTotalCount());
        eavEntityJsonListResult.setRows(pageinfo.getPageResults());
        List<Map<String, Object>> list = Lists.newArrayList();
        eavEntityJsonListResult.getRows().forEach(value -> {
            EavEntity eavEntity = (EavEntity) value;
            Map<String, Object> map = Maps.newHashMap();
            map.putAll(eavEntity.getValue());
            map.put("id", eavEntity.getId());
            map.put("schemaId", eavEntity.getSchemaId());
            map.put("entityCreateTime", eavEntity.getCreateTime());
            map.put("entityUpdateTime", eavEntity.getUpdateTime());
            list.add(map);
        });
        eavEntityJsonListResult.setRows(list);
        return eavEntityJsonListResult;
    }

    @Override
    protected void onShow(HttpServletRequest request, HttpServletResponse response, Model model, EavEntity entity) throws Exception {
        model.addAttribute("entityJson", entity.getValue().toJson());
    }

    @Override
    public JsonEntityResult<EavEntity> saveJson(HttpServletRequest request, HttpServletResponse response) {
        JsonEntityResult<EavEntity> result = new JsonEntityResult<>();
        try {
            Long schemaId = Long.valueOf(request.getParameter("schemaId"));
            EavEntity eavEntity = new EavEntity();
            eavEntity.setSchemaId(schemaId);
            Map<String, String> parameters = Servlets.getParameters(request);
            parameters.remove("schemaId");
            parameters.remove("eavEntityId");
            DBMap dbMap = new DBMap();
            dbMap.putAll(parameters);
            eavEntity.setValue(dbMap);
            eavEntityEntityService.save(eavEntity);
            result.setEntity(eavEntity);
            result.setMessage("新增成功");
        } catch (Exception e) {
            handleException(result, "新增", e);
        }
        return result;
    }

    @Override
    public JsonEntityResult<EavEntity> updateJson(HttpServletRequest request, HttpServletResponse response) {
        JsonEntityResult<EavEntity> result = new JsonEntityResult<>();
        try {
            Map<String, String> parameters = Servlets.getParameters(request);
            Long id = Long.parseLong(parameters.get("eavEntityId"));
            parameters.remove("eavEntityId");
            parameters.remove("schemaId");
            eavEntityService.addEavEntityValue(id, (Map) parameters);
            result.setEntity(eavEntityEntityService.get(id));
            result.setMessage("更新成功");
        } catch (Exception e) {
            handleException(result, "更新", e);
        }
        return result;
    }

    @Override
    protected void onEdit(HttpServletRequest request, HttpServletResponse response, Model model, EavEntity entity) {
        model.addAttribute("entityJson", entity.getValue().toJson());
        model.addAttribute("eavEntityId", entity.getId());
    }
}

/*
 * acooly.cn Inc.
 * Copyright (c) 2023 All Rights Reserved.
 * create by acooly
 * date:2023-05-06
 */
package com.acooly.module.syncdata.manage.web;

import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.core.common.web.MappingMethod;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.module.syncdata.SyncDataProperties;
import com.acooly.module.syncdata.client.AsyncDataClientService;
import com.acooly.module.syncdata.common.enums.QueryColumnTypeEnum;
import com.acooly.module.syncdata.common.enums.QueryTypeEnum;
import com.acooly.module.syncdata.manage.entity.TableAsyncData;
import com.acooly.module.syncdata.manage.service.TableAsyncDataService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 同步表数据信息 管理控制器
 *
 * @author acooly
 * @date 2023-05-06 08:59:00
 */
@Controller
@RequestMapping(value = "/manage/syncdata/tableAsyncData")
public class TableAsyncDataManagerController extends AbstractJsonEntityController<TableAsyncData, TableAsyncDataService> {


    {
        allowMapping = "*";
    }

    @SuppressWarnings("unused")
    @Autowired
    private TableAsyncDataService tableAsyncDataService;
    @Autowired
    private SyncDataProperties syncDataProperties;

    @Autowired
    private AsyncDataClientService asyncDataClientService;


    @RequestMapping(value = "moveTop")
    @ResponseBody
    public JsonResult moveTop(HttpServletRequest request, HttpServletResponse response) {

        JsonResult result = new JsonResult();
        try {
            String id = request.getParameter(getEntityIdName());
            getEntityService().moveTop(Long.valueOf(id));
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
            String id = request.getParameter(getEntityIdName());
            getEntityService().moveUp(Long.valueOf(id));
            result.setMessage("上移成功");
        } catch (Exception e) {
            handleException(result, "上移", e);
        }
        return result;
    }


    /**
     * 数据同步已执行
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "doAsyncDataJson")
    @ResponseBody
    public JsonResult doAsyncDataJson(HttpServletRequest request, HttpServletResponse response) {
        JsonResult result = new JsonResult();
        allow(request, response, MappingMethod.delete);
        try {
            String idStr = request.getParameter("id");
            TableAsyncData tableAsyncData = tableAsyncDataService.get(Long.parseLong(idStr));
            asyncDataClientService.doAsyncTableDataByPage(tableAsyncData, 1);
            result.setMessage("数据同步已执行");
        } catch (Exception e) {
            handleException(result, "数据同步", e);
        }
        return result;
    }

    @Override
    protected Map<String, Boolean> getSortMap(HttpServletRequest request) {
        Map<String, Boolean> sortMap = Maps.newHashMap();
        sortMap.put("sortTime", false);
//		sortMap.put("type", true);
        return sortMap;
    }


    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allQueryTypes", QueryTypeEnum.mapping());
        model.put("allQueryColumnTypes", QueryColumnTypeEnum.mapping());
        model.put("allTypes", syncDataProperties.getBusiTypeEnum());
    }

}

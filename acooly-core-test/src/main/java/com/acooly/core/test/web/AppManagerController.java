/*
 * acooly.cn Inc.
 * Copyright (c) 2016 All Rights Reserved.
 * create by acooly
 * date:2016-12-19
 *
 */
package com.acooly.core.test.web;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.test.domain.App;
import com.acooly.core.test.service.AppService;
import com.acooly.core.utils.Dates;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * app 管理控制器
 *
 * @author acooly Date: 2016-12-19 21:09:09
 */
@Controller
@RequestMapping(value = "/manage/module/app/app")
public class AppManagerController extends AbstractJQueryEntityController<App, AppService> {

    private static List<String> APP_EXPORT_TITLES =
            Lists.newArrayList(new String[]{"编号", "昵称", "名称", "类型", "用户编码", "注册时间"});

    @SuppressWarnings("unused")
    @Autowired
    private AppService appService;

    {
        allowMapping = "*";
    }

    @RequestMapping("/testFtl")
    public String testFtl(ModelMap modelMap) {
        modelMap.put("name", "na");
        modelMap.put("message", "hi");
        return "test";
    }

    /**
     * 导入
     *
     * @param fields
     * @return
     */
    @Override
    protected App doImportEntity(List<String> fields) {
        App app = new App();
        app.setDisplayName(fields.get(1));
        app.setName(fields.get(2));
        app.setParentAppId(Long.parseLong(fields.get(3)));
        app.setType(fields.get(4));
        return app;
    }

    @Override
    protected boolean isIgnoreTitle(HttpServletRequest request) {
        return true;
    }

    /**
     * 导出
     *
     * @param entity
     * @return
     */
    @Override
    protected List<String> doExportEntity(App entity) {
        List<String> row = Lists.newArrayList();
        row.add(String.valueOf(entity.getId()));
        row.add(entity.getDisplayName());
        row.add(entity.getName());
        row.add(entity.getType());
        row.add(String.valueOf(entity.getUserId()));
        row.add(Dates.format(entity.getRawAddTime()));
        return row;
    }

    @Override
    protected List<String> getExportTitles() {
        return APP_EXPORT_TITLES;
    }
}

/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by zhangpu
 * date:2019-05-06
 */
package com.acooly.module.data.region.web;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.module.data.region.dto.RegionInfo;
import com.acooly.module.data.region.entity.Region;
import com.acooly.module.data.region.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 省市区编码表 管理控制器
 *
 * @author zhangpu
 * Date: 2019-05-06 18:32:21
 */
@Controller
@RequestMapping(value = "/manage/module/data/region")
public class RegionManagerController extends AbstractJQueryEntityController<Region, RegionService> {


    {
        allowMapping = "";
    }

    @Autowired
    private RegionService regionService;


    @RequestMapping("tree")
    @ResponseBody
    public List<RegionInfo> tree(HttpServletRequest request, HttpServletResponse response) {
        return regionService.tree();
    }
}

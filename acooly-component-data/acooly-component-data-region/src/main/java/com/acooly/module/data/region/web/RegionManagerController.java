/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by zhangpu
 * date:2019-05-06
 */
package com.acooly.module.data.region.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonListResult;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.module.data.region.dto.RegionInfo;
import com.acooly.module.data.region.entity.Region;
import com.acooly.module.data.region.service.RegionService;

/**
 * 省市区编码表 管理控制器
 *
 * @author zhangpu Date: 2019-05-06 18:32:21
 */
@Controller
@RequestMapping(value = "/manage/module/data/region")
public class RegionManagerController extends AbstractJQueryEntityController<Region, RegionService> {

	{
		allowMapping = "*";
	}

	@Autowired
	private RegionService regionService;

	@RequestMapping("tree")
	@ResponseBody
	public List<RegionInfo> tree(HttpServletRequest request, HttpServletResponse response) {
		return regionService.tree(Apps.getAppName());
	}

	@RequestMapping(value = "listTree")
	@ResponseBody
	public JsonListResult<RegionInfo> getTopLevel(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		JsonListResult<RegionInfo> result = new JsonListResult<>();
		try {
			result.appendData(referenceData(request));
			List<RegionInfo> regions = regionService.tree(Apps.getAppName());
			result.setTotal(Long.valueOf(regions.size()));
			result.setRows(regions);
		} catch (Exception e) {
			handleException(new JsonResult(), "省市县联动查询树", e);
		}
		return result;
	}

}

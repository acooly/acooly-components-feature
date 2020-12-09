/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-04-26
 */
package com.acooly.module.cms.portal;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractPortalController;
import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.module.cms.domain.CmsCode;
import com.acooly.module.cms.domain.Content;
import com.acooly.module.cms.service.CmsCodeService;
import com.acooly.module.cms.service.ContentService;
import com.acooly.module.cms.service.ContentTypeService;
import com.acooly.module.cms.web.ContentManagerController;

import lombok.extern.slf4j.Slf4j;

/**
 * 编码 管理控制器
 *
 * @author cuifuq
 */
@Slf4j
@Controller
@RequestMapping(value = "/cms/portal")
public class ContentPortalController extends AbstractPortalController<Content, ContentService> {

	@Autowired
	private CmsCodeService cmsCodeService;
	@Autowired
	private ContentService contentService;
	@Autowired
	private ContentTypeService contentTypeService;

	{
		allowMapping = "query";
	}

	/**
	 * 单个cms 富文本框显示
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "single_{id}")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model,
			@PathVariable("id") String id) {
		try {
			Content content = contentService.get(Long.parseLong(id));
			model.addAttribute("content", content);
		} catch (Exception e) {
			handleException("富文本失败", e, request);
		}
		return "/portal/content_single";
	}
}

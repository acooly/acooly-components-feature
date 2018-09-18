/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-04-26
 */
package com.acooly.module.cms.web;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.module.cms.domain.CmsCode;
import com.acooly.module.cms.domain.Content;
import com.acooly.module.cms.service.CmsCodeService;
import com.acooly.module.cms.service.ContentService;
import com.acooly.module.cms.service.ContentTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 编码 管理控制器
 *
 * @author acooly Date: 2017-04-26 17:16:38
 */
@Slf4j
@Controller
@RequestMapping(value = "/manage/module/cms/cmsCode")
public class CmsCodeManagerController
        extends AbstractJQueryEntityController<CmsCode, CmsCodeService> {

    @Autowired
    private CmsCodeService cmsCodeService;
    @Autowired
    private ContentService contentService;
    @Autowired
    private ContentTypeService contentTypeService;

    {
        allowMapping = "*";
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allStatuss", ContentManagerController.allStatuss);
        Map<String, String> codeAndNames = contentTypeService.getEnabledCodeAndName();
        model.put("codeAndNames", codeAndNames);
    }


    @Override
    public JsonResult deleteJson(HttpServletRequest request, HttpServletResponse response) {
        CmsCode cmsCode = getCmsCode(request);
        String keyCode = null;
        if (cmsCode != null) {
            keyCode = cmsCode.getKeycode();
        }
        Content content = contentService.getByKeycode(keyCode);
        if (content != null) {
            log.error("任务编码有其他新闻在使用，不能删除：code{}", keyCode);
            JsonEntityResult<CmsCode> result = new JsonEntityResult<>();
            result.setSuccess(false);
            result.setMessage("任务编码有其他新闻在使用，不能删除!");
            return result;
        }
        return super.deleteJson(request, response);
    }

    private CmsCode getCmsCode(HttpServletRequest request) {
        return getEntityService().get(Long.valueOf(request.getParameter("id")));
    }
}

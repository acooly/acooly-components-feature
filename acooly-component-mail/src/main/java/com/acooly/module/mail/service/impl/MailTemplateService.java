/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-25 11:35 创建
 */
package com.acooly.module.mail.service.impl;

import com.acooly.core.common.boot.ApplicationContextHolder;
import com.acooly.core.common.boot.Apps;
import com.acooly.core.common.exception.AppConfigException;
import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.FreeMarkers;
import com.acooly.module.mail.MailDto;
import com.acooly.module.mail.MailProperties;
import com.acooly.module.mail.entity.EmailTemplate;
import com.acooly.module.mail.service.EmailTemplateService;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author qiubo@yiji.com
 */
@Service
public class MailTemplateService {
    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private EmailTemplateService emailTemplateService;

    private Map<String, String> templates = Maps.newConcurrentMap();

    public String parse(String key, MailDto dto) {
        //兼容之前，先查数据库模板，如果没有从classpath:/mail/下查找
        String template = null;
        EmailTemplate emailTemplate = emailTemplateService.findByName(key);
        if (emailTemplate != null) {
            template = emailTemplate.getContent();
            dto.setTemplateTile(emailTemplate.getTitle());
            if (Strings.isNullOrEmpty(dto.getSubject())) {
                dto.setSubject(emailTemplate.getSubject());
            }
        }
        if (StringUtils.isEmpty(template)) {
            template = getTemplates(key);
        }
        return FreeMarkers.rendereString(template, dto.getParams());
    }

    private String getTemplates(String key) {
        if (Apps.isDevMode()) {
            templates.clear();
        }
        String t = templates.get(key);
        if (Strings.isNullOrEmpty(t)) {
            String loc = mailProperties.getMailTemplatePath() + key + ".ftl";
            Resource resource = ApplicationContextHolder.get().getResource(loc);
            if (resource.exists()) {
                try {
                    t = Resources.toString(resource.getURL(), Charsets.UTF_8);
                    templates.put(key, t);
                } catch (IOException e) {
                    throw new AppConfigException("邮件模板不存在:" + loc, e);
                }
            } else {
                throw new AppConfigException("邮件模板不存在:" + loc);
            }
        }
        return t;
    }
}

/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-04-27
 *
 */
package com.acooly.module.mail.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.mail.entity.EmailTemplate;

import java.util.List;

/**
 * @author shuijing
 */
public interface EmailTemplateService extends EntityService<EmailTemplate> {


    /**
     * 根据指定属性查询
     *
     * @param property
     * @param value
     * @return
     */
    List<EmailTemplate> find(String property, Object value);

    /**
     * 根据模板名称（KEY）查询
     *
     * @param templateName
     */
    EmailTemplate findByName(String templateName);

    /**
     * 根据模板名称（KEY）删除模板
     *
     * @param templateName
     */
    void deleteByName(String templateName);
}

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
    List<EmailTemplate> find(String property, Object value);
}

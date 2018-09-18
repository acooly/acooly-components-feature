/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-04-27
 */
package com.acooly.module.mail.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.mail.dao.EmailTemplateDao;
import com.acooly.module.mail.entity.EmailTemplate;
import com.acooly.module.mail.service.EmailTemplateService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shuijing
 */
@Service("emailTemplateService")
public class EmailTemplateServiceImpl extends EntityServiceImpl<EmailTemplate, EmailTemplateDao>
        implements EmailTemplateService {

    @Override
    public List<EmailTemplate> find(String property, Object value) {
        return getEntityDao().find(property, value);
    }
}

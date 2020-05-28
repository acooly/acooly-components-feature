/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by acooly
 * date:2020-05-19
 */
package com.acooly.module.smsend.manage.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.smsend.common.enums.SmsProvider;
import com.acooly.module.smsend.dao.SmsTemplateProviderDao;
import com.acooly.module.smsend.entity.SmsTemplateProvider;
import com.acooly.module.smsend.manage.SmsTemplateProviderService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模板渠道 Service实现
 *
 * @author acooly
 * @date 2020-05-19 15:03:06
 */
@Service("smsTemplateProviderService")
public class SmsTemplateProviderServiceImpl extends EntityServiceImpl<SmsTemplateProvider, SmsTemplateProviderDao> implements SmsTemplateProviderService {


    @Override
    public SmsTemplateProvider findUnique(String templateCode, SmsProvider provider) {
        return getEntityDao().findByProviderAndTemplateCode(provider, templateCode);
    }

    @Override
    public List<SmsTemplateProvider> findByTemplateCode(String templateCode) {
        return getEntityDao().findByTemplateCode(templateCode);
    }
}

/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by acooly
 * date:2020-05-19
 */
package com.acooly.module.smsend.manage.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.smsend.manage.SmsTemplateProviderService;
import com.acooly.module.smsend.dao.SmsTemplateProviderDao;
import com.acooly.module.smsend.entity.SmsTemplateProvider;

/**
 * 模板渠道 Service实现
 *
 * @author acooly
 * @date 2020-05-19 15:03:06
 */
@Service("smsTemplateProviderService")
public class SmsTemplateProviderServiceImpl extends EntityServiceImpl<SmsTemplateProvider, SmsTemplateProviderDao> implements SmsTemplateProviderService {

}

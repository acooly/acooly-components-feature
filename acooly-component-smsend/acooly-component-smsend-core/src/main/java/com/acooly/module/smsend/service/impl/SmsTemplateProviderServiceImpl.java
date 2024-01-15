/*
 * acooly.cn Inc.
 * Copyright (c) 2023 All Rights Reserved.
 * create by acooly
 * date:2023-12-28
 */
package com.acooly.module.smsend.service.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.smsend.service.SmsTemplateProviderService;
import com.acooly.module.smsend.dao.SmsTemplateProviderDao;
import com.acooly.module.smsend.entity.SmsTemplateProvider;

/**
 * 模板渠道 Service实现
 *
 * @author acooly
 * @date 2023-12-28 09:36:37
 */
@Service("smsTemplateProviderService")
public class SmsTemplateProviderServiceImpl extends EntityServiceImpl<SmsTemplateProvider, SmsTemplateProviderDao> implements SmsTemplateProviderService {

}

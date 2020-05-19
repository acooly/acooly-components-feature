/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by acooly
 * date:2020-05-19
 */
package com.acooly.module.smsend.manage.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.smsend.manage.SmsTemplateService;
import com.acooly.module.smsend.dao.SmsTemplateDao;
import com.acooly.module.smsend.entity.SmsTemplate;

/**
 * 短信模板 Service实现
 *
 * @author acooly
 * @date 2020-05-19 15:03:05
 */
@Service("smsTemplateService")
public class SmsTemplateServiceImpl extends EntityServiceImpl<SmsTemplate, SmsTemplateDao> implements SmsTemplateService {

}

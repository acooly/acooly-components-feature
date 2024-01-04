/*
 * acooly.cn Inc.
 * Copyright (c) 2023 All Rights Reserved.
 * create by acooly
 * date:2023-12-28
 */
package com.acooly.module.smsend.service.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.smsend.service.SmsTemplateService;
import com.acooly.module.smsend.dao.SmsTemplateDao;
import com.acooly.module.smsend.entity.SmsTemplate;

/**
 * 短信模板 Service实现
 *
 * @author acooly
 * @date 2023-12-28 09:36:37
 */
@Service("smsTemplateService")
public class SmsTemplateServiceImpl extends EntityServiceImpl<SmsTemplate, SmsTemplateDao> implements SmsTemplateService {

}

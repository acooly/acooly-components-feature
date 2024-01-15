/*
 * acooly.cn Inc.
 * Copyright (c) 2023 All Rights Reserved.
 * create by acooly
 * date:2023-12-28
 */
package com.acooly.module.smsend.service.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.smsend.service.SmsAppService;
import com.acooly.module.smsend.dao.SmsAppDao;
import com.acooly.module.smsend.entity.SmsApp;

/**
 * 短信发送应用 Service实现
 *
 * @author acooly
 * @date 2023-12-28 09:36:37
 */
@Service("smsAppService")
public class SmsAppServiceImpl extends EntityServiceImpl<SmsApp, SmsAppDao> implements SmsAppService {

}

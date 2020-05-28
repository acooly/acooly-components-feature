/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by acooly
 * date:2020-05-19
 */
package com.acooly.module.smsend.manage.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.enums.SimpleStatus;
import com.acooly.module.smsend.dao.SmsAppDao;
import com.acooly.module.smsend.entity.SmsApp;
import com.acooly.module.smsend.manage.SmsAppService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短信发送应用 Service实现
 *
 * @author acooly
 * @date 2020-05-19 15:03:06
 */
@Service("smsAppService")
public class SmsAppServiceImpl extends EntityServiceImpl<SmsApp, SmsAppDao> implements SmsAppService {


    @Override
    public List<SmsApp> getAvailables() {
        return getEntityDao().findByStatus(SimpleStatus.enable);
    }

    @Override
    public SmsApp findByAppId(String appId) {
        return getEntityDao().findByAppId(appId);
    }
}

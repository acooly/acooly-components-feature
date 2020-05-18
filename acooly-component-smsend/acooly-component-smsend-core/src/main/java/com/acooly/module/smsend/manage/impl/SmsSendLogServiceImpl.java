/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by acooly
 * date:2020-05-08
 */
package com.acooly.module.smsend.manage.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.smsend.dao.SmsSendLogDao;
import com.acooly.module.smsend.entity.SmsSendLog;
import com.acooly.module.smsend.manage.SmsSendLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 短信发送记录 Service实现
 *
 * @author acooly
 * @date 2020-05-08 14:32:47
 */
@Service("smsSendLogService")
public class SmsSendLogServiceImpl extends EntityServiceImpl<SmsSendLog, SmsSendLogDao> implements SmsSendLogService {

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void saveInNewTrans(SmsSendLog smsSendLog) {
        save(smsSendLog);
    }

    @Override
    public void savesInNewTrans(List<SmsSendLog> smsSendLogs) {
        saves(smsSendLogs);
    }
}

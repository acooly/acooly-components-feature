/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by acooly
 * date:2020-05-08
 *
 */
package com.acooly.module.smsend.manage;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.smsend.entity.SmsSendLog;

import java.util.List;

/**
 * 短信发送记录 Service接口
 *
 * @author acooly
 * @date 2020-05-08 14:32:47
 */
public interface SmsSendLogService extends EntityService<SmsSendLog> {

    /**
     * 新启事务持久化
     *
     * @param smsSendLog
     */
    void saveInNewTrans(SmsSendLog smsSendLog);

    /**
     * 新启事务批量持久化
     * @param smsSendLogs
     */
    void savesInNewTrans(List<SmsSendLog> smsSendLogs);

}

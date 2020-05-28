/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by acooly
 * date:2020-05-19
 *
 */
package com.acooly.module.smsend.manage;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.smsend.entity.SmsApp;

import java.util.List;

/**
 * 短信发送应用 Service接口
 *
 * @author acooly
 * @date 2020-05-19 15:03:06
 */
public interface SmsAppService extends EntityService<SmsApp> {

    /**
     * 获取有效发送应用
     * @return
     */
    List<SmsApp> getAvailables();

    /**
     * 根据AppId查询
     * @param appId
     * @return
     */
    SmsApp findByAppId(String appId);
}

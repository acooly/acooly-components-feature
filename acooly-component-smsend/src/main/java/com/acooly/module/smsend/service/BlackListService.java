/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by shuijing
 * date:2017-08-01
 *
 */
package com.acooly.module.smsend.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.smsend.domain.SmsBlackList;

import java.util.List;

/**
 * 短信黑名单 Service接口
 * <p>
 * Date: 2017-08-01 17:28:24
 *
 * @author shuijing
 */
public interface BlackListService extends EntityService<SmsBlackList> {

    List<SmsBlackList> getEffective();

}

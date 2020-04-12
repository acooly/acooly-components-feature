/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by shuijing
 * date:2017-08-01
 */
package com.acooly.module.smsend.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;

import com.acooly.module.smsend.dao.SmsBlackListDao;
import com.acooly.module.smsend.domain.SmsBlackList;
import com.acooly.module.smsend.service.BlackListService;
import org.springframework.stereotype.Service;

/**
 * 短信黑名单 Service实现
 * <p>
 * Date: 2017-08-01 17:28:24
 *
 * @author shuijing
 */
@Service("blackListService")
public class BlackListServiceImpl extends EntityServiceImpl<SmsBlackList, SmsBlackListDao> implements BlackListService {

}

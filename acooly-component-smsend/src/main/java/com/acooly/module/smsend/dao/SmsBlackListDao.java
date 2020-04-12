/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by shuijing
 * date:2017-08-01
 */
package com.acooly.module.smsend.dao;

import com.acooly.module.jpa.EntityJpaDao;
import com.acooly.module.smsend.domain.SmsBlackList;

/**
 * 短信黑名单 JPA Dao
 * <p>
 * Date: 2017-08-01 17:28:24
 *
 * @author shuijing
 */
public interface SmsBlackListDao extends EntityJpaDao<SmsBlackList, Long> {

}

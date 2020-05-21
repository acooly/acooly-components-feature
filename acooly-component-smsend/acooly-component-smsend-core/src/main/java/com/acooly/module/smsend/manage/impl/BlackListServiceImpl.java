/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by shuijing
 * date:2017-08-01
 */
package com.acooly.module.smsend.manage.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.enums.SimpleStatus;
import com.acooly.module.smsend.dao.SmsBlackListDao;
import com.acooly.module.smsend.entity.SmsBlackList;
import com.acooly.module.smsend.manage.BlackListService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * 短信黑名单 Service实现
 * <p>
 * Date: 2017-08-01 17:28:24
 *
 * @author shuijing
 */
@Service("blackListService")
public class BlackListServiceImpl extends EntityServiceImpl<SmsBlackList, SmsBlackListDao> implements BlackListService {

    public static final String ALL_SMS_BLACKLIST_CACHE_KEY = "SMSEND:ALL_BLACKLIST";

    @Override
//    @CacheEvict(value = ALL_SMS_BLACKLIST_CACHE_KEY, allEntries = true)
    public void removeById(Serializable id) throws BusinessException {
        super.removeById(id);
    }

    @Override
//    @CacheEvict(value = ALL_SMS_BLACKLIST_CACHE_KEY, allEntries = true)
    public void save(SmsBlackList o) throws BusinessException {
        super.save(o);
    }

    @Override
//    @CacheEvict(value = ALL_SMS_BLACKLIST_CACHE_KEY, allEntries = true)
    public void update(SmsBlackList o) throws BusinessException {
        super.update(o);
    }


    @Override
//    @Cacheable(ALL_SMS_BLACKLIST_CACHE_KEY)
    public List<SmsBlackList> getEffective() {
        return getEntityDao().findByStatus(SimpleStatus.enable);
    }
}

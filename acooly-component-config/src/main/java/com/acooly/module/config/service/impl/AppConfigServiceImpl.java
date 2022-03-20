/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by shuijing
 * date:2018-06-19
 */
package com.acooly.module.config.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Strings;
import com.acooly.module.config.dao.AppConfigDao;
import com.acooly.module.config.entity.AppConfig;
import com.acooly.module.config.service.AppConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;

import static com.acooly.module.config.AppConfigAutoConfig.CONFIG_REDIS_TOPIC;

/**
 * sys_app_config Service实现
 * <p>
 * Date: 2018-06-19 21:52:29
 *
 * @author shuijing
 */
@Service("appConfigService")
public class AppConfigServiceImpl extends EntityServiceImpl<AppConfig, AppConfigDao> implements AppConfigService {

    @Autowired
    private AppConfigManager appConfigCache;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public AppConfig get(Serializable id) throws BusinessException {
        AppConfig appConfig = super.get(id);
        // 特殊处理：对老数据，title为null,comments不为null的情况，拷贝comments -> title
        if (Strings.isBlank(appConfig.getTitle())) {
            appConfig.setTitle(appConfig.getComments());
        }
        return appConfig;
    }

    @Override
    public void saveOrUpdate(AppConfig appConfig) throws BusinessException {
        super.saveOrUpdate(appConfig);
        appConfigCache.invalidate(appConfig);
        redisTemplate.convertAndSend(CONFIG_REDIS_TOPIC, appConfig.key());
    }

    @Override
    public void save(AppConfig appConfig) throws BusinessException {
        super.save(appConfig);
        appConfigCache.invalidate(appConfig);
        redisTemplate.convertAndSend(CONFIG_REDIS_TOPIC, appConfig.key());
    }

    @Override
    public void update(AppConfig appConfig) throws BusinessException {
        super.update(appConfig);
        appConfigCache.invalidate(appConfig);
        redisTemplate.convertAndSend(CONFIG_REDIS_TOPIC, appConfig.key());
    }
}

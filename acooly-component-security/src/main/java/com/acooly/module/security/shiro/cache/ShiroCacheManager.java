/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2016-10-27 23:35 创建
 */
package com.acooly.module.security.shiro.cache;

import com.acooly.core.common.boot.Apps;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author qiubo
 */
public class ShiroCacheManager implements CacheManager {

    public static final String KEY_PREFIX = Apps.getAppName() + "_shiro_redis_cache:";
    public static final String KEY_AUTHZ = Apps.getAppName() + "_authorizationCache";
    public static final String KEY_AUTHC = Apps.getAppName() + "_authenticationCache";
    private RedisTemplate redisTemplate;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {

        return new ShiroRedisCache(KEY_PREFIX + name, redisTemplate);
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}

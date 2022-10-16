/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2016-10-27 23:35 创建
 */
package cn.acooly.component.rbac.shiro.cache;

import com.acooly.core.common.boot.Apps;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Shiro的CacheManager的Redis实现
 * <p>
 * 如果环境中存在security组件，优先使用security组件`ShiroCacheManager`,否则使用该实现
 *
 * @author zhangpu
 */
public class RbacShiroCacheManager implements CacheManager {

    /**
     * RBAC的默认缓存key
     */
    public static final String KEY_PREFIX = Apps.getAppName() + "_rbac_shiro_redis_cache:";


    private RedisTemplate redisTemplate;

    /**
     * 默认过期时间，单位分钟，默认2小时
     */
    private int defaultTimeout = 2 * 60;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {

        return new ShiroRedisCache(KEY_PREFIX + name, redisTemplate, defaultTimeout);
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setDefaultTimeout(int defaultTimeout) {
        this.defaultTimeout = defaultTimeout;
    }
}

package com.acooly.module.config.service.impl;

import com.acooly.module.config.dao.AppConfigDao;
import com.acooly.module.config.entity.AppConfig;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

import static com.acooly.module.config.entity.AppConfig.key;

/**
 * @author qiuboboy@qq.com
 * @date 2018-06-20 03:58
 */
@Component
@Slf4j
public class AppConfigManager implements InitializingBean {
    @Autowired
    private RedisTemplate<String, AppConfig> redisTemplate;
    @Autowired
    private AppConfigDao appConfigDao;
    @Autowired(required = false)
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;
    private Cache<String, AppConfig> configCache = null;


    public String get(String name) {
        AppConfig appConfig = getAppConfig(name);
        if (appConfig != null) {
            return appConfig.getConfigValue();
        } else {
            return null;
        }
    }

    public AppConfig getAppConfig(String name) {
        String key = key(name);
        //1. get from local cache
        AppConfig appConfig = configCache.getIfPresent(key);
        if (appConfig != null) {
            return appConfig;
        } else {
            //2. get from redis
            appConfig = redisTemplate.opsForValue().get(key);
            if (appConfig != null) {
                appConfig.initLocalCache(configCache);
                return appConfig;
            } else {
                //3. get from database
                appConfig = appConfigDao.findAppConfigByName(name);
                if (appConfig != null) {
                    appConfig.initLocalCache(configCache);
                    appConfig.initRedisCache(redisTemplate);
                    return appConfig;
                } else {
                    log.warn("配置项[{}]不存在", name);
                    return null;
                }
            }
        }
    }

    /**
     * 读取配置
     *
     * @param name  配置项名称
     * @param clazz 转换为的类型
     * @param <T>   返回类型
     * @return 值
     */
    public <T> T get(String name, Class<T> clazz) {
        Assert.hasText(name);
        Assert.notNull(clazz);
        String value = get(name);
        return conversionService.convert(value, clazz);
    }

    public void create(AppConfig appConfig) {
        appConfigDao.create(appConfig);
    }

    public void invalidate(AppConfig appConfig) {
        if (!appConfig.isNew()) {
            String key = appConfig.key();
            configCache.invalidate(key);
            redisTemplate.delete(key);
        }
    }

    public void invalidate(String name) {
        String key = key(name);
        configCache.invalidate(key);
        redisTemplate.delete(key);
    }

    public void delete(String name) {
        appConfigDao.deleteByName(name);
        invalidate(name);
    }


    @Override
    public void afterPropertiesSet() {

        configCache = Caffeine.newBuilder().expireAfter(new Expiry<String, AppConfig>() {
            @Override
            public long expireAfterCreate(
                    String key, AppConfig value, long currentTime) {
                return TimeUnit.MILLISECONDS.toNanos(value.getLocalCacheExpire());
            }

            @Override
            public long expireAfterUpdate(
                    String key, AppConfig value, long currentTime, long currentDuration) {
                return currentDuration;
            }

            @Override
            public long expireAfterRead(
                    String key, AppConfig value, long currentTime, long currentDuration) {
                return currentDuration;
            }
        }).build();
        appConfigDao.findAll().stream().forEach(appConfig -> {
                    appConfig.initLocalCache(configCache);
                    appConfig.initRedisCache(redisTemplate);
                }
        );
    }
}

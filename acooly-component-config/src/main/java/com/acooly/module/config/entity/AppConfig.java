/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by shuijing
 * date:2018-06-19
 */
package com.acooly.module.config.entity;


import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.config.ConfigProperties;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisTemplate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.concurrent.TimeUnit;

/**
 * sys_app_config Entity
 *
 * @author qiubo
 * Date: 2018-06-19 21:52:29
 */
@Entity
@Table(name = "sys_app_config")
@Getter
@Setter
public class AppConfig extends AbstractEntity {


    /**
     * 标题(中文名称)
     * 升级代替comments
     */
    @NotBlank
    @Size(max = 32)
    private String title;

    /**
     * 配置数据类型
     * 管理界面处理方式不同
     */
    private String configType;

    /**
     * 配置项名称
     */
    @NotBlank
    @Size(max = 128)
    private String configName;

    /**
     * 配置值
     */
    @NotBlank
    @Size(max = 2048)
    private String configValue;

    /**
     * 配置描述
     */
    @NotBlank
    @Size(max = 255)
    private String comments;

    /**
     * 本地缓存过期时间,单位ms
     */
    private Integer localCacheExpire = 0;

    /**
     * redis缓存过期时间,单位ms
     */
    private Integer redisCacheExpire = 600000;

    public void initLocalCache(Cache<String, AppConfig> cache) {
        if (localCacheExpire != 0) {
            cache.put(key(), this);
        }
    }

    public void initRedisCache(RedisTemplate<String, AppConfig> redisTemplate) {
        if (redisCacheExpire != 0) {
            redisTemplate.opsForValue().set(key(), this, this.getRedisCacheExpire(), TimeUnit.MILLISECONDS);
        }
    }


    public String key() {
        return key(this.getConfigName());
    }

    public static String key(String name) {
        return ConfigProperties.CACHE_PREFIX + name;
    }
}

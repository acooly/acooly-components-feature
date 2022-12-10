/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2016-10-27 23:43 创建
 */
package com.acooly.module.security.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author qiubo
 */
public class ShiroRedisCache<K, V> implements Cache<K, V> {

    private K cachaName;

    private RedisTemplate<K, V> redisTemplate;

    private int defaultTimeout = 2 * 60;

    public ShiroRedisCache() {
    }


    public ShiroRedisCache(K cacheName, RedisTemplate redisTemplate, int defaultTimeout) {
        this.cachaName = cacheName;
        this.redisTemplate = redisTemplate;
        this.defaultTimeout = defaultTimeout;
    }

    public ShiroRedisCache(K cacheName, RedisTemplate redisTemplate) {
        this.cachaName = cacheName;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public V get(K key) throws CacheException {
        HashOperations<K, K, V> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(cachaName, key);
    }

    @Override
    public V put(K key, V value) throws CacheException {
        HashOperations<K, K, V> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(cachaName, key, value);
        redisTemplate.expire(cachaName, defaultTimeout, TimeUnit.MINUTES);
        return hashOperations.get(cachaName, key);
    }

    @Override
    public V remove(K key) throws CacheException {
        HashOperations<K, K, V> hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(cachaName, key);
        return null;
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.delete(cachaName);
    }

    @Override
    public int size() {
        HashOperations<K, K, V> hashOperations = redisTemplate.opsForHash();
        return hashOperations.size(cachaName).intValue();
    }

    @Override
    public Set<K> keys() {
        HashOperations<K, K, V> hashOperations = redisTemplate.opsForHash();
        return hashOperations.keys(cachaName);
    }

    @Override
    public Collection<V> values() {
        HashOperations<K, K, V> hashOperations = redisTemplate.opsForHash();
        return hashOperations.values(cachaName);
    }

    public RedisTemplate<K, V> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}

package com.acooly.module.distributedlock;


import com.acooly.module.distributedlock.redis.RedisDistributedLock;
import com.acooly.module.distributedlock.zookeeper.ZkDistributedLock;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.util.UUID;
import java.util.concurrent.locks.Lock;

public class DistributedLockFactory {

    public static final String LOCK_PATH = "/acooly/distributeLock/";

    private DistributedLockProperties properties;

    @Autowired(required = false)
    private CuratorFramework curatorFramework;

    @Autowired
    private RedisTemplate redisTemplate;

    public DistributedLockFactory(DistributedLockProperties properties) {
        this.properties = properties;
    }

    public Lock newLock(String lockName) {

        Assert.notNull(lockName, "lockName不能为空");
        //redis模式
        if (properties.getMode() == DistributedLockProperties.Mode.REDIS) {
            return new RedisDistributedLock(lockName, redisTemplate, UUID.randomUUID());
        }

        //zookeeper模式
        if (properties.getMode() == DistributedLockProperties.Mode.ZOOKEEPER) {
            String lockPath = LOCK_PATH + lockName;
            InterProcessMutex mutex = new InterProcessMutex(curatorFramework, lockPath);
            return new ZkDistributedLock(mutex, lockPath);
        }
        return null;
    }

}

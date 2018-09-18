/*
 * www.prosysoft.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * shuijing 2018-07-05 11:21 创建
 */
package com.acooly.module.distributedlock.redis;

import com.acooly.module.cache.DefaultKeySerializer;
import com.acooly.module.distributedlock.exception.DistributedException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 分布式锁的redis实现
 * <p>
 * 1. 支持线程可重入
 * 2. 不会出现客户端宕机死锁现象
 *
 * @author shuijing
 */
@Setter
@Getter
@Slf4j
public class RedisDistributedLock implements Lock {

    private Long lockWatchdogTimeout = 30 * 1000L;
    public static final Long unlockMessage = 0L;
    public static final String SPLIT = "|";

    Timer timer = new Timer();

    DefaultKeySerializer defaultKeySerializer = new DefaultKeySerializer();

    private static final ConcurrentMap<String, TimerTask> expirationRenewalMap = Maps.newConcurrentMap();

    private final String name;

    private RedisTemplate redisTemplate;
    final UUID id;

    public RedisDistributedLock(String name, RedisTemplate redisTemplate, UUID id) {
        this.name = name;
        this.redisTemplate = redisTemplate;
        this.id = id;
    }

    @Override
    public void lock() {
        log.info("开始获取分布式锁:{} ...", name);
        try {
            lockInterruptibly();
        } catch (InterruptedException e) {
            //Thread.currentThread().interrupt();
            log.info("获取分布式锁失败:{}", name, e.getMessage());
            throw new DistributedException("获取锁失败", e);
        }
        log.info("获取分布式锁成功:{}", name);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        lockInterruptibly(-1, null);
    }

    @Override
    public boolean tryLock() {
        log.info("尝试获取分布式锁:{} ...", name);

        Long aLong = tryAcquireSync(-1, null, Thread.currentThread().getId());
        boolean locked = aLong == null;

        log.info("获取分布式锁结果:{}", locked);
        return locked;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        log.info("尝试获取分布式锁:{} ...,最大等待时间:{} ms", name, unit.toMillis(time));

        boolean locked = tryLock(time, -1, unit);

        log.info("获取分布式锁结果:{}", locked);
        return locked;
    }

    @Override
    public void unlock() {
        Long unlocked = unlockInnerSync(Thread.currentThread().getId());
        if (unlocked == null) {
            throw new IllegalMonitorStateException("尝试去释放锁:" + getName() + "，但是没有被当前线程锁住，节点id: "
                    + id + " 线程id: " + Thread.currentThread().getId());
        }
        if (Long.valueOf(1).equals(unlocked)) {
            //取消内部循环
            cancelExpirationRenewal();
            //完全表示重入几次都释放完成
            log.info("分布式锁完全释放成功:{}", name);
        }
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException("分布式锁不支持创建条件");
    }


    public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException {

        long time = unit.toMillis(waitTime);
        long current = System.currentTimeMillis();
        final long threadId = Thread.currentThread().getId();
        Long ttl = tryAcquire(leaseTime, unit, threadId);
        // lock acquired
        if (ttl == null) {
            return true;
        }

        time -= (System.currentTimeMillis() - current);
        if (time <= 0) {
            return false;
        }

        RedisLockEntityHolder.getInstance().registEntry(getLockName());
        RedisLockEntityHolder.getInstance().subscribeTopic(getLockName(), getChannelName(), redisTemplate, RedisLockEntityHolder.getInstance().getEntry(getLockName()));

        try {
            while (true) {
                long currentTime = System.currentTimeMillis();
                ttl = tryAcquire(leaseTime, unit, threadId);
                // lock acquired
                if (ttl == null) {
                    return true;
                }

                time -= (System.currentTimeMillis() - currentTime);
                if (time <= 0) {
                    return false;
                }

                // 线程等待
                currentTime = System.currentTimeMillis();
                if (ttl >= 0 && ttl < time) {
                    RedisLockEntityHolder.getInstance().getEntry(getLockName()).getLatch().tryAcquire(ttl, TimeUnit.MILLISECONDS);
                } else {
                    RedisLockEntityHolder.getInstance().getEntry(getLockName()).getLatch().tryAcquire(time, TimeUnit.MILLISECONDS);
                }

                time -= (System.currentTimeMillis() - currentTime);
                if (time <= 0) {
                    return false;
                }
            }
        } finally {
            RedisLockEntityHolder.getInstance().unSubscribeTopic(getLockName());
        }
    }

    public void lockInterruptibly(long leaseTime, TimeUnit unit) throws InterruptedException {
        long threadId = Thread.currentThread().getId();
        Long ttl = tryAcquire(leaseTime, unit, threadId);

        // lock acquired
        if (ttl == null) {
            log.debug("locked ok:{},threadId:{}", getLockName(), threadId);
            return;
        }

        RedisLockEntityHolder.getInstance().registEntry(getLockName());
        RedisLockEntityHolder.getInstance().subscribeTopic(getLockName(), getChannelName(), redisTemplate, RedisLockEntityHolder.getInstance().getEntry(getLockName()));

        try {
            while (true) {
                //尝试去取锁
                ttl = tryAcquire(leaseTime, unit, threadId);
                // lock acquired
                if (ttl == null) {
                    break;
                }

                // 线程等待
                if (ttl >= 0) {
                    RedisLockEntityHolder.getInstance().getEntry(getLockName()).getLatch().tryAcquire(ttl, TimeUnit.MILLISECONDS);
                } else {
                    RedisLockEntityHolder.getInstance().getEntry(getLockName()).getLatch().acquire();
                }
            }
        } finally {
            RedisLockEntityHolder.getInstance().unSubscribeTopic(getLockName());
        }
    }

    private Long tryAcquire(long leaseTime, TimeUnit unit, long threadId) {
        return tryAcquireSync(leaseTime, unit, threadId);
    }

    private Long tryAcquireSync(long leaseTime, TimeUnit unit, final long threadId) {

        if (leaseTime != -1) {
            return tryLockInnerSync(leaseTime, unit, threadId);
        }

        Long ttlRemaining = tryLockInnerSync(lockWatchdogTimeout, TimeUnit.MILLISECONDS, threadId);
        // lock acquired
        if (ttlRemaining == null) {
            log.debug("locked ok:{},threadId:{}", getLockName(), threadId);
            //schedule it
            // 线程不主动释放，无限递归等待释放，为了防止死锁，在客户端挂掉情况下，30秒内自动解锁
            scheduleExpirationRenewal(threadId);
        }
        return ttlRemaining;
    }

    private Long tryLockInnerSync(long leaseTime, TimeUnit unit, long threadId) {

        Long internalLockLeaseTime = unit.toMillis(leaseTime);

        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        StringBuffer script = new StringBuffer();

        //return nil 返回null 表示获得锁
        redisScript.setScriptText(script.append("if (redis.call('exists', KEYS[1]) == 0) then ")
                .append("redis.call('hset', KEYS[1], ARGV[2], 1); ")
                .append("redis.call('pexpire', KEYS[1], ARGV[1]); ")
                .append("return nil; ")
                .append("end; ")
                .append("if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then ")
                .append("redis.call('hincrby', KEYS[1], ARGV[2], 1); ")
                .append("redis.call('pexpire', KEYS[1], ARGV[1]); ")
                .append("return nil; ")
                .append("end; ")
                .append("return redis.call('pttl', KEYS[1]);").toString());

        redisScript.setResultType(Long.class);
        List<String> keys = Lists.newArrayList(getName());
        String[] args = new String[]{internalLockLeaseTime.toString(), getLockThreadName(threadId)};
        Long ttlRemaining = (Long) redisTemplate.execute(redisScript, defaultKeySerializer, defaultKeySerializer, keys, args);
        return ttlRemaining;
    }

    protected Long unlockInnerSync(long threadId) {
        log.debug("unlock threadId:{}", threadId);
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();

        //hincrby记录重入次数
        StringBuffer unlockScript = new StringBuffer();
        unlockScript.append("if (redis.call('exists', KEYS[1]) == 0) then ")
                .append("redis.call('publish', KEYS[2], ARGV[1]); ")
                .append("return 1; ")
                .append("end;")
                .append("if (redis.call('hexists', KEYS[1], ARGV[3]) == 0) then ")
                .append("return nil;")
                .append("end; ")
                .append("local counter = redis.call('hincrby', KEYS[1], ARGV[3], -1); ")
                .append("if (counter > 0) then ")
                .append("redis.call('pexpire', KEYS[1], ARGV[2]); ")
                .append("return 0; ")
                .append("else ")
                .append("redis.call('del', KEYS[1]); ")
                .append("redis.call('publish', KEYS[2], ARGV[1]); ")
                .append("return 1; ")
                .append("end; ")
                .append("return nil;");

        redisScript.setScriptText(unlockScript.toString());
        redisScript.setResultType(Long.class);
        List<String> keys = Lists.newArrayList(getName(), getChannelName());
        String[] args = new String[]{unlockMessage.toString(), lockWatchdogTimeout.toString(), getLockThreadName(threadId)};
        Long unlocked = (Long) redisTemplate.execute(redisScript, defaultKeySerializer, defaultKeySerializer, keys, args);
        return unlocked;

    }

    private void scheduleExpirationRenewal(final long threadId) {

        if (expirationRenewalMap.containsKey(getLockName())) {
            return;
        }
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                boolean pexpire = prolongExpire(threadId);
                if (pexpire) {
                    expirationRenewalMap.remove(getLockName());
                    log.debug("reschedule itself : {}", getLockName());
                    //if true  reschedule itself
                    //递归
                    scheduleExpirationRenewal(threadId);
                }
            }
        };
        timer.schedule(timerTask, lockWatchdogTimeout / 3);

        if (expirationRenewalMap.putIfAbsent(getLockName(), timerTask) != null) {
            timerTask.cancel();
        }

    }

    private void cancelExpirationRenewal() {
        log.debug("cancle renewal:{}", getLockName());
        TimerTask task = expirationRenewalMap.remove(getLockName());
        if (task != null) {
            task.cancel();
        }
    }

    private boolean prolongExpire(final long threadId) {

        StringBuffer expireationScript = new StringBuffer();
        expireationScript.append("if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then ")
                .append("redis.call('pexpire', KEYS[1], ARGV[1]); ")
                .append("return 1; ")
                .append("end; ")
                .append("return 0;");
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Boolean.class);
        redisScript.setScriptText(expireationScript.toString());

        List<String> keys = Lists.newArrayList(getName());

        String[] args = new String[]{lockWatchdogTimeout.toString(), getLockThreadName(threadId)};

        Boolean pexpire = (Boolean) redisTemplate.execute(redisScript, defaultKeySerializer, defaultKeySerializer, keys, args);

        return pexpire;

    }

    private String getLockThreadName(long threadId) {
        return getLockName() + "_" + threadId;
    }

    private String getLockName() {
        return id + ":" + getName();
    }

    private String getChannelName() {
        return "redis_lock_channel" + ":{" + name + "}";
    }

}
package com.acooly.module.distributedlock.zookeeper;

import com.acooly.module.distributedlock.exception.DistributedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Slf4j
public class ZkDistributedLock implements Lock, AutoCloseable {

    private InterProcessMutex interProcessMutex;
    private String lockPath;

    public ZkDistributedLock(InterProcessMutex interProcessMutex, String lockPath) {
        this.interProcessMutex = interProcessMutex;
        this.lockPath = lockPath;
    }

    @Override
    public void lock() {
        try {
            log.info("尝试获取分布式锁:{}", lockPath);
            interProcessMutex.acquire();
            log.info("获取分布式锁成功:{}", lockPath);
        } catch (Exception e) {
            throw new DistributedException("获取锁失败", e);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException("分布式锁不支持创建条件");
    }

    @Override
    public boolean tryLock() {
        try {
            boolean acquire = interProcessMutex.acquire(1, TimeUnit.NANOSECONDS);
            log.info("获取分布式锁:{},结果{}", lockPath, acquire);
            return acquire;
        } catch (Exception e) {
            throw new DistributedException("获取锁失败", e);
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        try {
            log.info("尝试获取分布式锁:{}", lockPath);
            boolean acquire = interProcessMutex.acquire(time, unit);
            log.info("获取分布式锁结果:{}", acquire);
            return acquire;
        } catch (Exception e) {
            throw new DistributedException("获取锁失败", e);
        }
    }

    @Override
    public void unlock() {
        try {
            interProcessMutex.release();
            log.info("分布式锁释放成功:{}", lockPath);
        } catch (Exception e) {
            throw new DistributedException("释放锁失败", e);
        }
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException("分布式锁不支持创建条件");
    }

    @Override
    public void close() {
        unlock();
    }
}

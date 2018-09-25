/*
 * www.prosysoft.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * shuijing 2018-07-10 11:23 创建
 */
package com.acooly.core.test.distributedlock;

import com.acooly.core.test.TestBase;
import com.acooly.module.distributedlock.DistributedLockFactory;
import com.acooly.module.distributedlock.DistributedLockProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import static com.acooly.core.common.boot.Apps.SPRING_PROFILE_ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author shuijing
 */
@Slf4j
public class RedisdtLockTest extends TestBase {

    static {
        System.setProperty(SPRING_PROFILE_ACTIVE, "qiubo");
    }

    @Autowired
    private DistributedLockFactory factory;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DistributedLockProperties properties;

    protected void runInThreadPool(int threads, Runnable runnable) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            pool.execute(() -> runnable.run());
        }
    }

    @Test
    public void testLock() {

        Lock lock = factory.newLock("lock4");
        lock.lock();
        try {
            // 执行一些需要加分布式锁的操作
        } finally {
            lock.unlock();
        }
    }

    @Test
    public void testTryLock() {
        //测试 trylock

        Lock lock = factory.newLock("lock5");

        if (lock.tryLock()) {
            try {
                //拿到锁，执行一些需要加分布式锁的操作
            } finally {
                lock.unlock();
            }
        } else {
            //执行另外操作
        }
    }

    @Test
    public void testTryLockInTime() throws InterruptedException {
        long startTime = System.currentTimeMillis();

        Thread t = new Thread(() -> {
            Lock lock = factory.newLock("lock6");
            lock.lock();
            try {
                Thread.sleep(2000);
                System.out.println("new thread id:" + Thread.currentThread().getId());
                lock.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t.start();

        Thread.sleep(100);

        Lock lock = factory.newLock("lock6");
        //在等待时间内得到锁
        if (lock.tryLock(3, TimeUnit.SECONDS)) {
            try {
                assertThat(System.currentTimeMillis() - startTime).isLessThan(3000L);
                //拿到锁，执行一些需要加分布式锁的操作
            } finally {
                lock.unlock();
            }
        } else {
            //执行另外操作
        }
    }

    @Test
    public void testLockUnlock() {
        //测试加锁 释放

        Lock lock = factory.newLock("lock7");

        lock.lock();
        lock.unlock();

        lock.lock();
        lock.unlock();
    }

    @Test
    public void testTryLockWait() throws InterruptedException {

        runInThreadPool(1, () -> {
                    Lock lock = factory.newLock("lock8");
                    lock.lock();
                }
        );

        System.out.println("等待两秒...");
        Thread.sleep(2000L);

        Lock lock = factory.newLock("lock8");

        long startTime = System.currentTimeMillis();
        lock.tryLock(3, TimeUnit.SECONDS);
        assertThat(System.currentTimeMillis() - startTime).isBetween(2990L, 3100L);
    }


    @Test
    public void testAutoExpire() throws InterruptedException {

        //测试自动延长过期时间

        Thread t = new Thread(() -> {
            Lock lock = factory.newLock("lock9");
            lock.lock();
            try {
                Thread.sleep(2000);
                System.out.println("new thread id:" + Thread.currentThread().getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t.start();

        t.join();
        //t.stop();
        assertThat(t.isAlive()).isFalse();
        System.out.println("main thread id:" + Thread.currentThread().getId());
        Boolean hasKey = redisTemplate.hasKey("lock9");
        assertThat(hasKey).isTrue();
        Thread.sleep(properties.getRedis().getLockWatchdogTimeout());
        Boolean hasKeyAfter = redisTemplate.hasKey("lock9");
        //自动延长过期时间 仍然locked
        assertThat(hasKeyAfter).isTrue();
    }

    @Test
    public void testIsLockedOtherThread() throws InterruptedException {
        //测试多个线程获取锁
        Lock lock = factory.newLock("lock11");
        lock.lock();

        Thread t = new Thread(() -> {
            Lock lock1 = factory.newLock("lock11");
            boolean isLocked = lock1.tryLock();
            //Boolean islocked = redisTemplate.hasKey("lock");
            //没拿到锁
            Assert.assertFalse(isLocked);
        });

        t.start();
        t.join();
        lock.unlock();

        Thread t2 = new Thread(() -> {
            Lock lock12 = factory.newLock("lock11");
            boolean isLocked = lock12.tryLock();
            //拿到锁
            Assert.assertTrue(isLocked);
        });

        t2.start();
        t2.join();
    }

    @Test
    public void testUnlockFail() throws InterruptedException {

        //测试只有加锁的线程才能解锁，其他线程不能解锁

        Thread t = new Thread(() -> {
            Lock lock1 = factory.newLock("lock10");
            lock1.lock();

            System.out.println("子线程id：" + Thread.currentThread().getId());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lock1.unlock();
        });

        t.start();
        t.join(400);
        System.out.println("主线程id：" + Thread.currentThread().getId());
        try {
            //不是当前线程释放锁，抛异常
            Lock lock = factory.newLock("lock10");
            lock.unlock();
        } catch (Exception e) {
            t.join();
            throw e;
        }
    }

    @Test
    public void testReentrancy() throws InterruptedException {
        //测试同一线程可重入
        Lock lock = factory.newLock("lock12");
        Assert.assertTrue(lock.tryLock());
        Assert.assertTrue(lock.tryLock());
        lock.unlock();

        Thread thread1 = new Thread(() -> {
            Lock lock1 = factory.newLock("lock12");
            Assert.assertFalse(lock1.tryLock());
        });
        thread1.start();
        thread1.join();
        //释放两次
        lock.unlock();
    }

    @Test
    public void testListener() throws InterruptedException {
        //测试unlock事件监听

        Lock lock = factory.newLock("lock13");
        Assert.assertTrue(lock.tryLock());

        Thread thread1 = new Thread(() -> {
            Lock lock1 = factory.newLock("lock13");
            lock1.lock();
        });
        thread1.start();

        Thread.sleep(20000L);
        lock.unlock();

        Thread.sleep(15000L);
        // thread1.join();
    }
}

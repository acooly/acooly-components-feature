/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2019-04-08 00:02
 */
package com.acooly.module.test.distributedlock;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.utils.Dates;
import com.acooly.module.distributedlock.DistributedLockFactory;
import com.acooly.module.test.AppTestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangpu
 * @date 2019-04-08 00:02
 */
@Slf4j
public class DistributedlockTest extends AppTestBase {

    static {
        Apps.setProfileIfNotExists("dlock");
    }

    @Autowired
    private DistributedLockFactory distributedLockFactory;

    private final int threads = 200;

    @Test
    public void testTrylock() throws Exception {
        final Lock lock = distributedLockFactory.newLock("dlock");
        LongAdder countSuccess = new LongAdder();
        long timeTasks = runTasks(threads, () -> {

            while (lock.tryLock()) {
                try {
                    countSuccess.increment();
                    log.info("dlock {} 获得锁成功：{}", Thread.currentThread().getName(), Dates.format(new Date(), "HH:mm:ss.SSS"));
                } finally {
                    lock.unlock();
                }
                break;
            }


        });
        log.info("dlock test. threads:{},success:{}, ms:{}", threads, countSuccess.intValue(), timeTasks);
    }

    @Test
    public void testTrylockByTimeout() throws Exception {
        final Lock lock = distributedLockFactory.newLock("dlock");
        LongAdder countSuccess = new LongAdder();
        long timeTasks = runTasks(threads, () -> {
            try {
                if (lock.tryLock(1, TimeUnit.SECONDS)) {
                    try {
                        countSuccess.increment();
                        log.info("dlock {} 获得锁成功：{}", Thread.currentThread().getName(), Dates.format(new Date(), "HH:mm:ss.SSS"));
                    } catch (Exception e) {
                        log.warn("dlock {} 执行异常", Thread.currentThread().getName());
                    } finally {
                        lock.unlock();
                    }
                } else {
                    log.info("dlock {} 未获得锁", Thread.currentThread().getName());
                }
            } catch (Exception e) {
                log.error("dlock trylock error", e);
            }

        });
        log.info("dlock test. threads:{},success:{}, ms:{}", threads, countSuccess.intValue(), timeTasks);
    }

    @Test
    public void testlock() throws Exception {

        final Lock lock = distributedLockFactory.newLock("dlock");
        LongAdder countSuccess = new LongAdder();
        long timeTasks = runTasks(threads, () -> {
            try {
                lock.lock();
                countSuccess.increment();
                log.info("dlock {} 获得锁成功：{}", Thread.currentThread().getName(), System.currentTimeMillis());
            } finally {
                lock.unlock();
            }
        });
        log.info("dlock test. threads:{},success:{}, ms:{}", threads, countSuccess.intValue(), timeTasks);
    }


    /**
     * 指定多个线程同时运行一个任务，测试并发性
     *
     * @param nThreads
     * @param task
     * @return
     * @throws InterruptedException
     */
    public long runTasks(int nThreads, final Runnable task) throws InterruptedException {
        // 真正的测试
        // 使用同步工具类，保证多个线程同时（近似同时）执行
        final CountDownLatch startGate = new CountDownLatch(1);
        // 使用同步工具类，用于等待所有线程都运行结束时，再统计耗时
        final CountDownLatch endGate = new CountDownLatch(nThreads);
        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        }
        long start = System.currentTimeMillis();
        log.info("runTasks start:{},ready-threads:{}", start, nThreads);
        startGate.countDown();
        endGate.await();
        long end = System.currentTimeMillis();
        log.info("runTasks end:{},ready-threads:{}", end, nThreads);
        return end - start;
    }
}

/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * kuli@yiji.com 2017-06-17 17:51 创建
 */
package com.acooly.core.test.utils;

import com.acooly.core.utils.Ids;
import com.acooly.core.utils.id.NetAddressIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * @author acooly
 */
@Slf4j
public class IdsTest {


    @Test
    public void testIds() throws Exception {
        final Set<String> container = Collections.synchronizedSet(new HashSet<>());

        int times = 999;
        int threads = 100;
        CountDownLatch latch = new CountDownLatch(threads);
        for (int i = 0; i < threads; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程:" + Thread.currentThread().getName() + " 开始");
                    String id = null;
                    for (int j = 0; j < times; j++) {
                        id = Ids.getDid();
                        if (!container.add(id)) {
                            System.out.println("----- 重复数据：----- " + id);
                        }
                    }
                    latch.countDown();
                    System.out.println("线程:" + Thread.currentThread().getName() + " 结束");
                }
            });
            t.setName("test_" + i);
            t.start();
        }
        latch.await();
        System.out.println("test get count:" + times * threads);
        System.out.println("test saved count:" + container.size());
    }

    @Test
    public void name() throws Exception {

        NetAddressIdWorker netAddressIdWorker = new NetAddressIdWorker();
        Long generate = netAddressIdWorker.generate();
        System.out.println(generate + ":" + Long.toString(generate).toString().length());
    }
}

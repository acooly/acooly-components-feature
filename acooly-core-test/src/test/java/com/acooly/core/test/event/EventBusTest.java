/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-03-10 16:10 创建
 */
package com.acooly.core.test.event;

import com.acooly.core.test.TestBase;
import com.acooly.module.event.EventBus;
import org.junit.AfterClass;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author qiubo@yiji.com
 */
public class EventBusTest extends TestBase {
    @Resource
    private EventBus eventBus;

    @AfterClass
    public static void testW() throws Exception {
        System.out.println("a");
    }

    //立即发布事件
    @Test
    public void testPublish() throws Exception {
        CreateCustomerEvent event = new CreateCustomerEvent();
        event.setId(1l);
        event.setUserName("dfd");
        eventBus.publish(event);
    }

    //仅当当前事务提交成功后才发布消息,非事务环境直接发布消息
    @Test
    public void testPublishAfterTransactionCommitted() throws Exception {
        CreateCustomerEvent event = new CreateCustomerEvent();
        event.setId(2l);
        event.setUserName("dfd");
        eventBus.publishAfterTransactionCommitted(event);
    }
}

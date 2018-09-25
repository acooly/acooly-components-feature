/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-03-10 16:11 创建
 */
package com.acooly.core.test.event;

import com.acooly.module.event.EventHandler;
import lombok.extern.slf4j.Slf4j;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Invoke;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author qiubo@yiji.com
 */
@EventHandler
@Slf4j
public class CustomerEventHandler {
    // 同步事件处理器
    @Handler
    public void handleCreateCustomerEvent(CreateCustomerEvent event) {
        log.info("{}", event);
        // do what you like
    }

    // 异步事件处理器
    @Transactional
    @Handler(delivery = Invoke.Asynchronously)
    public void handleCreateCustomerEventAsyn(CreateCustomerEvent event) {
        log.info("{}", event);
        // do what you like
    }
}

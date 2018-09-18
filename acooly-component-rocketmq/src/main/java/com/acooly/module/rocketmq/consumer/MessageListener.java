package com.acooly.module.rocketmq.consumer;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * @author qiubo
 */
public interface MessageListener {
    void onMessage(MessageExt messages, ConsumeConcurrentlyContext context);

    String getTopic();

    String getTags();

    MethodWrapper getMethodWrapper();
}

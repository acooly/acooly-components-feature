package com.acooly.module.rocketmq.producer;

import com.acooly.module.rocketmq.dto.MessageDto;
import com.alibaba.rocketmq.client.producer.SendResult;

/**
 * @author qiubo
 */
public interface MessageProducer {
    SendResult send(MessageDto messageDto);
}

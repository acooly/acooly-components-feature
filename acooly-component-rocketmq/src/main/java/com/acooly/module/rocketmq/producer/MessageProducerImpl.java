package com.acooly.module.rocketmq.producer;

import com.acooly.core.common.exception.AppConfigException;
import com.acooly.core.utils.ToString;
import com.acooly.module.rocketmq.converter.MessageConverter;
import com.acooly.module.rocketmq.dto.MessageDto;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.MQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author qiubo
 */

public class MessageProducerImpl implements MessageProducer {

    private static final Logger logger = LoggerFactory.getLogger(MessageProducerImpl.class);
    private MQProducer defaultMQProducer;
    private MessageConverter messageConverter;

    @Autowired
    private ThreadPoolTaskExecutor commonTaskExecutor;

    public MessageProducerImpl(MQProducer defaultMQProducer, MessageConverter messageConverter) {
        this.defaultMQProducer = defaultMQProducer;
        this.messageConverter = messageConverter;
    }

    public SendResult send(MessageDto messageDto) {
        Assert.notNull(messageDto, "消息发送对象不能为空");
        messageDto.check();
        Message message = messageConverter.to(messageDto);
        SendResult result;
        logger.info("发送消息:{}, 大小:{}", ToString.toString(messageDto), message.getBody().length);
        result = sendDirect(message);
        logger.info("消息发送结果：mgsId={},status={}", result.getMsgId(), result.getSendStatus());
        return result;
    }

    private SendResult sendDirect(Message message) {
        SendResult result;
        try {
            result = defaultMQProducer.send(message);
        } catch (Exception e) {
            throw new AppConfigException("发送消息失败", e);
        }
        return result;
    }

    @PostConstruct
    public void start() throws MQClientException {
        logger.info("启动RocketMQ Producer");
        defaultMQProducer.start();
    }

    @PreDestroy
    public void stop() {
        logger.info("关闭RocketMQ Producer");
        defaultMQProducer.shutdown();
    }
}

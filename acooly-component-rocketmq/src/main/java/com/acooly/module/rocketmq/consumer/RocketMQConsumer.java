package com.acooly.module.rocketmq.consumer;

import com.acooly.core.common.exception.AppConfigException;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author qiubo
 */
public class RocketMQConsumer {
    private static final Logger logger = LoggerFactory.getLogger(RocketMQConsumer.class);
    private DefaultMQPushConsumer defaultMQPushConsumer;
    private String groupName;
    private String nameAddr;
    private int consumeThreadMin = 5;
    private int consumeThreadMax = 10;
    private Map<String, MessageListener> listenerMap = new ConcurrentHashMap();

    public RocketMQConsumer(
            String groupName, String nameAddr, int consumeThreadMin, int consumeThreadMax)
            throws MQClientException {
        this.groupName = groupName;
        this.nameAddr = nameAddr;
        this.consumeThreadMin = consumeThreadMin;
        this.consumeThreadMax = consumeThreadMax;
        //普通消费端指定组名加common后缀
        defaultMQPushConsumer = new DefaultMQPushConsumer(this.groupName + "_common");
        defaultMQPushConsumer.setNamesrvAddr(this.nameAddr);
        defaultMQPushConsumer.setConsumeThreadMin(this.consumeThreadMin);
        defaultMQPushConsumer.setConsumeThreadMax(this.consumeThreadMax);
        //设置一个新的订阅组第一次启动从队列的最前位置开始消费, 后续再启动接着上次消费的进度开始消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        logger.info(
                "构建RocketMQConsumer,groupName={}, nameAddr={},consumeThreadMin={},consumeThreadMax={}",
                groupName,
                nameAddr,
                consumeThreadMin,
                consumeThreadMax);
    }

    public void addTopicListener(MessageListener messageListener) {
        if (listenerMap.get(messageListener.getTopic()) != null) {
            throw new AppConfigException(
                    "同一个topic不请允许存在多个消息RocketListener，请检查配置,topic=" + messageListener.getTopic() + "重复");
        }
        listenerMap.put(messageListener.getTopic(), messageListener);
    }

    public void start() {
        try {
            startDefaultMQPushConsumer();
        } catch (MQClientException e) {
            throw new AppConfigException("RocketMQ消费端启动失败", e);
        }
    }

    protected void startDefaultMQPushConsumer() throws MQClientException {
        if (defaultMQPushConsumer != null) {
            if (listenerMap.size() != 0) {
                registerSubscribes(defaultMQPushConsumer, listenerMap);
                defaultMQPushConsumer.registerMessageListener(
                        (MessageListenerConcurrently)
                                (msgs, context) -> {
                                    try {
                                        invokerListener(context, msgs, listenerMap);
                                    } catch (Exception e) {
                                        logger.error("调用消息监听方法出错", e);
                                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                                    }
                                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                                });
                defaultMQPushConsumer.start();
            }
        }
    }

    protected void registerSubscribes(
            DefaultMQPushConsumer mqPushConsumer, Map<String, MessageListener> listenerMap)
            throws MQClientException {
        for (Map.Entry<String, MessageListener> entry : listenerMap.entrySet()) {
            mqPushConsumer.subscribe(entry.getKey(), entry.getValue().getTags());
        }
    }

    protected <T> void invokerListener(
            T context, List<MessageExt> msgs, Map<String, MessageListener> listeners) throws Exception {
        for (MessageExt messageExt : msgs) {
            MessageListener listener = listeners.get(messageExt.getTopic());
            if (listener != null) {
                listener.onMessage(messageExt, (ConsumeConcurrentlyContext) context);
            } else {
                throw new IllegalArgumentException("没有找到主题" + messageExt.getTopic() + "对应的TopicListener");
            }
        }
    }

    public void stop() {
        if (defaultMQPushConsumer != null) {
            defaultMQPushConsumer.shutdown();
        }
    }

    public static class RocketMQConsumerContextRefreshedEventListener
            implements ApplicationListener<ContextRefreshedEvent> {

        private RocketMQConsumer consumer;

        public RocketMQConsumerContextRefreshedEventListener(RocketMQConsumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public void onApplicationEvent(ContextRefreshedEvent event) {
            logger.info("容器初始化完毕，开始启动RocketMQConsumer");
            this.consumer.start();
        }
    }

    public static class RocketMQConsumerContextClosedEventListener
            implements ApplicationListener<ContextClosedEvent> {

        private RocketMQConsumer consumer;

        public RocketMQConsumerContextClosedEventListener(RocketMQConsumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public void onApplicationEvent(ContextClosedEvent event) {
            logger.info("容器将要关闭，开始关闭RocketMQConsumer");
            this.consumer.stop();
        }
    }
}

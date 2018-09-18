package com.acooly.module.rocketmq;

import com.acooly.module.rocketmq.consumer.RocketListenerAnnotationBeanPostProcessor;
import com.acooly.module.rocketmq.consumer.RocketMQConsumer;
import com.acooly.module.rocketmq.converter.KyroMessageConverter;
import com.acooly.module.rocketmq.producer.MessageProducer;
import com.acooly.module.rocketmq.producer.MessageProducerImpl;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qiubo
 */
@Configuration
@ConditionalOnProperty(value = "acooly.rocketmq.enable", matchIfMissing = true)
@EnableConfigurationProperties({RocketMQProperties.class})
public class RocketMQAutoConfig {
    @Configuration
    @ConditionalOnProperty(value = "acooly.rocketmq.producer.enable")
    public static class RocketProducerConfig {
        @Bean
        public DefaultMQProducer defaultMQProducer(RocketMQProperties rocketMQProperties)
                throws MQClientException {
            DefaultMQProducer defaultMQProducer =
                    new DefaultMQProducer(rocketMQProperties.getProducer().getGroup());
            defaultMQProducer.setNamesrvAddr(rocketMQProperties.getNameSrvAddr());
            defaultMQProducer.setRetryAnotherBrokerWhenNotStoreOK(true);
            return defaultMQProducer;
        }

        @Bean
        public MessageProducer messageProducer(DefaultMQProducer defaultMQProducer) {
            MessageProducerImpl messageProducer =
                    new MessageProducerImpl(defaultMQProducer, new KyroMessageConverter());
            return messageProducer;
        }
    }

    @Configuration
    @ConditionalOnProperty(value = "acooly.rocketmq.consumer.enable")
    public static class RocketConsumerConfig {
        @Bean
        public static RocketListenerAnnotationBeanPostProcessor
        rocketListenerAnnotationBeanPostProcessor() {
            return new RocketListenerAnnotationBeanPostProcessor();
        }

        @Bean
        public RocketMQConsumer rocketMQConsumer(RocketMQProperties rocketMQProperties)
                throws MQClientException {
            RocketMQConsumer rocketMQConsumer =
                    new RocketMQConsumer(
                            rocketMQProperties.getConsumer().getGroup(),
                            rocketMQProperties.getNameSrvAddr(),
                            rocketMQProperties.getConsumer().getConsumeThreadMin(),
                            rocketMQProperties.getConsumer().getConsumeThreadMax());
            return rocketMQConsumer;
        }

        @Bean
        public RocketMQConsumer.RocketMQConsumerContextRefreshedEventListener
        rocketMQConsumerContextRefreshedEventListener(RocketMQConsumer rocketMQConsumer)
                throws MQClientException {
            RocketMQConsumer.RocketMQConsumerContextRefreshedEventListener
                    rocketMQConsumerContextRefreshedEventListener =
                    new RocketMQConsumer.RocketMQConsumerContextRefreshedEventListener(rocketMQConsumer);
            return rocketMQConsumerContextRefreshedEventListener;
        }

        @Bean
        public RocketMQConsumer.RocketMQConsumerContextClosedEventListener
        rocketMQConsumerContextClosedEventListener(RocketMQConsumer rocketMQConsumer)
                throws MQClientException {
            RocketMQConsumer.RocketMQConsumerContextClosedEventListener
                    rocketMQConsumerContextClosedEventListener =
                    new RocketMQConsumer.RocketMQConsumerContextClosedEventListener(rocketMQConsumer);
            return rocketMQConsumerContextClosedEventListener;
        }
    }
}

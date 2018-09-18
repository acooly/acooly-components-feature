package com.acooly.module.rocketmq.consumer;

import com.acooly.core.common.boot.Apps;
import com.acooly.module.rocketmq.converter.KyroMessageConverter;
import com.acooly.module.rocketmq.dto.MessageDto;
import com.alibaba.rocketmq.client.exception.MQClientException;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author qiubo
 */
public class RocketListenerAnnotationBeanPostProcessor
        implements BeanPostProcessor, Ordered, BeanFactoryAware {
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        if (!isMatchPackage(targetClass)) {
            return bean;
        }
        ReflectionUtils.doWithMethods(
                targetClass,
                (Method method) -> {
                    RocketListener rocketListener =
                            AnnotationUtils.getAnnotation(method, RocketListener.class);
                    if (rocketListener != null) {
                        try {
                            processRocketListener(rocketListener, method, bean, beanName);
                        } catch (MQClientException e) {
                            throw new BeanInitializationException(e.getMessage(), e);
                        }
                    }
                });
        return bean;
    }

    private boolean isMatchPackage(Class clazz) {
        return clazz.getName().startsWith(Apps.getBasePackage());
    }

    protected void processRocketListener(
            RocketListener rocketListener, Method method, Object bean, String beanName)
            throws MQClientException {
        checkMethodParameter(method, rocketListener);
        RocketMQConsumer rocketMQConsumer = this.beanFactory.getBean(RocketMQConsumer.class);
        MessageListener topicMessageListener = generateTopicListener(rocketListener, method, bean);
        rocketMQConsumer.addTopicListener(topicMessageListener);
    }

    protected void checkMethodParameter(Method method, RocketListener rocketListener) {
        if (method.getParameterCount() == 1) {
            if (!method.getParameterTypes()[0].equals(MessageDto.class)) {
                throw new BeanInitializationException("消息监听方法，只能有一个参数，参数类型为参数类型为MessageDto");
            }
        } else {
            throw new BeanInitializationException("消息监听方法，只能有一个参数，参数类型为MessageDto");
        }
    }

    protected TopicMessageListener generateTopicListener(
            RocketListener rocketListener, Method method, Object bean) {
        TopicMessageListener topicMessageListener = new TopicMessageListener();
        topicMessageListener.setTopic(rocketListener.topic());
        MethodWrapper methodWrapper = new MethodWrapper(bean, method);
        topicMessageListener.setMethodWrapper(methodWrapper);
        topicMessageListener.setMessageConverter(new KyroMessageConverter());
        topicMessageListener.setTags(rocketListener.tags());
        return topicMessageListener;
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}

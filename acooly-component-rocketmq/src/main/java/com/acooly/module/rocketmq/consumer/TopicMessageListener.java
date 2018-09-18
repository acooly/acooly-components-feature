package com.acooly.module.rocketmq.consumer;

import com.acooly.core.common.exception.AppConfigException;
import com.acooly.core.utils.ToString;
import com.acooly.module.rocketmq.converter.MessageConverter;
import com.acooly.module.rocketmq.dto.MessageDto;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.lang.reflect.InvocationTargetException;

/**
 * @author qiubo
 */
public class TopicMessageListener implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(TopicMessageListener.class);

    private String topic;
    private String tags;

    private MethodWrapper methodWrapper;
    private MessageConverter messageConverter;

    public void onMessage(MessageExt message, ConsumeConcurrentlyContext context) {
        handle(messageConverter.from(message), context.getMessageQueue().getTopic());
    }

    private void handle(MessageDto message, String topic) {
        MDC.put("gid", message.getGid());
        try {
            logger.info("收到消息:{}", ToString.toString(message));
            Object[] args = {message};
            methodWrapper.getMethod().invoke(methodWrapper.getBean(), args);
        } catch (Throwable ex) {
            if (ex instanceof InvocationTargetException) {
                ex = ((InvocationTargetException) ex).getTargetException();
            }
            logger.error("消息处理异常", ex);
            throw new AppConfigException(ex);
        } finally {
            logger.info("消息处理完成");
            MDC.clear();
        }
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    public String toString() {
        return "TopicMessageListener: " + "(" + topic + ")" + "(" + methodWrapper.toString() + ")";
    }

    @Override
    public MethodWrapper getMethodWrapper() {
        return methodWrapper;
    }

    public void setMethodWrapper(MethodWrapper methodWrapper) {
        this.methodWrapper = methodWrapper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TopicMessageListener)) return false;

        TopicMessageListener that = (TopicMessageListener) o;

        if (topic != null ? !topic.equals(that.topic) : that.topic != null) return false;
        return tags != null ? tags.equals(that.tags) : that.tags == null;
    }

    @Override
    public int hashCode() {
        int result = topic != null ? topic.hashCode() : 0;
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }
}

package com.acooly.module.rocketmq;

import com.acooly.core.common.boot.Apps;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.acooly.module.rocketmq.RocketMQProperties.PREFIX;

/**
 * @author qiubo
 */
@Data
@ConfigurationProperties(PREFIX)
public class RocketMQProperties {
    public static final String PREFIX = "acooly.rocketmq";

    private boolean enable = true;

    private String nameSrvAddr;

    private Producer producer;

    private Consumer consumer;

    @Data
    public static class Producer {

        private static final String PROVIDER_PREFIX = "Provider_";
        /**
         * 是否启用生产端，默认为不启用
         */
        private boolean enable = false;
        /**
         * 生产端的group名字，默认为: PROVIDER_PREFIX + Apps.getAppName()
         */
        private String group = PROVIDER_PREFIX + Apps.getAppName();
    }

    @Data
    public static class Consumer {

        private static final String CONSUMER_PREFIX = "Consumers_";
        /**
         * 消费端的group名字，默认为: CONSUMER_PREFIX + Apps.getAppName()
         */
        private String group = CONSUMER_PREFIX + Apps.getAppName();
        /**
         * 是否启用消费端，默认为不启用
         */
        private boolean enable = false;
        /**
         * 每次从服务端获取多少条消费到本地消费，不建议修改
         *
         * <p>如果值大于1,每个批量中有一条处理错误,会导致整个批量重发
         */
        private int consumeMessageBatchMaxSize = 1;
        /**
         * 消费端线程池最小为多少
         */
        private int consumeThreadMin = 10;
        /**
         * 消费端线程池最大为多少
         */
        private int consumeThreadMax = 50;
    }
}

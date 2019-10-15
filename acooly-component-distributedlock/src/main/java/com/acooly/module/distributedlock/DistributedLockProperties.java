package com.acooly.module.distributedlock;

import com.acooly.core.utils.Assert;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * @author shuijing
 */
@ConfigurationProperties(DistributedLockProperties.PREFIX)
@Data
@Validated
public class DistributedLockProperties implements InitializingBean {

    public static final String PREFIX = "acooly.distributedlock";

    private boolean enable = true;

    /**
     * 默认使用redis模式
     */
    private Mode mode = Mode.REDIS;

    private Zookeeper zookeeper = new Zookeeper();
    private Redis redis = new Redis();

    @Override
    public void afterPropertiesSet() throws Exception {
        if(mode == Mode.ZOOKEEPER){
            Assert.hasText(zookeeper.url,"zookeeper模式下 acooly.distributedlock.zookeeper.url 属性必须配置");
        }
    }

    @Data
    public static class Redis {
        /**
         * redis分布式锁，客户端宕机，自动解锁超时时间，默认30秒
         */
        private Long lockWatchdogTimeout = 30 * 1000L;
    }

    @Data
    public static class Zookeeper {

        /**
         * zkUrl
         */
        @NotBlank
        private String url;

        /**
         * zk连接故障时重试次数
         */
        private int retryTimes = 1000;
        /**
         * zk session timeout ，单位ms
         */
        private int sessionTimeoutMs = 30000;
        /**
         * zk connection timeout ，单位ms
         */
        private int connectionTimeoutMs = 10000;

        /**
         * zk连接异常时，重试时间间隔
         */
        private int sleepMsBetweenRetries = 1000;
    }


    public enum Mode {
        /**
         * redis 实现的分布式锁
         */
        REDIS,

        /**
         * zookeeper InterProcessMutex分布式锁
         */
        ZOOKEEPER;
    }


}

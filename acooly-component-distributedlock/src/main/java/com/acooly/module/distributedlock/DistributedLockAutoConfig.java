package com.acooly.module.distributedlock;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.acooly.module.distributedlock.DistributedLockProperties.PREFIX;

/**
 * @author shuijing
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({DistributedLockProperties.class})
@ConditionalOnProperty(value = PREFIX + ".enable", matchIfMissing = true)
public class DistributedLockAutoConfig {

    @Autowired
    private DistributedLockProperties distributedLockProperties;


    @Bean
    @ConditionalOnProperty(
            name = {PREFIX + ".mode"},
            havingValue = "zookeeper"
    )
    public CuratorFramework curatorFramework(DistributedLockProperties lockProperties) {
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString(distributedLockProperties.getZookeeper().getUrl())
                .retryPolicy(
                        new RetryNTimes(lockProperties.getZookeeper().getRetryTimes(), lockProperties.getZookeeper().getSleepMsBetweenRetries()))
                .connectionTimeoutMs(lockProperties.getZookeeper().getConnectionTimeoutMs())
                .sessionTimeoutMs(lockProperties.getZookeeper().getSessionTimeoutMs());
        CuratorFramework curatorFramework = builder.build();
        curatorFramework.start();
        return curatorFramework;
    }

    @Bean
    public DistributedLockFactory distributedLockFactory(DistributedLockProperties lockProperties) {
        return new DistributedLockFactory(lockProperties);
    }

}

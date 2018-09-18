package com.acooly.module.scheduler;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.acooly.module.scheduler.SchedulerProperties.PREFIX;

/**
 * @author shuijing
 */
@Data
@Slf4j
@ConfigurationProperties(prefix = PREFIX)
public class SchedulerProperties {
    public static final String PREFIX = "acooly.scheduler";

    public boolean enable;

    /**
     * quartz执行线程数
     */
    private int threadCount = 10;

    /**
     * 集群检查时间间隔 ms
     */
    private int clusterCheckinInterval = 20000;
}

package com.acooly.module.scheduler;

import com.acooly.module.scheduler.executor.TaskStatusEnum;
import com.acooly.module.scheduler.executor.TaskTypeEnum;
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

    public boolean enable = true;

    /**
     * 是否开启job，用于pre环境不执行调度任务控制
     */
    public boolean enablejob = true;


    /**
     * quartz执行线程数
     */
    private int threadCount = 10;

    /**
     * 集群检查时间间隔 ms
     */
    private int clusterCheckinInterval = 20000;


    @Data
    public static class Rule {
        /**
         * 规则名称
         */
        private String name;

        /**
         * 任务类型：HTTP,LOCAL,DUBBO
         */
        private TaskTypeEnum type = TaskTypeEnum.LOCAL_TASK;

        /**
         * 本地任务（LOCAL）: 类全路径（spring容器通过类类型获取实例，请保证唯一实现）
         */
        private String localClassName;

        private String localMethodName;

        private String dubboGroup;

        private String dubboVersion;

        private String dubboParams;

        private String httpUrl;

        private String cron;

        /**
         * 归属（系统模块或开发人员）
         */
        private String owner;

        /**
         * 任务状态：NORMAL,CANCELED
         */
        private TaskStatusEnum status = TaskStatusEnum.NORMAL;


    }

}

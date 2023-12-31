/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-07-29 16:03
 */
package com.acooly.module.smsend.analysis;

import com.acooly.module.distributedlock.DistributedLockFactory;
import com.acooly.module.smsend.analysis.service.SmsSendDayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.util.concurrent.locks.Lock;

/**
 * 短信发送分析 调度
 *
 * @author zhangpu
 * @date 2020-07-29 16:03
 */
@Slf4j
public class SmsSendAnalysisScheduleManager implements SchedulingConfigurer {
    @Autowired
    private SmsSendDayService smsSendDayService;
    @Autowired
    private DistributedLockFactory lockFactory;
    @Autowired
    private SmsSendAnalysisProperties smsSendAnalysisProperties;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(() -> {
                    // 分布式锁控制多节点同时的触发，如果多节点时间不同步，daySummery内部控制去重复执行机制
                    Lock lock = lockFactory.newLock("SmsSendAnalysisSchedule");
                    if (lock.tryLock()) {
                        try {
                            log.info("短信发送分析 调度启动... ");
                            smsSendDayService.yesterdaySummary();
                            log.info("短信发送分析 调度执行完成. ");
                        } catch (Exception e) {
                            log.warn("短信发送分析 调度执行失败. {}", e.getMessage());
                        } finally {
                            lock.unlock();
                        }
                    }
                },
                triggerContext -> {
                    return new CronTrigger(smsSendAnalysisProperties.getSummaryCron()).nextExecutionTime(triggerContext);
                });
    }
}

package com.acooly.module.scheduler.engine;

import com.acooly.module.scheduler.domain.SchedulerRule;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author shuijing
 */
public interface ScheduleEngine extends ApplicationListener<ContextRefreshedEvent>, DisposableBean {

    Long addJobToEngine(SchedulerRule rule);

    void update(SchedulerRule rule);

    void deleteJob(SchedulerRule ruleDO);
}

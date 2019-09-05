package com.acooly.module.scheduler.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.scheduler.domain.SchedulerRule;

public interface SchedulerRuleService extends EntityService<SchedulerRule> {

    /**
     * 唯一本地任务
     *
     * @param className
     * @param methodName
     * @return
     */
    SchedulerRule getUniqueLocal(String className, String methodName);

    /**
     * 唯一Dubbo任务
     *
     * @param group
     * @param version
     * @return
     */
    SchedulerRule getUniqueDubbo(String group, String version);

    /**
     * 唯一http任务
     *
     * @param url
     * @return
     */
    SchedulerRule getUniqueHttp(String url);

}

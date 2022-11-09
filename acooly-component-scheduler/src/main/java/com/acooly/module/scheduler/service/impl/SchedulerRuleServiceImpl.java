package com.acooly.module.scheduler.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Collections3;
import com.acooly.module.scheduler.dao.SchedulerRuleDao;
import com.acooly.module.scheduler.domain.SchedulerRule;
import com.acooly.module.scheduler.executor.TaskTypeEnum;
import com.acooly.module.scheduler.service.SchedulerRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("schedulerRuleService")
public class SchedulerRuleServiceImpl extends EntityServiceImpl<SchedulerRule, SchedulerRuleDao>
        implements SchedulerRuleService {


    @Override
    public SchedulerRule getUniqueLocal(String className, String methodName) {
        return getFirst(getEntityDao().findByActionTypeAndClassNameAndMethodName(TaskTypeEnum.LOCAL_TASK.getCode(), className, methodName));
    }

    @Override
    public SchedulerRule getUniqueDubbo(String group, String version) {
        return getFirst(getEntityDao().findByActionTypeAndDubboGroupAndDubboVersion(TaskTypeEnum.DUBBO_TASK.getCode(), group, version));
    }

    @Override
    public SchedulerRule getUniqueHttp(String url) {
        return getFirst(getEntityDao().findByActionTypeAndProperties(TaskTypeEnum.HTTP_TASK.getCode(), url));
    }


    protected SchedulerRule getFirst(List<SchedulerRule> schedulerRules) {
        return Collections3.getFirst(schedulerRules);
    }
}

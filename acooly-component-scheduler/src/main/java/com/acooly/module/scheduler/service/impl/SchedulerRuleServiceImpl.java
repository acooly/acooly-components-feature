package com.acooly.module.scheduler.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.scheduler.dao.SchedulerRuleDao;
import com.acooly.module.scheduler.domain.SchedulerRule;
import com.acooly.module.scheduler.service.SchedulerRuleService;
import org.springframework.stereotype.Service;

@Service("schedulerRuleService")
public class SchedulerRuleServiceImpl extends EntityServiceImpl<SchedulerRule, SchedulerRuleDao>
        implements SchedulerRuleService {
}

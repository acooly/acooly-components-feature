/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2019-03-30 14:39
 */
package com.acooly.module.scheduler.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.exception.CommonErrorCodes;
import com.acooly.core.utils.Assert;
import com.acooly.core.utils.mapper.BeanCopier;
import com.acooly.module.scheduler.domain.SchedulerRule;
import com.acooly.module.scheduler.dto.SchedulerDto;
import com.acooly.module.scheduler.engine.ScheduleEngine;
import com.acooly.module.scheduler.executor.TaskStatusEnum;
import com.acooly.module.scheduler.executor.TaskTypeEnum;
import com.acooly.module.scheduler.service.SchedulerRuleService;
import com.acooly.module.scheduler.service.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangpu
 * @date 2019-03-30 14:39
 */
@Slf4j
@Component
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    private SchedulerRuleService schedulerRuleService;

    @Autowired
    private ScheduleEngine scheduleEngine;

    @Override
    public SchedulerRule save(SchedulerDto schedulerDto) {
        try {
            Assert.hasLength(schedulerDto.getActionType());
            TaskTypeEnum taskType = TaskTypeEnum.getEnumByCode(schedulerDto.getActionType());
            SchedulerRule schedulerRule = null;
            if (taskType == TaskTypeEnum.LOCAL_TASK) {
                schedulerRule = schedulerRuleService.getUniqueLocal(schedulerDto.getClassName(), schedulerDto.getMethodName());
            } else if (taskType == TaskTypeEnum.DUBBO_TASK) {
                schedulerRule = schedulerRuleService.getUniqueDubbo(schedulerDto.getDGroup(), schedulerDto.getDVersion());
            } else if (taskType == TaskTypeEnum.HTTP_TASK) {
                schedulerRule = schedulerRuleService.getUniqueHttp(schedulerDto.getProperties());
            }

            if (schedulerRule == null) {
                // 新增
                schedulerRule = BeanCopier.copy(schedulerDto, SchedulerRule.class);
                scheduleEngine.validateRule(schedulerRule);
                scheduleEngine.addJobToEngine(schedulerRule);
            } else {
                // 更新或取消
                BeanCopier.copy(schedulerDto, schedulerRule);
                scheduleEngine.validateRule(schedulerRule);
                //开启 就更新quartz
                if (schedulerRule.getStatus().equals(TaskStatusEnum.NORMAL.getCode())) {
                    //更新调度
                    scheduleEngine.update(schedulerRule);
                }
                //关闭 就从quartz中删除
                if (schedulerRule.getStatus().equals(TaskStatusEnum.CANCELED.getCode())) {
                    scheduleEngine.deleteJob(schedulerRule);
                }
            }
            schedulerRuleService.saveOrUpdate(schedulerRule);
            return schedulerRule;
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            throw new BusinessException(CommonErrorCodes.INTERNAL_ERROR, e.getMessage());
        }

    }
}

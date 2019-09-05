/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2019-03-30 14:30
 */
package com.acooly.module.scheduler.service;

import com.acooly.module.scheduler.domain.SchedulerRule;
import com.acooly.module.scheduler.dto.SchedulerDto;

/**
 * @author zhangpu
 * @date 2019-03-30 14:30
 */
public interface SchedulerService {


    SchedulerRule save(SchedulerDto schedulerDto);

}

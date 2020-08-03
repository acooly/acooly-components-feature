/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by zhangpu
 * date:2020-07-26
 *
 */
package com.acooly.module.smsend.analysis.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.smsend.analysis.dto.SmsSendPeriod;
import com.acooly.module.smsend.analysis.entity.SmsSendDay;
import com.acooly.module.smsend.analysis.enums.DateUnit;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 发送统计 Service接口
 *
 * @author zhangpu
 * @date 2020-07-26 14:13:43
 */
public interface SmsSendDayService extends EntityService<SmsSendDay> {

    /**
     * 日汇总
     *
     * @param day
     */
    void daySummary(Date day);

    default void daySummary() {
        daySummary(new Date());
    }

    /**
     * 分类统计
     *
     * @param start
     * @param end
     * @return
     */
    List<SmsSendPeriod> querySendGroup(@NotNull DateUnit dateUnit, Date start, Date end);

}

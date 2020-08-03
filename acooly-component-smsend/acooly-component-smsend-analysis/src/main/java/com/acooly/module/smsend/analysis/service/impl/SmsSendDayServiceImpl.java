/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by zhangpu
 * date:2020-07-26
 */
package com.acooly.module.smsend.analysis.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.exception.CommonErrorCodes;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Dates;
import com.acooly.module.smsend.analysis.dao.SmsSendDayDao;
import com.acooly.module.smsend.analysis.dto.SmsSendPeriod;
import com.acooly.module.smsend.analysis.entity.SmsSendDay;
import com.acooly.module.smsend.analysis.enums.DateUnit;
import com.acooly.module.smsend.analysis.service.SmsSendDayService;
import com.acooly.module.smsend.manage.SmsSendLogService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 发送统计 Service实现
 *
 * @author zhangpu
 * @date 2020-07-26 14:13:43
 */
@Slf4j
@Service("smsSendDayService")
public class SmsSendDayServiceImpl extends EntityServiceImpl<SmsSendDay, SmsSendDayDao> implements SmsSendDayService {

    @Autowired
    private SmsSendLogService smsSendLogService;

    @Override
    public void daySummary(Date day) {
        if (day == null) {
            day = new Date();
        }
        Date dayPeriod = Dates.getDate(day);
        if (Collections3.isNotEmpty(getEntityDao().topSummery(dayPeriod))) {
            return;
        }
        getEntityDao().daySummery(dayPeriod, Dates.addDay(dayPeriod));
        log.info("短信发送分析 完成日终汇总处理。period:{}", Dates.format(dayPeriod, Dates.CHINESE_DATE_FORMAT_LINE));
    }

    @Override
    public List<SmsSendPeriod> querySendGroup(@NotNull DateUnit dateUnit, Date start, Date end) {
        if (dateUnit == null) {
            return Lists.newArrayList();
        }
        if (DateUnit.DAY != dateUnit && DateUnit.MONTH != dateUnit) {
            throw new BusinessException(CommonErrorCodes.UNSUPPORTED_ERROR);
        }

        if (DateUnit.DAY == dateUnit) {
            return getEntityDao().groupByDay(start, end);
        }
        if (DateUnit.MONTH == dateUnit) {
            return getEntityDao().groupByMonth(start, end);
        }
        return null;
    }
}

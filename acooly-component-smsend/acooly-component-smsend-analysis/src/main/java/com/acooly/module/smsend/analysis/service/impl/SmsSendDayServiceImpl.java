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
import com.acooly.module.smsend.SmsSendProperties;
import com.acooly.module.smsend.analysis.dao.SmsSendDayDao;
import com.acooly.module.smsend.analysis.dto.SmsSendPeriod;
import com.acooly.module.smsend.analysis.entity.SmsSendDay;
import com.acooly.module.smsend.analysis.enums.DateUnit;
import com.acooly.module.smsend.analysis.service.SmsSendDayService;
import com.acooly.module.smsend.common.enums.SmsProvider;
import com.acooly.module.smsend.manage.SmsSendLogService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private SmsSendProperties smsSendProperties;

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void daySummary(Date day, boolean redo) {
        if (day == null) {
            day = new Date();
        }
        Date dayPeriod = Dates.getDate(day);
        String dayLog = Dates.format(dayPeriod, Dates.CHINESE_DATE_FORMAT_LINE);
        if (Collections3.isNotEmpty(getEntityDao().topSummery(dayPeriod))) {
            if (redo) {
                getEntityDao().daySummaryClean(day);
                log.warn("短信发送分析 日终渠道汇总：清理数据 [成功] period:{}", dayLog);
            } else {
                log.warn("短信发送分析 指定日期日志渠道汇总已生成 [失败] period:{}", dayLog);
                throw new BusinessException(CommonErrorCodes.UNSUPPORTED_ERROR, "指定日期（" + dayLog + "）日志渠道汇总已生成");
            }
        }
        summaryCount(dayPeriod);
        summaryPrice(dayPeriod);
        log.info("短信发送分析 日终渠道汇总 [成功] period:{}", Dates.format(dayPeriod, Dates.CHINESE_DATE_FORMAT_LINE));
    }

    @Override
    public void summaryCount(Date day) {
        getEntityDao().daySummery(day, Dates.addDay(day));
        log.info("短信发送分析 日终渠道汇总：发送数量 [成功] period:{}", Dates.format(day, Dates.CHINESE_DATE_FORMAT_LINE));
    }

    @Override
    public void summaryPrice(Date day) {
        for (Map.Entry<SmsProvider, SmsSendProperties.SmsProviderInfo> entry : smsSendProperties.getProviders().entrySet()) {
            getEntityDao().daySummaryPrice(day, entry.getKey(), entry.getValue().getPrice());
        }
        log.info("短信发送分析 日终渠道汇总：发送费用 [成功] period:{}", Dates.format(day, Dates.CHINESE_DATE_FORMAT_LINE));
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

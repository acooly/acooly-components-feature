/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by zhangpu
 * date:2020-07-26
 */
package com.acooly.module.smsend.analysis.web;

import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.core.common.web.support.JsonListResult;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Dates;
import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.Strings;
import com.acooly.module.smsend.analysis.dto.SmsSendPeriod;
import com.acooly.module.smsend.analysis.entity.SmsSendDay;
import com.acooly.module.smsend.analysis.enums.DateUnit;
import com.acooly.module.smsend.analysis.service.SmsSendDayService;
import com.acooly.module.smsend.common.enums.SmsProvider;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 发送统计 管理控制器
 *
 * @author zhangpu
 * @date 2020-07-26 14:13:43
 */
@Controller
@RequestMapping(value = "/manage/analysis/smsSendDay")
public class SmsSendDayManagerController extends AbstractJsonEntityController<SmsSendDay, SmsSendDayService> {


    {
        allowMapping = "*";
    }

    @SuppressWarnings("unused")
    @Autowired
    private SmsSendDayService smsSendDayService;


    @Override
    protected List<String> doExportEntity(SmsSendDay entity) {
        return Lists.newArrayList(Dates.format(entity.getPeriod(), Dates.CHINESE_DATE_FORMAT_LINE), entity.getAppId(),
                entity.getProvider().getMessage(), String.valueOf(entity.getCount()));
    }

    @Override
    protected List<String> getExportTitles() {
        return Lists.newArrayList("日期", "应用ID", "提供方", "发送数量");
    }


    @RequestMapping("period")
    public String statistic(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAllAttributes(referenceData(request));
        return "/manage/analysis/smsSendDayPeriod";
    }

    @RequestMapping("periodData")
    @ResponseBody
    public JsonListResult<SmsSendPeriod> statisticData(HttpServletRequest request, HttpServletResponse response) {
        JsonListResult<SmsSendPeriod> result = new JsonListResult<SmsSendPeriod>();
        try {
            Date start = Servlets.getDateParameter(request, "search_GTE_start");
            Date end = Servlets.getDateParameter(request, "search_LTE_end");
            String dateUnit = Servlets.getParameter(request, "search_EQ_dateUnit");
            List<SmsSendPeriod> rows = getEntityService().querySendGroup(DateUnit.find(dateUnit), start, end);
            result.setRows(rows);
            result.setTotal(Long.valueOf(rows.size()));
        } catch (Exception e) {
            handleException(result, "", e);
        }
        return result;
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allProviders", SmsProvider.mapping());
        Map<String, String> dateUnits = Maps.newHashMap();
        dateUnits.put(DateUnit.DAY.code(), DateUnit.DAY.message());
        dateUnits.put(DateUnit.MONTH.code(), DateUnit.MONTH.message());
        model.put("allDateUnits", dateUnits);
    }

    @RequestMapping(value = "summary")
    @ResponseBody
    public JsonResult summary(HttpServletRequest request, HttpServletResponse response) {
        JsonResult result = new JsonResult();
        try {
            Date period = null;
            String periodStr = Servlets.getParameter(request, "period");
            if (Strings.isNotBlank(periodStr)) {
                period = Dates.parse(periodStr);
            } else {
                period = Dates.addDay(new Date(), -1);
            }
            smsSendDayService.daySummary(period);
            result.setMessage("生成日汇总成功：" + Dates.format(period, "yyyy-MM-dd"));
        } catch (Exception e) {
            handleException(result, "生成日汇总失败", e);
        }
        return result;
    }

    @Override
    protected Map<String, Boolean> getSortMap(HttpServletRequest request) {
        Map<String, Boolean> sortMap = super.getSortMap(request);
        if (sortMap.size() == 0) {
            sortMap.put("period", false);
        }
        return sortMap;
    }
}

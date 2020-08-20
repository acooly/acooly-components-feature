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
import com.acooly.core.utils.Money;
import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.enums.Messageable;
import com.acooly.module.smsend.analysis.dto.SmsSendPeriod;
import com.acooly.module.smsend.analysis.entity.SmsSendDay;
import com.acooly.module.smsend.analysis.enums.DateUnit;
import com.acooly.module.smsend.analysis.service.SmsSendDayService;
import com.acooly.module.smsend.common.enums.SmsProvider;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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

    @Resource(name = "mvcConversionService")
    private ConversionService conversionService;


    protected List<Object> doExportRow(SmsSendDay entity) {
//        Set<Field> fields = Reflections.getFields(entity.getClass());
//        List<Field> fieldList = Lists.newArrayList(fields);
//        AnnotationAwareOrderComparator.sort(fieldList);
//        List<Object> values = Lists.newArrayList();
//        for (Field field : fieldList) {
//            values.add(Reflections.getFieldValue(entity, field));
//        }
//        return values;
        return Lists.newArrayList(entity.getPeriod(), entity.getAppId(),
                entity.getProvider(), entity.getCount(), entity.getAmount());
    }


    @Override
    protected int doExportExcelPage(List<SmsSendDay> list, int startRow, Sheet sheet) throws Exception {
        int rowNum = startRow;
        Object value;
        Row row;
        Cell cell;
        for (SmsSendDay entity : list) {
            List data = doExportRow(entity);
            row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < data.size(); cellNum++) {
                value = data.get(cellNum);
                cell = row.createCell(cellNum);
                // 空值直接返回
                if (value == null) {
                    cell.setCellType(CellType.BLANK);
                    continue;
                }
                // 枚举
                if (Messageable.class.isAssignableFrom(value.getClass())) {
                    value = ((Messageable) value).message();
                }
                // Money转数字
                if (value instanceof Money) {
                    value = ((Money) value).getAmount();
                }
                // 日期处理
                if (value instanceof Date) {
                    if (Dates.isDate((Date) value)) {
                        value = Dates.format((Date) value, Dates.CHINESE_DATE_FORMAT_SLASH);
                    } else {
                        value = Dates.format((Date) value);
                    }
                }
                if (value instanceof Number) {
                    cell.setCellType(CellType.NUMERIC);
                    cell.setCellValue(conversionService.convert(value, Double.class));
                } else {
                    if (!(value instanceof String)) {
                        value = conversionService.convert(value, String.class);
                    }
                    cell.setCellValue((String) value);
                    int cellColumnWidth = (((String) value).getBytes("UTF-8").length + 1) * 256;
                    if (cellColumnWidth > sheet.getColumnWidth(cellNum)) {
                        sheet.setColumnWidth(cellNum, cellColumnWidth);
                    }
                }
            }

            rowNum = rowNum + 1;
        }
        return rowNum;
    }

    @Override
    protected List<String> getExportTitles() {
        return Lists.newArrayList("日期", "应用ID", "提供方", "发送数量", "费用");
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
        Map<String, String> dateUnits = Maps.newLinkedHashMap();
        dateUnits.put(DateUnit.DAY.code(), DateUnit.DAY.message());
        dateUnits.put(DateUnit.MONTH.code(), DateUnit.MONTH.message());
        model.put("allDateUnits", dateUnits);
    }

    @RequestMapping("summaryView")
    public String summaryView(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAllAttributes(referenceData(request));
        return "/manage/analysis/smsSendDaySummary";
    }

    @RequestMapping(value = "summary")
    @ResponseBody
    public JsonResult summary(HttpServletRequest request, HttpServletResponse response) {
        JsonResult result = new JsonResult();
        try {
            Date period = null;
            String periodStr = Servlets.getParameter(request, "period");
            String redoStr = Servlets.getParameter(request, "redo");
            if (Strings.isNotBlank(periodStr)) {
                period = Dates.parse(periodStr);
            } else {
                period = Dates.addDay(new Date(), -1);
            }
            smsSendDayService.daySummary(period, Strings.equalsIgnoreCase(redoStr, "true"));
            result.setMessage("生成日汇总成功：" + Dates.format(period, "yyyy-MM-dd"));
        } catch (Exception e) {
            handleException(result, "生成日汇总失败", e);
        }
        return result;
    }

    @Override
    protected Map<String, Boolean> getSortMap(HttpServletRequest request) {
        Map<String, Boolean> sortMap = super.getSortMap(request);
        if (sortMap.size() == 1 && sortMap.get("id") != null) {
            sortMap = Maps.newHashMap();
            sortMap.put("period", false);
        }
        return sortMap;
    }

    @Override
    protected Map<String, Object> getSearchParams(HttpServletRequest request) {
        Map<String, Object> map = super.getSearchParams(request);
        String ltPeriod = (String) map.get("LT_period");
        if (Strings.isNotBlank(ltPeriod)) {
            map.put("LT_period", ltPeriod + " 23:59:59");
        }
        return map;
    }
}

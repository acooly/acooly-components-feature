/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by zhangpu
 * date:2020-07-26
 */
package com.acooly.module.smsend.analysis.dao;

import com.acooly.module.mybatis.EntityMybatisDao;
import com.acooly.module.smsend.analysis.dto.SmsSendPeriod;
import com.acooly.module.smsend.analysis.entity.SmsSendDay;
import com.acooly.module.smsend.common.enums.SmsProvider;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * 发送统计 Mybatis Dao
 *
 * @author zhangpu
 * @date 2020-07-26 14:13:43
 */
public interface SmsSendDayDao extends EntityMybatisDao<SmsSendDay> {


    /**
     * 按月汇总应用发送量查询
     *
     * @param start
     * @param end
     * @return
     */
    @Select("select periodMonth as period,app_id as appId,count,amount from (select DATE_FORMAT(period,'%Y-%m') as periodMonth, app_id, sum(count) as count,sum(amount) as amount from sms_send_day" +
            " where period > #{start} and period <= #{end} group by periodMonth,app_id) t1")
    List<SmsSendPeriod> groupByMonth(@Param("start") Date start, @Param("end") Date end);

    /**
     * 按日汇总应用发送量查询
     *
     * @param start
     * @param end
     * @return
     */
    @Select("select period, app_id as appId, sum(count) as count,sum(amount) as amount from sms_send_day " +
            " where period >= #{start} and period <= #{end} group by period,app_id")
    List<SmsSendPeriod> groupByDay(@Param("start") Date start, @Param("end") Date end);


    /**
     * 从明细汇总数据到日汇总表
     *
     * @param start
     * @param end
     */
    @Update("insert into sms_send_day(app_id,provider,period,`count`) select t1.app_id,t1.provider,#{start},t1.sendCount from\n" +
            "(select app_id, provider,count(*) as sendCount from sms_send_log " +
            "where send_time >= #{start} and send_time < #{end} and status = 'SUCCESS' " +
            "group by app_id, provider) t1")
    void daySummery(@Param("start") Date start, @Param("end") Date end);

    /**
     * 更新当日指定提供方的汇总费用
     *
     * @param day
     * @param provider
     * @param providerUnitPrice
     */
    @Update("update sms_send_day set amount = (count / 10 * #{providerUnitPrice}) " +
            "where period = #{day} and provider = #{provider}")
    void daySummaryPrice(@Param("day") Date day,
                         @Param("provider") SmsProvider provider,
                         @Param("providerUnitPrice") long providerUnitPrice);


    /**
     * 清理指定日期的汇总数据
     *
     * @param day
     */
    @Delete("delete from sms_send_day where period = #{day}")
    void daySummaryClean(@Param("day") Date day);


    /**
     * 查询账期对应的数据
     *
     * @param period
     * @return
     */
    @Select("select * from sms_send_day where period = #{period} ")
    List<SmsSendDay> topSummery(@Param("period") Date period);

}

/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by acooly
 * date:2020-05-08
 */
package com.acooly.module.smsend.dao;

import com.acooly.module.mybatis.EntityMybatisDao;
import com.acooly.module.smsend.entity.SmsSendLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * 短信发送记录 Mybatis Dao
 *
 * @author acooly
 * @date 2020-05-08 14:32:47
 */
public interface SmsSendLogDao extends EntityMybatisDao<SmsSendLog> {

    /**
     * IP统计
     *
     * @param ip
     * @param startTime
     * @return
     */
    @Query("select count(e) from sms_send_log e where e.client_ip=#{ip} and e.create_time > #{startTime}")
    Long countByIp(@Param("ip") String ip, @Param("startTime") Date startTime);

    /**
     * 手机号码统计
     *
     * @param mobileNo
     * @param startTime
     * @return
     */
    @Query("select count(e) from sms_send_log e where e.mobile_no=#{mobileNo} and e.create_time > #{startTime}")
    Long countByMobileNo(@Param("mobileNo") String mobileNo, @Param("startTime") Date startTime);
}

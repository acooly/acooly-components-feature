/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by acooly
 * date:2020-05-19
 */
package com.acooly.module.smsend.dao;

import com.acooly.core.utils.enums.SimpleStatus;
import com.acooly.module.mybatis.EntityMybatisDao;
import com.acooly.module.smsend.entity.SmsApp;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 短信发送应用 Mybatis Dao
 *
 * @author acooly
 * @date 2020-05-19 15:03:06
 */
public interface SmsAppDao extends EntityMybatisDao<SmsApp> {

    /**
     * 更具状态查询
     *
     * @param status
     * @return
     */
    @Select("select * from sms_app where status = #{status}")
    List<SmsApp> findByStatus(@Param("status") SimpleStatus status);

    /**
     * 根据AppId查询
     *
     * @param appId
     * @return
     */
    @Select("select * from sms_app where app_id = #{appId}")
    SmsApp findByAppId(@Param("appId") String appId);
}

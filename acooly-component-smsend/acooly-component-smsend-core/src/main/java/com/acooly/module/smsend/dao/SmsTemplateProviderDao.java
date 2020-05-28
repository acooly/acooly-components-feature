/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by acooly
 * date:2020-05-19
 */
package com.acooly.module.smsend.dao;

import com.acooly.module.mybatis.EntityMybatisDao;
import com.acooly.module.smsend.common.enums.SmsProvider;
import com.acooly.module.smsend.entity.SmsTemplateProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 模板渠道 Mybatis Dao
 *
 * @author acooly
 * @date 2020-05-19 15:03:06
 */
public interface SmsTemplateProviderDao extends EntityMybatisDao<SmsTemplateProvider> {

    /**
     * 查询唯一
     *
     * @param provider
     * @param templateCode
     * @return
     */
    @Select("select * from sms_template_provider where template_code = #{templateCode} and provider = #{provider}")
    SmsTemplateProvider findByProviderAndTemplateCode(@Param("provider") SmsProvider provider,
                                                      @Param("templateCode") String templateCode);

    /**
     * 根据模板编码查询
     * @param templateCode
     * @return
     */
    @Select("select * from sms_template_provider where template_code = #{templateCode}")
    List<SmsTemplateProvider> findByTemplateCode(@Param("templateCode") String templateCode);
}

/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by acooly
 * date:2020-05-19
 *
 */
package com.acooly.module.smsend.manage;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.smsend.common.enums.SmsProvider;
import com.acooly.module.smsend.entity.SmsTemplateProvider;

import java.util.List;

/**
 * 模板渠道 Service接口
 *
 * @author acooly
 * @date 2020-05-19 15:03:06
 */
public interface SmsTemplateProviderService extends EntityService<SmsTemplateProvider> {

    /**
     * 查询唯一的渠道模板
     *
     * @param templateCode
     * @param provider
     * @return
     */
    SmsTemplateProvider findUnique(String templateCode, SmsProvider provider);

    /**
     * 根据模板编码查询
     *
     * @param templateCode
     * @return
     */
    List<SmsTemplateProvider> findByTemplateCode(String templateCode);

}

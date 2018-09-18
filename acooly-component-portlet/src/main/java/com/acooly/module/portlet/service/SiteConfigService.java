/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-03-20
 *
 */
package com.acooly.module.portlet.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.portlet.entity.SiteConfig;

import java.util.List;

/**
 * portlet_site_config Service接口
 *
 * <p>Date: 2017-03-20 23:36:29
 *
 * @author acooly
 */
public interface SiteConfigService extends EntityService<SiteConfig> {

    /**
     * 通过类型查询所有配置实体
     *
     * @param type
     * @return
     */
    List<SiteConfig> findByType(String type);

    /**
     * 查询默认类型的所有配置
     *
     * @return
     */
    List<SiteConfig> findByType();

    /**
     * 通过类型和名称查询唯一的配置
     *
     * @param type
     * @param name
     * @return
     */
    SiteConfig findUnique(String type, String name);

    /**
     * 查询默认类型的指定名称的配置
     *
     * @param name
     * @return
     */
    SiteConfig findUnique(String name);
}

/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-03-20
 */
package com.acooly.module.portlet.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.portlet.dao.SiteConfigDao;
import com.acooly.module.portlet.entity.SiteConfig;
import com.acooly.module.portlet.enums.SiteConfigTypeEnum;
import com.acooly.module.portlet.service.SiteConfigService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * portlet_site_config Service实现
 *
 * <p>Date: 2017-03-20 23:36:29
 *
 * @author acooly
 */
@Service("siteConfigService")
public class SiteConfigServiceImpl extends EntityServiceImpl<SiteConfig, SiteConfigDao>
        implements SiteConfigService {

    @Cacheable(value = "cacheName", key = "#type")
    @Override
    public List<SiteConfig> findByType(String type) {
        return getEntityDao().findByType(type);
    }

    @Override
    public List<SiteConfig> findByType() {
        return findByType(SiteConfigTypeEnum.def.code());
    }

    @Override
    @Cacheable(value = "cacheName", key = "#type + '_' + #name")
    public SiteConfig findUnique(String type, String name) {
        return getEntityDao().findUnique(type, name);
    }

    @Override
    public SiteConfig findUnique(String name) {
        return findUnique(SiteConfigTypeEnum.def.code(), name);
    }
}

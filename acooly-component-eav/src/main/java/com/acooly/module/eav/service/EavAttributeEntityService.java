/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiubo
 * date:2018-06-26
 *
 */
package com.acooly.module.eav.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.eav.entity.EavAttribute;

import java.util.List;

/**
 * eav_attribute Service接口
 *
 * Date: 2018-06-26 21:51:37
 * @author qiubo
 *
 */
public interface EavAttributeEntityService extends EntityService<EavAttribute> {

    List<EavAttribute> loadEavAttribute(Long schemeId);

    void moveTop(Long id);

    void moveUp(Long id);

}

/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-03-21
 *
 */
package com.acooly.module.portlet.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.portlet.entity.ActionMapping;

/**
 * 访问映射 Service接口
 *
 * <p>Date: 2017-03-21 00:34:47
 *
 * @author acooly
 */
public interface ActionMappingService extends EntityService<ActionMapping> {

    ActionMapping getActionMapping(String url);
}

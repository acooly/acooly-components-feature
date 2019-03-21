/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by zhangpu
 * date:2019-03-05
 *
 */
package com.acooly.module.eav.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.eav.entity.EavSchemeTag;

import java.util.List;

/**
 * 方案标签 Service接口
 * <p>
 * Date: 2019-03-05 18:02:36
 *
 * @author zhangpu
 */
public interface EavSchemeTagService extends EntityService<EavSchemeTag> {


    List<EavSchemeTag> list(Long schemeId);
    
    EavSchemeTag save(Long schemeId, String tag);

}

/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiubo
 * date:2018-06-26
 */
package com.acooly.module.eav.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.eav.dao.EavEntityDao;
import com.acooly.module.eav.entity.EavEntity;
import com.acooly.module.eav.service.EavEntityEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * eav_entity Service实现
 * <p><b>注意:此类所有的方法都在事务中执行。<b/>
 * Date: 2018-06-26 21:51:37
 *
 * @author qiubo
 */
@Service("eavEntityEntityService")
public class EavEntityEntityServiceImpl extends EntityServiceImpl<EavEntity, EavEntityDao> implements EavEntityEntityService {
    @Autowired
    private EavEntityService eavEntityService;

    @Override
    public void save(EavEntity o) throws BusinessException {
        eavEntityService.validate(o);
        super.save(o);
    }
}

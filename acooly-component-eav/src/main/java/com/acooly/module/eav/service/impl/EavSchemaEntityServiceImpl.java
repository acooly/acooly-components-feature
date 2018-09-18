/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiubo
 * date:2018-06-26
 */
package com.acooly.module.eav.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.eav.dao.EavSchemaDao;
import com.acooly.module.eav.entity.EavSchema;
import com.acooly.module.eav.service.EavSchemaEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * eav_schema Service实现
 * <p><b>注意:此类所有的方法都在事务中执行。<b/>
 * Date: 2018-06-26 21:51:37
 *
 * @author qiubo
 */
@Service("eavSchemaEntityService")
public class EavSchemaEntityServiceImpl extends EntityServiceImpl<EavSchema, EavSchemaDao> implements EavSchemaEntityService {
    @Autowired
    private EavEntityService eavEntityService;

    @Override
    public void remove(EavSchema o) throws BusinessException {
        super.remove(o);
        eavEntityService.sendEavSchemaChangeMessage(o.getId());
    }

    @Override
    public void removeById(Serializable id) throws BusinessException {
        super.removeById(id);
        eavEntityService.sendEavSchemaChangeMessage(id);

    }

    @Override
    public void removes(Serializable... ids) throws BusinessException {
        super.removes(ids);
        for (Serializable id : ids) {
            eavEntityService.sendEavSchemaChangeMessage(id);
        }
    }

    @Override
    public void save(EavSchema o) throws BusinessException {
        super.save(o);
        if (!o.isNew()) {
            eavEntityService.sendEavSchemaChangeMessage(o.getId());
        }
    }

    @Override
    public void saves(List<EavSchema> eavSchemas) throws BusinessException {
        super.saves(eavSchemas);
        for (EavSchema eavSchema : eavSchemas) {
            if (!eavSchema.isNew()) {
                eavEntityService.sendEavSchemaChangeMessage(eavSchema.getId());
            }
        }
    }

    @Override
    public void update(EavSchema o) throws BusinessException {
        super.update(o);
        eavEntityService.sendEavSchemaChangeMessage(o.getId());
    }

    @Override
    public void saveOrUpdate(EavSchema eavSchema) throws BusinessException {
        super.saveOrUpdate(eavSchema);
        if (!eavSchema.isNew()) {
            eavEntityService.sendEavSchemaChangeMessage(eavSchema.getId());
        }
    }
}

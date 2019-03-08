/*
 * acooly.cn Inc.
 * Copyright (c) 2018 All Rights Reserved.
 * create by qiubo
 * date:2018-06-26
 */
package com.acooly.module.eav.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Strings;
import com.acooly.module.eav.dao.EavAttributeDao;
import com.acooly.module.eav.entity.EavAttribute;
import com.acooly.module.eav.service.EavAttributeEntityService;
import com.acooly.module.eav.service.EavOptionService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * eav_attribute Service实现
 * <p><b>注意:此类所有的方法都在事务中执行。<b/>
 * Date: 2018-06-26 21:51:37
 *
 * @author qiubo
 */
@Service("eavAttributeEntityService")
public class EavAttributeEntityServiceImpl extends EntityServiceImpl<EavAttribute, EavAttributeDao> implements EavAttributeEntityService {
    @Autowired
    private EavEntityService eavEntityService;
    @Autowired
    private EavOptionService eavOptionService;


    @Override
    public void moveTop(Long id) {
        try {
            EavAttribute entity = get(id);
            Long schemeId = entity.getSchemeId();
            List<EavAttribute> levels = getEntityDao().findAttributesBySchemaId(schemeId);
            if (levels.size() == 1) {
                return;
            }
            EavAttribute first = Collections3.getFirst(levels);
            if (first.getId().equals(entity.getId())) {
                return;
            }
            entity.setSortTime(first.getSortTime() - RandomUtils.nextInt(10, 100));
            update(entity);
        } catch (Exception e) {
            throw new BusinessException("置顶失败", e);
        }
    }

    @Transactional
    @Override
    public void moveUp(Long id) {
        try {
            EavAttribute current = get(id);
            Long schemeId = current.getSchemeId();
            List<EavAttribute> levels = getEntityDao().findAttributesBySchemaId(schemeId);
            if (levels.size() == 1) {
                return;
            }

            int index = levels.indexOf(current);
            // 如果在本层的第一个，则不处理
            if (index <= 0) {
                return;
            }
            EavAttribute prev = levels.get(index - 1);
            long pervSortTime = prev.getSortTime();
            prev.setSortTime(current.getSortTime());
            current.setSortTime(pervSortTime);
            update(current);
            update(prev);
        } catch (Exception e) {
            throw new BusinessException("上移失败", e);
        }
    }

    @Override
    public void remove(EavAttribute o) throws BusinessException {
        super.remove(o);
        eavEntityService.sendEavAttributeChangeMessage(o.getId());
    }

    @Override
    public void removeById(Serializable id) throws BusinessException {
        super.removeById(id);
        eavEntityService.sendEavAttributeChangeMessage(id);

    }

    @Override
    public void removes(Serializable... ids) throws BusinessException {
        super.removes(ids);
        for (Serializable id : ids) {
            eavEntityService.sendEavAttributeChangeMessage(id);
        }
    }

    @Override
    public void save(EavAttribute o) throws BusinessException {
        super.save(o);
        if (!o.isNew()) {
            eavEntityService.sendEavAttributeChangeMessage(o.getId());
        }
        if (o.getSchemeId() != null) {
            eavEntityService.sendEavSchemaChangeMessage(o.getSchemeId());
        }
    }

    @Override
    public void saves(List<EavAttribute> eavAttributes) throws BusinessException {
        super.saves(eavAttributes);
        for (EavAttribute eavAttribute : eavAttributes) {
            if (!eavAttribute.isNew()) {
                eavEntityService.sendEavAttributeChangeMessage(eavAttribute.getId());
            }
            if (eavAttribute.getSchemeId() != null) {
                eavEntityService.sendEavSchemaChangeMessage(eavAttribute.getSchemeId());
            }
        }
    }

    @Override
    public void update(EavAttribute o) throws BusinessException {
        super.update(o);
        if (!o.isNew()) {
            eavEntityService.sendEavAttributeChangeMessage(o.getId());
        }
        if (o.getSchemeId() != null) {
            eavEntityService.sendEavSchemaChangeMessage(o.getSchemeId());
        }
    }

    @Override
    public void saveOrUpdate(EavAttribute eavAttribute) throws BusinessException {
        super.saveOrUpdate(eavAttribute);
        if (!eavAttribute.isNew()) {
            eavEntityService.sendEavAttributeChangeMessage(eavAttribute.getId());
        }
        if (eavAttribute.getSchemeId() != null) {
            eavEntityService.sendEavSchemaChangeMessage(eavAttribute.getSchemeId());
        }
    }


    @Override
    public List<EavAttribute> loadEavAttribute(Long schemeId) {
        List<EavAttribute> eavAttributes = getEntityDao().findAttributesBySchemaId(schemeId);
        eavAttributes.forEach((e) -> {
            if (Strings.isNotBlank(e.getEnumValue())) {
                e.setOptions(eavOptionService.listChildrenByCode(e.getEnumValue()));
            }
        });
        return eavAttributes;
    }
}

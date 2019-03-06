/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by zhangpu
 * date:2019-03-05
 */
package com.acooly.module.eav.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Strings;
import com.acooly.module.eav.dao.EavOptionDao;
import com.acooly.module.eav.entity.EavOption;
import com.acooly.module.eav.service.EavOptionService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 属性选项 Service实现
 * <p>
 * Date: 2019-03-05 18:02:36
 *
 * @author zhangpu
 */
@Service("eavOptionService")
public class EavOptionServiceImpl extends EntityServiceImpl<EavOption, EavOptionDao> implements EavOptionService {


    @Override
    public List<EavOption> listTop() {
        return getEntityDao().findTops();
    }

    @Override
    public List<EavOption> listByPath(String path) {
        return getEntityDao().findByPath(path);
    }

    @Override
    public List<EavOption> listByParentId(Long parentId) {
        return getEntityDao().findByParentId(parentId);
    }

    @Override
    public void save(EavOption o) throws BusinessException {
        // 处理parent
        if (o.getId() == null) {
            doSetPath(o);
            super.save(o);
        } else {
            super.update(o);
        }
    }


    @Override
    @Transactional
    public void saves(List<EavOption> eavOptions) throws BusinessException {
        if (Collections3.isEmpty(eavOptions)) {
            return;
        }
        int index = 1;
        for (EavOption eavOption : eavOptions) {
            eavOption.setSortTime(eavOption.getSortTime() + index++);
            save(eavOption);
        }
    }

    @Override
    public void moveTop(Long id) {
        try {
            EavOption eavOption = get(id);
            eavOption.setSortTime(System.currentTimeMillis());
            save(eavOption);
        } catch (Exception e) {
            throw new BusinessException("置顶失败", e);
        }
    }

    @Override
    public void moveUp(Long id) {
        try {
            EavOption current = get(id);
            Long parentId = current.getParentId();
            List<EavOption> levels = null;
            if (parentId == null) {
                levels = getEntityDao().findTops();
            } else {
                levels = getEntityDao().findByParentId(parentId);
            }

            if (levels.size() == 1) {
                return;
            }

            int index = levels.indexOf(current);
            // 如果在本层的第一个，则不处理
            if (index <= 0) {
                return;
            }
            EavOption prev = levels.get(index - 1);
            long pervSortTime = prev.getSortTime();
            prev.setSortTime(current.getSortTime());
            current.setSortTime(pervSortTime);
            saves(Lists.newArrayList(current, prev));
        } catch (Exception e) {
            throw new BusinessException("上移失败", e);
        }
    }

    protected void doSetPath(EavOption o) {
        o.setPath("/");
        if (o.getParentId() != null) {
            EavOption parent = get(o.getParentId());
            if (parent != null) {
                o.setParentId(parent.getId());
                String parentPath = parent.getPath();
                if (Strings.isBlank(parentPath)) {
                    parentPath = "/";
                }
                o.setPath(parentPath + parent.getId() + "/");
            }
        }
    }
}

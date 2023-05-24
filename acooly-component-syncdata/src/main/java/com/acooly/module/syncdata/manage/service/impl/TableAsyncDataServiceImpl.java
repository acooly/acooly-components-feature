/*
 * acooly.cn Inc.
 * Copyright (c) 2023 All Rights Reserved.
 * create by acooly
 * date:2023-05-06
 */
package com.acooly.module.syncdata.manage.service.impl;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.syncdata.manage.dao.TableAsyncDataDao;
import com.acooly.module.syncdata.manage.entity.TableAsyncData;
import com.acooly.module.syncdata.manage.service.TableAsyncDataService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 同步表数据信息 Service实现
 *
 * @author acooly
 * @date 2023-05-06 08:59:00
 */
@Service("tableAsyncDataService")
public class TableAsyncDataServiceImpl extends EntityServiceImpl<TableAsyncData, TableAsyncDataDao> implements TableAsyncDataService {

    @Override
    public List<TableAsyncData> findByType(String type) {
        return getEntityDao().findByType(type);
    }


    public void moveUp(Serializable id) {
        try {
            TableAsyncData appBanner = get(id);
            Date current = appBanner.getSortTime();
            TableAsyncData perv = null;
            PageInfo<TableAsyncData> pageInfo = new PageInfo<TableAsyncData>(1, (int) 1);
            Map<String, Boolean> sortMap = Maps.newLinkedHashMap();
            sortMap.put("sortTime", true);
            Map<String, Object> searchMap = Maps.newHashMap();
            searchMap.put("GT_sortTime", current);
            pageInfo = getEntityDao().query(pageInfo, searchMap, sortMap);
            if (pageInfo.getPageResults() != null && pageInfo.getPageResults().size() > 0) {
                perv = pageInfo.getPageResults().get(0);
            }
            if (perv != null) {
                appBanner.setSortTime(perv.getSortTime());
                perv.setSortTime(current);
                saveOrUpdate(perv);
            } else {
                appBanner.setSortTime(new Date());
            }
            saveOrUpdate(appBanner);
        } catch (Exception e) {
            throw new BusinessException("上移失败", e);
        }


    }

    @Override
    public void moveTop(Serializable id) {
        TableAsyncData appBanner = get(id);
        try {
            appBanner.setSortTime(new Date());
            this.saveOrUpdate(appBanner);
        } catch (Exception e) {
            throw new BusinessException("置顶失败", e);
        }
    }
}

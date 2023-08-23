/*
 * acooly.cn Inc.
 * Copyright (c) 2023 All Rights Reserved.
 * create by acooly
 * date:2023-05-06
 *
 */
package com.acooly.module.syncdata.manage.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.syncdata.manage.entity.TableAsyncData;

import java.io.Serializable;
import java.util.List;

/**
 * 同步表数据信息 Service接口
 *
 * @author acooly
 * @date 2023-05-06 08:59:00
 */
public interface TableAsyncDataService extends EntityService<TableAsyncData> {

    List<TableAsyncData> findByType(String type);


    void moveUp(Serializable id);


    void moveTop(Serializable id);
}

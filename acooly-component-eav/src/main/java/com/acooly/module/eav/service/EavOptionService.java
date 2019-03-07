/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by zhangpu
 * date:2019-03-05
 *
 */
package com.acooly.module.eav.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.eav.entity.EavOption;

import java.util.List;

/**
 * 属性选项 Service接口
 * <p>
 * Date: 2019-03-05 18:02:35
 *
 * @author zhangpu
 */
public interface EavOptionService extends EntityService<EavOption> {


    List<EavOption> listTop();

    List<EavOption> listByPath(String path);

    List<EavOption> listChildrenByCode(String code);

    List<EavOption> listByParentId(Long parentId);

    void moveTop(Long id);

    void moveUp(Long id);
}

/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by shuijing
 * date:2017-05-26
 *
 */
package com.acooly.module.security.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.security.domain.Org;
import com.acooly.module.security.dto.OrgManagers;

import java.util.List;
import java.util.Map;

/**
 * 组织机构 Service接口 Date: 2017-05-26 16:48:57
 *
 * @author shuijing
 */
public interface OrgService extends EntityService<Org> {

    Map<Long, Object> getOrganizeInfo(long parentId);

    List<Org> getTreeList(Long orgId);

    List<Org> getTreeListLikeName(Long orgId, String name);

    /**
     * 检查组织结构是否可用
     *
     * @param orgId
     * @return
     */
    Boolean checkOrgValid(Long orgId);


    /**
     * 获取制定Id的组织机构的管理者信息
     *
     * @param orgId
     * @return
     */
    OrgManagers getOrgManagers(Long orgId);

}

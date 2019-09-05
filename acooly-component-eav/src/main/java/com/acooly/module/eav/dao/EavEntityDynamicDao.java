package com.acooly.module.eav.dao;

import com.acooly.core.common.dao.DynamicListQueryDao;
import com.acooly.core.common.dao.DynamicPagedQueryDao;
import com.acooly.module.eav.entity.EavEntity;

/**
 * 动态实体的查询专用DTO
 *
 * @author zhangpu
 * @date 2019-03-13 17:36
 */
public interface EavEntityDynamicDao extends DynamicListQueryDao<EavEntity>, DynamicPagedQueryDao<EavEntity> {


}

/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by zhangpu
 * date:2019-03-05
 */
package com.acooly.module.eav.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Strings;
import com.acooly.module.eav.dao.EavSchemeTagDao;
import com.acooly.module.eav.entity.EavSchemeTag;
import com.acooly.module.eav.service.EavSchemeTagService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 方案标签 Service实现
 * <p>
 * Date: 2019-03-05 18:02:36
 *
 * @author zhangpu
 */
@Service("eavSchemeTagService")
public class EavSchemeTagServiceImpl extends EntityServiceImpl<EavSchemeTag, EavSchemeTagDao> implements EavSchemeTagService {

    @Override
    public List<EavSchemeTag> list(Long schemeId) {
        return getEntityDao().findBySchemeId(schemeId);
    }

    @Override
    public EavSchemeTag save(Long schemeId, String tag) {
        List<EavSchemeTag> tags = list(schemeId);
        if (Collections3.isNotEmpty(tags)) {
            for (EavSchemeTag eavSchemeTag : tags) {
                if (Strings.equals(eavSchemeTag.getTag(), tag)) {
                    return eavSchemeTag;
                }
            }
        }
        EavSchemeTag eavSchemeTag = new EavSchemeTag(schemeId, tag);
        save(eavSchemeTag);
        return eavSchemeTag;
    }
}

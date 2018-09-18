package com.acooly.module.cms.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.cms.dao.ContentTypeDao;
import com.acooly.module.cms.domain.Content;
import com.acooly.module.cms.domain.ContentType;
import com.acooly.module.cms.service.ContentTypeService;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.acooly.module.cms.domain.Content.STATUS_ENABLED;

@Service("contentTypeService")
public class ContentTypeServiceImpl extends EntityServiceImpl<ContentType, ContentTypeDao>
        implements ContentTypeService {

    @Override
    public void save(ContentType o) throws BusinessException {

        if (o.getParent() != null && o.getParent().getId() != null) {
            o.setPath(StringUtils.trimToEmpty(o.getParent().getPath()) + o.getParent().getId() + "|");
        }
        super.save(o);
    }

    @Override
    public List<ContentType> getLevel(String parentId) {

        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(parentId)) {
            map.put("EQ_parent.id", parentId);
        } else {
            map.put("NULL_parent.id", "1");
        }
        return query(map, null);
    }

    @Override
    public ContentType getContentType(String code) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("EQ_code", code);
        List<ContentType> list = query(map, null);
        if (list != null && list.size() > 0) {
            return list.iterator().next();
        } else {
            return null;
        }
    }

    @Override
    public Map<String, String> getEnabledCodeAndName() {
        Map<String, String> res = Maps.newHashMap();
        List<ContentType> all = getAll();
        all.forEach(type -> {
            if (type.getParent() != null) {
                res.put(type.getCode(), type.getName());
            }
        });
        return res;
    }
}

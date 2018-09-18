/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-03-21
 */
package com.acooly.module.portlet.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Collections3;
import com.acooly.module.portlet.dao.ActionMappingDao;
import com.acooly.module.portlet.entity.ActionMapping;
import com.acooly.module.portlet.service.ActionMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * 访问映射 Service实现
 *
 * <p>Date: 2017-03-21 00:34:47
 *
 * @author acooly
 */
@Slf4j
@Service("actionMappingService")
public class ActionMappingServiceImpl extends EntityServiceImpl<ActionMapping, ActionMappingDao>
        implements ActionMappingService {

    private static final String ACTION_MAPPING_CACHE_PREFIX = "portlet.action.mapping";
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ActionMapping getActionMapping(String url) {
        ValueOperations<String, ActionMapping> va = redisTemplate.opsForValue();
        String cacheKey = getCacheKey(url);
        ActionMapping actionMapping = va.get(cacheKey);
        if (actionMapping != null) {
            return actionMapping;
        } else {
            List<ActionMapping> actionMappingList = getEntityDao().findByLikeUrl(url);

            if (Collections3.isEmpty(actionMappingList)) {
                return null;
            }
            actionMapping = Collections3.getFirst(actionMappingList);
            if (actionMapping == null) {
                return null;
            }
            va.set(cacheKey, actionMapping);
            return actionMapping;
        }
    }

    @Override
    public void save(ActionMapping o) throws BusinessException {
        super.save(o);
        updateCache(o);
    }

    @Override
    public void removeById(Serializable id) throws BusinessException {
        ActionMapping actionMapping = get(id);
        if (actionMapping != null) {
            deleteCache(actionMapping);
        }
        super.removeById(id);
    }

    private void deleteCache(ActionMapping actionMapping) {
        try {
            redisTemplate.delete(actionMapping.getUrl());
        } catch (Exception e) {
            log.warn("actionMapping删除缓存失败：{}", actionMapping);
        }
    }

    private void updateCache(ActionMapping actionMapping) {
        try {
            ValueOperations<String, ActionMapping> va = redisTemplate.opsForValue();
            String cacheKey = getCacheKey(actionMapping.getUrl());
            va.set(cacheKey, actionMapping);
        } catch (Exception e) {
            log.warn("actionMapping更新缓存失败：{}", actionMapping);
        }
    }

    private String getCacheKey(String url) {
        return ACTION_MAPPING_CACHE_PREFIX + "_" + url;
    }
}

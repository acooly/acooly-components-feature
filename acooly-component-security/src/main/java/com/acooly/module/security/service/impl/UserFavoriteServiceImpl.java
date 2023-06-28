/*
 * acooly.cn Inc.
 * Copyright (c) 2023 All Rights Reserved.
 * create by zhangpu
 * date:2023-06-27
 */
package com.acooly.module.security.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.security.dao.UserFavoriteDao;
import com.acooly.module.security.domain.UserFavorite;
import com.acooly.module.security.dto.UserFavorites;
import com.acooly.module.security.service.UserFavoriteService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * sys_user_favorite Service实现
 *
 * @author zhangpu
 * @date 2023-06-27 20:16:01
 */
@Service("userFavoriteService")
public class UserFavoriteServiceImpl extends EntityServiceImpl<UserFavorite, UserFavoriteDao> implements UserFavoriteService {

    @Override
    public List<UserFavorites> queryFavorites(Long userId) {
        return getEntityDao().findByUserId(userId);
    }

    @Override
    public void deleteByUserIdAndResourceId(Long userId, Long resourceId) {
        getEntityDao().deleteByUserIdAndResourceId(userId, resourceId);
    }
}

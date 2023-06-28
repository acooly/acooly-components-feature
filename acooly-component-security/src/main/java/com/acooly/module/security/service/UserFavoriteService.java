/*
 * acooly.cn Inc.
 * Copyright (c) 2023 All Rights Reserved.
 * create by zhangpu
 * date:2023-06-27
 *
 */
package com.acooly.module.security.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.security.domain.UserFavorite;
import com.acooly.module.security.dto.UserFavorites;

import java.util.List;

/**
 * sys_user_favorite Service接口
 *
 * @author zhangpu
 * @date 2023-06-27 20:16:01
 */
public interface UserFavoriteService extends EntityService<UserFavorite> {

    /**
     * 查询收藏的资源
     *
     * @param userId
     * @return
     */
    List<UserFavorites> queryFavorites(Long userId);

    /**
     * 唯一索引删除
     *
     * @param userId
     * @param resourceId
     */
    void deleteByUserIdAndResourceId(Long userId, Long resourceId);

}

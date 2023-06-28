/*
 * acooly.cn Inc.
 * Copyright (c) 2023 All Rights Reserved.
 * create by zhangpu
 * date:2023-06-27
 */
package com.acooly.module.security.dao;

import com.acooly.module.mybatis.EntityMybatisDao;
import com.acooly.module.security.domain.UserFavorite;
import com.acooly.module.security.dto.UserFavorites;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * sys_user_favorite Mybatis Dao
 *
 * @author zhangpu
 * @date 2023-06-27 20:16:01
 */
public interface UserFavoriteDao extends EntityMybatisDao<UserFavorite> {

    /**
     * 根据用户ID查询收藏的资源信息
     *
     * @param userId
     * @return
     */
    @Select("select t1.*, t2.name as resc_name, t2.`value` as resc_value,t2.icon as resc_icon, t2.show_mode from sys_user_favorite t1, sys_resource t2 where t1.resc_id = t2.id and t1.user_id = #{userId} order by t1.sort_time desc")
    List<UserFavorites> findByUserId(@Param("userId") Long userId);

    /**
     * 唯一索引删除
     *
     * @param userId     用户ID
     * @param resourceId 资源ID
     * @return 条数
     */
    @Delete("delete from sys_user_favorite where user_id = #{userId} and resc_id = #{resourceId}")
    int deleteByUserIdAndResourceId(@Param("userId") Long userId, @Param("resourceId") Long resourceId);

}

/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by zhangpu
 * date:2022-10-05
 */
package cn.acooly.component.rbac.dao;

import cn.acooly.component.rbac.dto.RbacResourceNode;
import cn.acooly.component.rbac.entity.RbacResource;
import com.acooly.module.mybatis.EntityMybatisDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 权限资源表 Mybatis Dao
 *
 * @author zhangpu
 * @date 2022-10-05 23:03:16
 */
public interface RbacResourceDao extends EntityMybatisDao<RbacResource> {


    /**
     * 根据RoleId查询对应的权限资源
     *
     * @param roleId
     * @return
     */
    @Select("select t1.* from rbac_resource t1, rbac_role_resc t2  where t1.id = t2.resc_id and " +
            "t2.role_id = #{roleId} order by t1.order_time desc")
    List<RbacResource> findByRoleId(@Param("roleId") Long roleId);


    /**
     * 根据用户ID查询所有已授权的资源列表
     *
     * @param userId
     * @return
     */
    @Select("select t1.* from rbac_resource t1, rbac_role_resc t2, rbac_user_role t3 "
            + "where t1.id = t2.resc_id and t2.role_id = t3.role_id and "
            + "t3.user_id = #{userId} order by t1.order_time desc")
    List<RbacResourceNode> getAuthorizedResourceNodeWithUserId(@Param("userId") Long userId);

    @Select("select t1.* from rbac_resource t1 where t1.`value`=#{resourceValue} limit 1")
    RbacResource findOneByResourceValue(@Param("resourceValue")String resourceValue);
}

/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by zhangpu
 * date:2022-10-05
 */
package cn.acooly.component.rbac.dao;

import cn.acooly.component.rbac.entity.RbacRole;
import com.acooly.module.mybatis.EntityMybatisDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 角色表 Mybatis Dao
 *
 * @author zhangpu
 * @date 2022-10-05 23:03:16
 */
public interface RbacRoleDao extends EntityMybatisDao<RbacRole> {

    /**
     * 根据userId查询对应的角色
     *
     * @param userId
     * @return
     */
    @Select(" select t1.* from rbac_role t1, rbac_user_role t2  where t1.id = t2.role_id and t2.user_id = #{userId}")
    List<RbacRole> findByUserId(@Param("userId") Long userId);


    @Select(" select t1.* from rbac_role t1 where t1.name= #{roleName}")
    RbacRole findByName(@Param("roleName") String roleName);
}

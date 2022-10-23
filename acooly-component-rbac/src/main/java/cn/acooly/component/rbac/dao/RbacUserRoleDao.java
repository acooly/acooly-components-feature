/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by acooly
 * date:2022-10-12
 */
package cn.acooly.component.rbac.dao;

import com.acooly.module.mybatis.EntityMybatisDao;
import cn.acooly.component.rbac.entity.RbacUserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户角色表 Mybatis Dao
 *
 * @author acooly
 * @date 2022-10-12 16:36:28
 */
public interface RbacUserRoleDao extends EntityMybatisDao<RbacUserRole> {


    @Select("select * from rbac_user_role where user_id=#{userId}")
    List<RbacUserRole> findByUserId(@Param("userId") long userId);

    @Delete("delete from rbac_user_role where user_id=#{userId}")
    void removeByUserId(@Param("userId") long userId);

}

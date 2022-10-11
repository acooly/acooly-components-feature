/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by zhangpu
 * date:2022-10-05
 */
package cn.acooly.component.rbac.dao;

import cn.acooly.component.rbac.entity.RbacUser;
import com.acooly.module.mybatis.EntityMybatisDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用户表 Mybatis Dao
 *
 * @author zhangpu
 * @date 2022-10-05 23:03:16
 */
public interface RbacUserDao extends EntityMybatisDao<RbacUser> {

    /**
     * 用户名查询
     *
     * @param username
     * @return
     */
    @Select("select * from rbac_user where username=#{username}")
    RbacUser findByUsername(@Param("username") String username);


    /**
     * 会员编码查询
     *
     * @param memberNo
     * @return
     */
    @Select("select * from rbac_user where member_no=#{memberNo}")
    RbacUser findByMemberNo(@Param("memberNo") String memberNo);
}


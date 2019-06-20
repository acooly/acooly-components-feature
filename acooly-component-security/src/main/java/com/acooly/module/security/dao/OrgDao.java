/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by shuijing
 * date:2017-05-26
 */
package com.acooly.module.security.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.acooly.module.jpa.EntityJpaDao;
import com.acooly.module.security.domain.Org;

/**
 * 组织机构 JPA Dao
 *
 * <p>
 * Date: 2017-05-26 16:48:57
 *
 * @author shuijing
 */
public interface OrgDao extends EntityJpaDao<Org, Long> {
	@Query(value = "select * from sys_org where parent_id= :parentId", nativeQuery = true)
	List<Org> findByParentId(@Param("parentId") long parentId);

	@Query(value = "select * from sys_org where name like %:name%", nativeQuery = true)
	List<Org> findByNameLike(@Param("name") String name);
}

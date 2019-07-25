package com.acooly.module.security.dao;

import com.acooly.module.jpa.EntityJpaDao;
import com.acooly.module.security.domain.Role;

import java.util.List;
import java.util.Set;

public interface RoleDao extends EntityJpaDao<Role, Long> {

    /**
     * 根据角色名查询角色
     *
     * @param name
     * @return
     */
    Set<Role> findByName(String name);

}

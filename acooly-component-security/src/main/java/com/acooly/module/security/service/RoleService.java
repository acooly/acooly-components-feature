package com.acooly.module.security.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.security.domain.Role;

import java.util.Set;

public interface RoleService extends EntityService<Role> {

    Set<Role> getRole(String name);
}

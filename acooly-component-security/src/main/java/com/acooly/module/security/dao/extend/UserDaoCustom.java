package com.acooly.module.security.dao.extend;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.module.security.dto.UserDto;
import com.acooly.module.security.dto.UserRole;

import java.util.List;
import java.util.Map;

public interface UserDaoCustom {

    PageInfo<UserDto> queryDto(
            PageInfo<UserDto> pageInfo, Map<String, Object> map, Map<String, Boolean> orderMap);

    List<UserRole> getRoleIdsByUserId(Long userId);
}

package com.acooly.module.security.dao.hibernate;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.module.security.domain.User;

import java.util.Map;

public interface UserDaoCustom {

    User findByJdbcTemplate(Long id);

    User findByCustomJap(Long id);

    User findByHibernate4(Long id);

    PageInfo<User> customeQueryWithPagedJdbcTemplate(
            PageInfo<User> pageInfo, Map<String, String> map);
}

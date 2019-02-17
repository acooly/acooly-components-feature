package com.acooly.module.security.dao;

import com.acooly.module.jpa.EntityJpaDao;
import com.acooly.module.security.dao.extend.UserDaoCustom;
import com.acooly.module.security.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends EntityJpaDao<User, Long>, UserDaoCustom {
    User findByUsername(String username);

    @Query("from User where username = ?1 or email = ?1 or mobileNo = ?1")
    User getAuthenticateUser(String key);

    @Modifying
    @Query("update User set loginFailTimes = 0 where username = ?1")
    void clearLoginFailureCount(String username);

    @Modifying
    @Query("update User set loginFailTimes = loginFailTimes + 1 where username = ?1")
    void addLoginFailureCount(String username);
}

package com.acooly.module.security.dao;

import com.acooly.module.jpa.EntityJpaDao;
import com.acooly.module.security.dao.extend.UserDaoCustom;
import com.acooly.module.security.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户 JPA Dao
 *
 * @author zhangpu
 */
@Repository
public interface UserDao extends EntityJpaDao<User, Long>, UserDaoCustom {

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    User findByUsername(String username);


    /**
     * 根据用户名查询用户
     *
     * @param key
     * @return
     */
    @Query("from User where username = ?1")
    User getAuthenticateUser(String key);

    /**
     * 重置用户登录错误次数
     *
     * @param username
     */
    @Modifying
    @Query("update User set loginFailTimes = 0 where username = ?1")
    void clearLoginFailureCount(String username);

    /**
     * 更新用户登录失败次数
     *
     * @param username
     */
    @Modifying
    @Query("update User set loginFailTimes = loginFailTimes + 1 where username = ?1")
    void addLoginFailureCount(String username);

    /**
     * 根据手机号码查询用户
     *
     * @param mobileNo
     * @return
     */
    List<User> findByMobileNo(String mobileNo);

}

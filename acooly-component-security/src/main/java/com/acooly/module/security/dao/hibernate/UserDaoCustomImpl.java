package com.acooly.module.security.dao.hibernate;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.module.security.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Map;

/**
 * Spring3-Hibernate4整合方案，暂时未封装基类
 *
 * @author zhangpu
 */
public class UserDaoCustomImpl implements UserDaoCustom {

    @Autowired
    private JdbcTemplate pagedJdbcTemplate;
    @Autowired
    private EntityManager entityManager;

    @Transactional("transactionManager")
    @Override
    public User findByJdbcTemplate(Long id) {
        String sql = "select * from SYS_USER where id = ?";
        SqlRowSet rowSet = pagedJdbcTemplate.queryForRowSet(sql, new Object[]{id});
        User user = null;
        if (rowSet.next()) {
            user = new User();
            user.setId(rowSet.getLong(1));
            // ......
        }
        return user;
    }

    @Override
    public User findByCustomJap(Long id) {
        throw new UnsupportedOperationException("Spring3-Hibernate4整合方案不支持JAP");
    }

    @Transactional("transactionManager")
    public User findByHibernate4(Long id) {
        User users = entityManager.find(User.class, 1);
        ;
        return users;
    }

    @Override
    public PageInfo<User> customeQueryWithPagedJdbcTemplate(
            PageInfo<User> pageInfo, Map<String, String> map) {
        // TODO Auto-generated method stub
        return null;
    }
}

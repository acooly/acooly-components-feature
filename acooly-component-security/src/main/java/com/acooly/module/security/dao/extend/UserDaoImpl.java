package com.acooly.module.security.dao.extend;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.utils.Strings;
import com.acooly.module.ds.AbstractJdbcTemplateDao;
import com.acooly.module.security.dto.UserDto;
import com.acooly.module.security.dto.UserRole;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * JPA方案中，对特殊需求的DAO的扩展实现DEMO
 *
 *
 *
 * <p>要求： <br>
 * 1.另外定义接口UserDaoCustom <br>
 * 2.实现新定义的扩展接口，但名次必须命名为JAP接口名+Impl后缀
 *
 * @author zhangpu
 */
@Service
public class UserDaoImpl extends AbstractJdbcTemplateDao implements UserDaoCustom {

    @Autowired
    JdbcTemplate pagedJdbcTemplate;

    @Override
    public PageInfo<UserDto> queryDto(
            PageInfo<UserDto> pageInfo, Map<String, Object> map, Map<String, Boolean> orderMap) {
        String sql =
                "select t1.id,t1.USERNAME as userName,t1.REAL_NAME as realName,t1.USER_TYPE as userType,\n"
                        + "t1.EMAIL as email,t1.MOBILE_NO as mobileNo,t1.CREATE_TIME as createTime,t1.LAST_MODIFY_TIME as lastModifyTime,\n"
                        + "t1.UNLOCK_TIME as unlockTime,t3.id as roleId,t3.NAME as roleName,t3.DESCN as roleDescn,\n"
                        + "t1.STATUS as status,t1.ORG_ID as orgId,t1.ORG_NAME as orgName,t1.descn as descn\n"
                        + "from sys_user t1, sys_user_role t2,sys_role t3 where t1.id = t2.user_id and t2.role_id = t3.id ";

        String username = (String) map.get("LIKE_username");
        if (Strings.isNotBlank(username)) {
            sql += " and t1.USERNAME like '%" + username + "%'";
        }

        String realName = (String) map.get("LIKE_realName");
        if (Strings.isNotBlank(realName)) {
            sql += " and t1.REAL_NAME like '%" + realName + "%'";
        }

        String status = (String) map.get("EQ_status");
        if (Strings.isNotBlank(status)) {
            sql += " and t1.STATUS = " + status;
        }
        String userType = (String) map.get("EQ_userType");
        if (Strings.isNotBlank(userType)) {
            sql += " and t1.USER_TYPE = " + userType;
        }

        String roleId = (String) map.get("EQ_role");
        if (Strings.isNotBlank(roleId)) {
            sql += " and t3.id = " + roleId;
        }

        String orgId = (String) map.get("EQ_orgId");
        if (Strings.isNotBlank(orgId)) {
            sql += " and t1.org_id = " + orgId;
        }

        sql += " order by t1.id desc";

        return super.query(pageInfo, sql, UserDto.class);
    }

    @Override
    public List<UserRole> getRoleIdsByUserId(Long userId) {
        List<UserRole> roleIds = Lists.newArrayList();
        String sql = "SELECT * FROM sys_user_role WHERE user_id=?";
        SqlRowSet rs = pagedJdbcTemplate.queryForRowSet(sql, userId);
        while (rs.next()) {
            UserRole ur = new UserRole();
            ur.setUserId(rs.getLong("USER_ID"));
            ur.setRoleId(rs.getLong("ROLE_ID"));
            roleIds.add(ur);
        }
        return roleIds;
    }
}

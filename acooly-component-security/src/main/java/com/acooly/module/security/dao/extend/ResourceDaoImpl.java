package com.acooly.module.security.dao.extend;

import com.acooly.module.jpa.JapDynamicQueryDao;
import com.acooly.module.security.domain.Resource;
import com.acooly.module.security.dto.ResourceNode;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceDaoImpl extends JapDynamicQueryDao<Resource> implements ResourceCustomDao {

    @Autowired
    JdbcTemplate pagedJdbcTemplate;

    @Override
    public List<Resource> getAuthorizedResources(Long userId) {
        String sql =
                "select t1.* from sys_resource t1, sys_role_resc t2, sys_user_role t3 "
                        + "where t1.id = t2.resc_id and t2.role_id = t3.role_id and t1.show_state = 0 and "
                        + "t3.user_id = ?0 order by t1.order_time desc";
        return queryByNativeSql(Resource.class, sql, userId);
    }

    @Override
    public List<ResourceNode> getAuthorizedResourceNodeAsUser(Long userId) {
        List<ResourceNode> resourceNodes = Lists.newArrayList();
        String sql =
                "select t1.* from sys_resource t1, sys_role_resc t2, sys_user_role t3 "
                        + "where t1.id = t2.resc_id and t2.role_id = t3.role_id and "
                        + "t3.user_id = ? order by t1.order_time desc";
        SqlRowSet rs = pagedJdbcTemplate.queryForRowSet(sql, userId);
        while (rs.next()) {
            ResourceNode node = new ResourceNode();
            node.setId(rs.getLong("ID"));
            node.setParentId(rs.getLong("PARENTID"));
            node.setIcon(rs.getString("ICON"));
            node.setName(rs.getString("NAME"));
            node.setOrderTime(rs.getDate("ORDER_TIME"));
            node.setShowMode(rs.getInt("SHOW_MODE"));
            node.setType(rs.getString("TYPE"));
            node.setValue(rs.getString("VALUE"));
            node.setShowState(rs.getInt("SHOW_STATE"));
            node.setChecked(false);
            resourceNodes.add(node);
        }
        return resourceNodes;
    }

    @Override
    public List<Resource> getResourcesByRole(Long roleId) {
        String sql =
                "select t1.* from sys_resource t1, sys_role_resc t2 "
                        + "where t1.id = t2.resc_id and t1.show_state = 0 and "
                        + "t2.role_id = ?0 order by t1.order_time desc";
        return queryByNativeSql(Resource.class, sql, roleId);
    }

    @Override
    public List<Resource> getTopAuthorizedResource(Long userId) {
        String sql =
                "select t1.* "
                        + "from sys_resource t1, sys_role_resc t2, sys_user_role t3 "
                        + "where t1.id = t2.resc_id and t2.role_id = t3.role_id and t1.parentid is null "
                        + "and t1.type = 'MENU' and t1.show_state = 0 and t3.user_id = ?0 order by t1.order_time desc";
        return queryByNativeSql(Resource.class, sql, userId);
    }

    @Override
    public List<ResourceNode> getAuthorizedResourceNodeAsRole(Long roleId) {
        List<ResourceNode> resourceNodes = Lists.newArrayList();
        String sql =
                "select t2.role_id as ROLEID,t1.* from sys_resource t1 left join sys_role_resc t2 "
                        + "on t1.id = t2.resc_id and t2.role_id = ? order by t1.order_time desc";
        SqlRowSet rs = pagedJdbcTemplate.queryForRowSet(sql, roleId);
        while (rs.next()) {
            ResourceNode node = new ResourceNode();
            node.setId(rs.getLong("ID"));
            node.setParentId(rs.getLong("PARENTID"));
            node.setIcon(rs.getString("ICON"));
            node.setName(rs.getString("NAME"));
            node.setOrderTime(rs.getDate("ORDER_TIME"));
            node.setShowMode(rs.getInt("SHOW_MODE"));
            node.setType(rs.getString("TYPE"));
            node.setValue(rs.getString("VALUE"));
            node.setShowState(rs.getInt("SHOW_STATE"));
            node.setChecked(rs.getObject("ROLEID") != null);
            resourceNodes.add(node);
        }
        return resourceNodes;
    }

    @Override
    public List<ResourceNode> getResourcesNodeByRole(Long roleId) {
        List<ResourceNode> resourceNodes = Lists.newArrayList();
        String sql =
                "select t1.* from sys_resource t1, sys_role_resc t2 "
                        + "where t1.id = t2.resc_id and t1.show_state = 0 and "
                        + "t2.role_id = ? order by t1.order_time desc";
        SqlRowSet rs = pagedJdbcTemplate.queryForRowSet(sql, roleId);
        while (rs.next()) {
            ResourceNode node = new ResourceNode();
            node.setId(rs.getLong("ID"));
            node.setParentId(rs.getLong("PARENTID"));
            node.setIcon(rs.getString("ICON"));
            node.setName(rs.getString("NAME"));
            node.setOrderTime(rs.getDate("ORDER_TIME"));
            node.setShowMode(rs.getInt("SHOW_MODE"));
            node.setType(rs.getString("TYPE"));
            node.setValue(rs.getString("VALUE"));
            node.setShowState(rs.getInt("SHOW_STATE"));
            node.setChecked(true);
            resourceNodes.add(node);
        }
        return resourceNodes;
    }
}

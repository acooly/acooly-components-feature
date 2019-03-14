package com.acooly.module.eav.dao.impl;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.dao.support.SearchFilter;
import com.acooly.core.common.type.DBMap;
import com.acooly.module.eav.dao.EavEntityDynamicDao;
import com.acooly.module.eav.entity.EavEntity;
import com.google.common.collect.Lists;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhangpu
 * @date 2019-03-13 17:42
 */
@Slf4j
@Repository
public class EavEntityDynamicDaoImpl extends AbstractJdbcTemplateDao implements EavEntityDynamicDao {

    /**
     * RowMapper实现
     */
    private EavEntityDynamicRowMapper eavEntityDynamicRowMapper = new EavEntityDynamicRowMapper();

    /**
     * 非动态属性
     */
    private static final List<String> ENTITY_COMMON_ATTRS =
            Lists.newArrayList(new String[]{"id", "createTime", "updateTime", "memo", "schemeId", "schemeName", "schemeTitle"});

    @Override
    public List<EavEntity> list(Map<String, Object> map, Map<String, Boolean> orderMap) {
        return super.queryForList(buildSql(map, orderMap), EavEntity.class, eavEntityDynamicRowMapper);
    }

    @Override
    public PageInfo<EavEntity> query(PageInfo<EavEntity> pageInfo, Map<String, Object> map, Map<String, Boolean> orderMap) {
        return super.query(pageInfo, buildSql(map, orderMap), EavEntity.class, eavEntityDynamicRowMapper);
    }

    protected String buildSql(Map<String, Object> parameters, Map<String, Boolean> sortMap) {

        Assert.notEmpty(parameters, "查询参数不能为空，至少包含schemeId");

        Long schemeId = (Long) parameters.get("schemeId");
        if (schemeId == null && parameters.get("EQ_schemeId") != null) {
            schemeId = Long.valueOf(parameters.get("EQ_schemeId").toString());
        }
        Assert.notEmpty(parameters, "schemeId不能为空");
        parameters.remove("EQ_schemeId");
        parameters.remove("schemeId");
        StringBuilder sql = new StringBuilder("SELECT * FROM eav_entity WHERE scheme_id=");
        sql.append(schemeId);

        // 动态查询条件
        List<SearchFilter> searchFilters = SearchFilter.parse(parameters);
        for (SearchFilter searchFilter : searchFilters) {
            if (ENTITY_COMMON_ATTRS.contains(searchFilter.fieldName)) {
                sql.append(" and ").append(searchFilter.fieldName).append("'").append(operatorParse(searchFilter));
            }
            sql.append(" and value->'$.").append(searchFilter.fieldName).append("'").append(operatorParse(searchFilter));
        }

        // 排序
        if (sortMap != null && sortMap.size() > 0) {
            sql.append(" order by");
            int index = 1;
            for (Map.Entry<String, Boolean> entry : sortMap.entrySet()) {
                sql.append(" ").append(entry.getKey()).append(" ").append((entry.getValue() ? "asc" : "desc"));
                if (index++ < sortMap.size()) {
                    sql.append(",");
                }
            }
        } else {
            sql.append(" order by id desc");
        }

        return sql.toString();
    }

    protected String operatorParse(SearchFilter searchFilter) {
        StringBuilder sb = new StringBuilder();
        Object value = searchFilter.value;
        switch (searchFilter.operator) {
            case EQ:
                if (value instanceof String || value instanceof Date) {
                    sb.append(" = '" + value + "'");
                } else {
                    sb.append(" = " + value);
                }
                break;
            case NEQ:
                if (value instanceof String) {
                    sb.append(" != '" + value + "'");
                } else {
                    sb.append(" != " + value);
                }
                break;
            case LIKE:
                sb.append(" like '%" + value + "%'");
                break;
            case LLIKE:
                sb.append(" like '%" + value + "'");
                break;
            case RLIKE:
                sb.append(" like '" + value + "%'");
                break;
            case GT:
                if (value instanceof String || value instanceof Date) {
                    sb.append(" > '" + value + "'");
                } else {
                    sb.append(" > " + value);
                }
                break;
            case LT:
                if (value instanceof String || value instanceof Date) {
                    sb.append(" < '" + value + "'");
                } else {
                    sb.append(" < " + value);
                }
                break;
            case GTE:
                if (value instanceof String || value instanceof Date) {
                    sb.append(" >= '" + value + "'");
                } else {
                    sb.append(" >= " + value);
                }
                break;
            case LTE:
                if (value instanceof String || value instanceof Date) {
                    sb.append(" <= '" + value + "'");
                } else {
                    sb.append(" <= " + value);
                }
                break;
            case NULL:
                sb.append(" is null ");
                break;
            case NOTNULL:
                sb.append(" is not null ");
                break;
        }
        return sb.toString();
    }


    static class EavEntityDynamicRowMapper implements RowMapper<EavEntity> {
        @Override
        public EavEntity mapRow(ResultSet rs, int i) throws SQLException {
            EavEntity eavEntity = new EavEntity();
            eavEntity.setId(rs.getLong("id"));
            eavEntity.setSchemeId(rs.getLong("scheme_id"));
            eavEntity.setSchemeName(rs.getString("scheme_name"));
            eavEntity.setValue(DBMap.fromJson(rs.getString("value")));
            eavEntity.setCreateTime(rs.getDate("create_time"));
            eavEntity.setUpdateTime(rs.getDate("update_time"));
            return eavEntity;
        }
    }
}

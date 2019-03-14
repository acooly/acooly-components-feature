package com.acooly.module.eav.dao.impl;

import com.acooly.core.common.dao.support.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author qiuboboy@qq.com
 * @date 2019-03-13 18:29
 */
@Slf4j
public abstract class AbstractJdbcTemplateDao {
    private static final String ORACLE_PAGESQL_TEMPLATE = "SELECT * FROM (SELECT XX.*,rownum ROW_NUM FROM (${sql}) XX ) ZZ where ZZ.ROW_NUM BETWEEN ${startNum} AND ${endNum}";
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    private com.acooly.module.ds.AbstractJdbcTemplateDao.DbType dbType;

    public AbstractJdbcTemplateDao() {
    }

    public static String getJdbcUrlFromDataSource(DataSource dataSource) {
        Connection connection = null;

        String var2;
        try {
            connection = dataSource.getConnection();
            if (connection == null) {
                throw new IllegalStateException("Connection returned by DataSource [" + dataSource + "] was null");
            }

            var2 = connection.getMetaData().getURL();
        } catch (SQLException var11) {
            throw new RuntimeException("Could not get database url", var11);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException var10) {
                    ;
                }
            }

        }

        return var2;
    }

    public <T> PageInfo<T> query(PageInfo<T> pageInfo, String sql, Class<T> requiredType, RowMapper<T> rowMapper) {
        com.acooly.module.ds.AbstractJdbcTemplateDao.DbType dbType = this.getDbType();
        if (dbType == com.acooly.module.ds.AbstractJdbcTemplateDao.DbType.mysql) {
            return this.queryMySql(pageInfo, sql, requiredType, rowMapper);
        } else if (dbType == com.acooly.module.ds.AbstractJdbcTemplateDao.DbType.oracle) {
            return this.queryOracle(pageInfo, sql, requiredType, rowMapper);
        } else {
            throw new UnsupportedOperationException("不支持[" + dbType + "]的分页查询");
        }
    }

    public <T> PageInfo<T> query(PageInfo<T> pageInfo, String sql, Class<T> requiredType) {
        return query(pageInfo, sql, requiredType, null);
    }

    protected com.acooly.module.ds.AbstractJdbcTemplateDao.DbType getDbType() {
        if (this.dbType == null) {
            synchronized (this) {
                if (this.dbType == null) {
                    String jdbcUrl = getJdbcUrlFromDataSource(this.jdbcTemplate.getDataSource());
                    if (jdbcUrl.contains(":mysql:")) {
                        this.dbType = com.acooly.module.ds.AbstractJdbcTemplateDao.DbType.mysql;
                    } else if (jdbcUrl.contains(":oracle:")) {
                        this.dbType = com.acooly.module.ds.AbstractJdbcTemplateDao.DbType.oracle;
                    } else {
                        this.dbType = com.acooly.module.ds.AbstractJdbcTemplateDao.DbType.mysql;
                    }
                }
            }
        }

        return this.dbType;
    }

    protected <T> List<T> queryForList(String sql, Class<T> elementType) throws DataAccessException {
        return queryForList(sql, elementType, null);
    }

    protected <T> List<T> queryForList(String sql, Class<T> elementType, RowMapper<T> rowMapper) throws DataAccessException {

        if (rowMapper != null) {
            return jdbcTemplate.query(sql, rowMapper);
        }

        if (elementType.isAssignableFrom(Map.class)) {
            List<Map<String, Object>> result = this.jdbcTemplate.queryForList(sql);
            return (List<T>) result;
        }

        return this.jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(elementType));

    }


    private <T> PageInfo<T> queryOracle(PageInfo<T> pageInfo, String sql, Class<T> requiredType, RowMapper<T> rowMapper) {
        String orderBy = "";
        if (StringUtils.contains(sql, "order by")) {
            orderBy = sql.substring(sql.indexOf("order by"));
            sql = sql.substring(0, sql.indexOf("order by"));
        }

        String sqlCount = "select count(*) from (" + sql + ") as xxttbb";
        long totalCount = this.getCount(sqlCount);
        long totalPage = totalCount % (long) pageInfo.getCountOfCurrentPage() == 0L ? totalCount / (long) pageInfo.getCountOfCurrentPage() : totalCount / (long) pageInfo.getCountOfCurrentPage() + 1L;
        pageInfo.setTotalCount(totalCount);
        pageInfo.setTotalPage(totalPage);
        String pageSql = "SELECT * FROM (SELECT XX.*,rownum ROW_NUM FROM (${sql}) XX ) ZZ where ZZ.ROW_NUM BETWEEN ${startNum} AND ${endNum} " + orderBy;
        int startNum = pageInfo.getCountOfCurrentPage() * (pageInfo.getCurrentPage() - 1) + 1;
        int endNum = pageInfo.getCountOfCurrentPage() * pageInfo.getCurrentPage();
        if ((long) endNum > totalCount) {
            endNum = (int) totalCount;
        }

        pageSql = StringUtils.replace(pageSql, "${sql}", sql);
        pageSql = StringUtils.replace(pageSql, "${startNum}", String.valueOf(startNum));
        pageSql = StringUtils.replace(pageSql, "${endNum}", String.valueOf(endNum));
        List<T> result = this.queryForList(pageSql, requiredType, rowMapper);
        pageInfo.setPageResults(result);
        return pageInfo;
    }

    private <T> PageInfo<T> queryOracle(PageInfo<T> pageInfo, String sql, Class<T> requiredType) {
        return queryOracle(pageInfo, sql, requiredType, null);
    }

    private <T> PageInfo<T> queryMySql(PageInfo<T> pageInfo, String sql, Class<T> dtoEntity, RowMapper<T> rowMapper) {
        try {
            String sqlCount = "select count(*) from (" + sql + ") as xxttbb";
            long totalCount = this.getCount(sqlCount);
            int startNum = pageInfo.getCountOfCurrentPage() * (pageInfo.getCurrentPage() - 1);
            int endNum = pageInfo.getCountOfCurrentPage() * pageInfo.getCurrentPage();
            if ((long) endNum > totalCount) {
                endNum = (int) totalCount;
            }

            long totalPage = totalCount % (long) pageInfo.getCountOfCurrentPage() == 0L ? totalCount / (long) pageInfo.getCountOfCurrentPage() : totalCount / (long) pageInfo.getCountOfCurrentPage() + 1L;
            String pageSql = sql + " limit " + startNum + "," + pageInfo.getCountOfCurrentPage();
            List<T> result = this.queryForList(pageSql, dtoEntity, rowMapper);
            pageInfo.setPageResults(result);
            pageInfo.setTotalCount(totalCount);
            pageInfo.setTotalPage(totalPage);
            return pageInfo;
        } catch (Exception var13) {
            throw new RuntimeException(var13);
        }
    }

    private <T> PageInfo<T> queryMySql(PageInfo<T> pageInfo, String sql, Class<T> dtoEntity) {
        return queryMySql(pageInfo, sql, dtoEntity, null);
    }

    private long getCount(String sqlCount) {
        return (Long) this.jdbcTemplate.queryForObject(sqlCount, Long.class);
    }

    public static enum DbType {
        mysql,
        oracle;

        private DbType() {
        }
    }
}

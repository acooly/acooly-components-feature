package com.acooly.module.syncdata.openapi.service.impl;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.module.ds.AbstractJdbcTemplateDao;
import com.acooly.module.syncdata.common.enums.QueryColumnTypeEnum;
import com.acooly.module.syncdata.common.enums.QueryTypeEnum;
import com.acooly.module.syncdata.openapi.service.AsyncDataService;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Slf4j
@Service("asyncDataService")
public class AsyncDataServiceImpl extends AbstractJdbcTemplateDao implements AsyncDataService {


    @Autowired
    protected JdbcTemplate jdbcTemplate;


    public PageInfo<Object> query(PageInfo<Object> pageInfo, String tableName, String queryColumn, String queryColumnValue, QueryTypeEnum queryType, QueryColumnTypeEnum queryColumnType) {
        String sql = assemblySql(tableName, queryColumn, queryColumnValue, queryType, queryColumnType, pageInfo.getCountOfCurrentPage());
        pageInfo = super.query(pageInfo, sql, Object.class);
        return pageInfo;
    }

    /**
     * 查询单表记录
     *
     * @param tableName
     * @param rowsDataJson
     * @return
     */
    @Override
    public boolean findData(String tableName, String primaryColumnName, JSONObject rowsDataJson) {
        String primaryColumnValue = rowsDataJson.get(primaryColumnName).toString();
        String sql = "select * from " + tableName + "  where " + primaryColumnName + " =";
        String whereSql = primaryColumnValue;
        if (!primaryColumnValue.toLowerCase().equals("id")) {
            whereSql = " '" + primaryColumnValue + "'";
        }
        List<Map<String, Object>> lists = jdbcTemplate.queryForList(sql + whereSql);
        return (lists.size() > 0) ? true : false;
    }


    /**
     * 更新数据
     *
     * @param tableName
     * @param rowsDataJson
     */
    @Override
    public void updateData(String tableName, String primaryColumnName, JSONObject rowsDataJson) {
        String primaryColumnValue = rowsDataJson.get(primaryColumnName).toString();

        //判断 主键 是否为 id
        boolean isId = primaryColumnValue.toLowerCase().equals("id");

        StringBuffer sbSql = new StringBuffer();
        sbSql.append("update  " + tableName + " set ");
        Set<String> rowsDataKeySet = rowsDataJson.keySet();
        int size = rowsDataKeySet.size();
        int i = 1;
        for (String rowsKey : rowsDataKeySet) {
            //不是ID，不能更新ID字段
            if (!isId) {
                if (rowsKey.toLowerCase().equals("id")) {
                    size = size - 1;
                    continue;
                }
            }

            sbSql.append(rowsKey + " = '" + rowsDataJson.get(rowsKey) + "'");
            if (i < size) {
                sbSql.append(",");
            }
            i = i + 1;
        }
        //主键是id
        sbSql.append(" where " + primaryColumnName + " = ");
        String whereSql = primaryColumnValue;

        //主键不是id
        if (!isId) {
            whereSql = "'" + primaryColumnValue + "'";
        }

        sbSql.append(whereSql);
        log.info("数据同步[更新数据],执行sql：{}", sbSql.toString());
        jdbcTemplate.execute(sbSql.toString());
    }

    /**
     * 新增数据
     *
     * @param tableName
     * @param rowsDataJson
     */
    @Override
    public void insertData(String tableName, String primaryColumnName, JSONObject rowsDataJson) {
        //主键为表
        String primaryKey = primaryColumnName.toLowerCase();
        StringBuffer sbKeySql = new StringBuffer();
        sbKeySql.append("insert into " + tableName + " ( ");
        StringBuffer sbValuesSql = new StringBuffer();
        Set<String> rowsDataKeySet = rowsDataJson.keySet();
        List<String> values = Lists.newLinkedList();
        int size = rowsDataKeySet.size();
        int i = 1;
        for (String rowsKey : rowsDataKeySet) {
            String rowsKeyLowerCase = rowsKey.toLowerCase();
            //表的主键为ID
            if (rowsKeyLowerCase.equals(primaryKey) && "id".equals(rowsKeyLowerCase)) {
                sbKeySql.append("`" + rowsKey + "`");
                sbValuesSql.append("'" + rowsDataJson.get(rowsKey) + "'");
                if (i < size) {
                    sbKeySql.append(",");
                    sbValuesSql.append(",");
                }
            } else {
                //表的主键 不是ID
                if (rowsKey.toLowerCase().equals("id")) {
                    size = size - 1;
                    continue;
                }
                sbKeySql.append("`" + rowsKey + "`");
                sbValuesSql.append("'" + rowsDataJson.get(rowsKey) + "'");
                if (i < size) {
                    sbKeySql.append(",");
                    sbValuesSql.append(",");
                }
            }
            i = i + 1;
        }
        sbKeySql.append(" )value( ");
        sbKeySql.append(sbValuesSql.toString());
        sbKeySql.append(" )");
        log.info("数据同步[新增数据],执行sql：{}", sbKeySql.toString());
        jdbcTemplate.execute(sbKeySql.toString());
    }


    /**
     * 查询 同步数据
     *
     * @param tableName
     * @param queryColumn
     * @param queryColumnType
     * @param queryRows
     * @return
     */
    public String assemblySql(String tableName, String queryColumn, String queryColumnValue, QueryTypeEnum queryType, QueryColumnTypeEnum queryColumnType, long queryRows) {
        StringBuffer sbSql = new StringBuffer();
        sbSql.append("select * from ");
        sbSql.append(tableName);
        sbSql.append("  where 1=1 ");
        sbSql.append("  and " + queryColumn);
        sbSql.append(queryType.getOperatorCode());
        if (queryColumnType == QueryColumnTypeEnum.type_long) {
            sbSql.append(queryColumnValue);
        } else {
            sbSql.append(" '" + queryColumnValue + "' ");
        }
        sbSql.append(" order by  " + queryColumn + "  asc");
        log.info("数据同步，执行sql：{}", sbSql.toString());
        return sbSql.toString();
    }


    /**
     * 获取json 表Id
     *
     * @param rowsDataJson
     * @return
     */
    private String getTableIdKey(JSONObject rowsDataJson) {
        String idStr = "id";
        if (rowsDataJson.get(idStr) == null) {
            idStr = "ID";
        }
        return idStr;
    }
}

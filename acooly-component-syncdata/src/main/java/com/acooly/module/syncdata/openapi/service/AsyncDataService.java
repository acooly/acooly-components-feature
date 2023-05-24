package com.acooly.module.syncdata.openapi.service;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.module.syncdata.common.enums.QueryColumnTypeEnum;
import com.acooly.module.syncdata.common.enums.QueryTypeEnum;
import com.alibaba.fastjson.JSONObject;

/**
 * 数据同步服务
 */
public interface AsyncDataService {


    /**
     * 查询 同步数据
     *
     * @return
     */
    public PageInfo<Object> query(PageInfo<Object> pageInfo, String tableName, String queryColumn, String queryColumnValue, QueryTypeEnum queryType, QueryColumnTypeEnum queryColumnType);


    /**
     * 查询单表记录
     * @param tableName
     * @return
     */
    boolean findData(String tableName, JSONObject rowsDataJson);


    /**
     * 更新数据
     * @param tableName
     * @param rowsDataJson
     */
    void updateData(String tableName, JSONObject rowsDataJson);

    /**
     * 新增数据
     * @param tableName
     * @param rowsDataJson
     */
    void insertData(String tableName, JSONObject rowsDataJson);

}

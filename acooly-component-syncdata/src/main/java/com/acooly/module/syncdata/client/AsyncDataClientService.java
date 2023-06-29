package com.acooly.module.syncdata.client;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.module.syncdata.SyncDataProperties;
import com.acooly.module.syncdata.manage.entity.TableAsyncData;
import com.acooly.module.syncdata.manage.service.TableAsyncDataService;
import com.acooly.module.syncdata.openapi.message.AsyncDataApiRequest;
import com.acooly.module.syncdata.openapi.message.AsyncDataApiResponse;
import com.acooly.module.syncdata.openapi.service.AsyncDataService;
import com.acooly.openapi.framework.client.OpenApiClientProperties;
import com.acooly.openapi.framework.common.OpenApiTools;
import com.acooly.openapi.framework.common.enums.ApiServiceResultCode;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service("asyncDataClientService")
public class AsyncDataClientService {

    @Autowired
    private TableAsyncDataService tableAsyncDataService;

    @Autowired
    private OpenApiClientProperties openApiClientProperties;

    @Autowired
    private SyncDataProperties syncDataProperties;

    @Autowired
    private AsyncDataService asyncDataService;


    /**
     * 默认
     */
    public void doAsyncTableData() {
        List<TableAsyncData> tableAsyncDataList = tableAsyncDataService.findByType("default");
        for (TableAsyncData tableAsyncData : tableAsyncDataList) {
            doAsyncTableDataByPage(tableAsyncData, 1);
        }
    }


    /**
     * 默认所有的业务枚举类
     */
    public void doAsyncTableDataByAllType() {
        Map<String, String> maps = syncDataProperties.getBusiTypeEnum();
        Set<String> mapKeys = maps.keySet();
        for (String mapKey : mapKeys) {
            List<TableAsyncData> tableAsyncDataList = tableAsyncDataService.findByType(mapKey);
            for (TableAsyncData tableAsyncData : tableAsyncDataList) {
                doAsyncTableDataByPage(tableAsyncData, 1);
            }
        }
    }

    /**
     * 同步表数据
     * <p>
     * 基于acooly.openapi.client配置文件，详情参考类 OpenApiClientProperties
     */
    public void doAsyncTableData(String type) {
        List<TableAsyncData> tableAsyncDataList = tableAsyncDataService.findByType(type);
        for (TableAsyncData tableAsyncData : tableAsyncDataList) {
            doAsyncTableDataByPage(tableAsyncData, 1);
        }
    }

    /**
     * 分页查询
     *
     * @param tableAsyncData
     * @param start
     */
    public void doAsyncTableDataByPage(TableAsyncData tableAsyncData, int start) {
        try {
            //请求对象
            AsyncDataApiRequest request = new AsyncDataApiRequest();
            request.setService("asyncData");
            request.setTableName(tableAsyncData.getTableName());
            request.setQueryType(tableAsyncData.getQueryType());
            request.setQueryColumnType(tableAsyncData.getQueryColumnType());
            request.setQueryColumnName(tableAsyncData.getQueryColumnName());
            request.setQueryColumnValue(tableAsyncData.getQueryColumnValue());
            request.setStart(start);
            request.setLimit(tableAsyncData.getQueryRows());

            //请求OpenApi
            OpenApiTools openApiTools = new OpenApiTools(openApiClientProperties.getGateway(), openApiClientProperties.getAccessKey(), openApiClientProperties.getSecretKey());
            AsyncDataApiResponse response = openApiTools.send(request, AsyncDataApiResponse.class);

            //数据解析
            //总页数
            long totalPages = response.getTotalPages();
            //总行数
            long totalRows = response.getTotalRows();
            //页数据
            List<Object> resultList = response.getRows();

            String lastColumnValue = tableAsyncData.getQueryColumnValue();
            for (Object rowsData : resultList) {
                //json 行数据
                JSONObject rowsDataJson = JSON.parseObject(rowsData.toString());

                String tableName = tableAsyncData.getTableName();
                String primaryColumnName = tableAsyncData.getPrimaryColumnName();
                boolean isData = asyncDataService.findData(tableName, primaryColumnName, rowsDataJson);
                if (isData) {
                    asyncDataService.updateData(tableName, primaryColumnName, rowsDataJson);
                } else {
                    asyncDataService.insertData(tableName, primaryColumnName, rowsDataJson);
                }
                lastColumnValue = rowsDataJson.get(tableAsyncData.getQueryColumnName()).toString();
            }
            log.info("数据同步：表名:【{}】,查询字段值:【{}】,当前页码:【{}】,总页数:【{}】,最后行字段值：【{}】", tableAsyncData.getTableName(), tableAsyncData.getQueryColumnValue(), start, totalPages, lastColumnValue);

            if (start < totalPages) {
                doAsyncTableDataByPage(tableAsyncData, start + 1);
            } else {
                //更新最后查询值
                tableAsyncData.setQueryColumnValue(lastColumnValue);
                tableAsyncDataService.update(tableAsyncData);
            }
        } catch (Exception e) {
            log.error("数据同步：失败：{}", e);
            throw new BusinessException(ApiServiceResultCode.FAILURE.code(), ApiServiceResultCode.FAILURE.message(), "数据同步失败");
        }
    }


}

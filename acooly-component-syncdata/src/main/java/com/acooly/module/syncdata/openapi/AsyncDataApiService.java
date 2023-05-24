package com.acooly.module.syncdata.openapi;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.module.syncdata.openapi.message.AsyncDataApiRequest;
import com.acooly.module.syncdata.openapi.message.AsyncDataApiResponse;
import com.acooly.module.syncdata.openapi.service.AsyncDataService;
import com.acooly.openapi.framework.common.annotation.ApiDocNote;
import com.acooly.openapi.framework.common.annotation.ApiDocType;
import com.acooly.openapi.framework.common.annotation.OpenApiService;
import com.acooly.openapi.framework.common.enums.ApiBusiType;
import com.acooly.openapi.framework.core.service.base.BaseApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Slf4j
@ApiDocNote("数据同步")
@ApiDocType(code = "asyncData", name = "数据同步")
@OpenApiService(name = "asyncData", desc = "数据同步", owner = "cuifuq", busiType = ApiBusiType.Query)
public class AsyncDataApiService extends BaseApiService<AsyncDataApiRequest, AsyncDataApiResponse> {


    @Autowired
    private AsyncDataService asyncDataService;

    @Override
    protected void doService(AsyncDataApiRequest request, AsyncDataApiResponse response) {
        response.setQueryStartTime(new Date());

        PageInfo<Object> pageInfo = new PageInfo<>();
        pageInfo.setCurrentPage(request.getStart());
        pageInfo.setCountOfCurrentPage(request.getLimit());
        PageInfo<Object> queryDataResult = asyncDataService.query(pageInfo, request.getTableName(), request.getQueryColumnName(), request.getQueryColumnValue(), request.getQueryType(), request.getQueryColumnType());

        response.setTotalPages(queryDataResult.getTotalPage());
        response.setTotalRows(queryDataResult.getTotalCount());
        response.setRows(queryDataResult.getPageResults());
    }
}

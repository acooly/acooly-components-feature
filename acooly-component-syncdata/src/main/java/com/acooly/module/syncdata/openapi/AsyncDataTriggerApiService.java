package com.acooly.module.syncdata.openapi;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.module.syncdata.client.AsyncDataClientService;
import com.acooly.module.syncdata.openapi.message.AsyncDataApiRequest;
import com.acooly.module.syncdata.openapi.message.AsyncDataApiResponse;
import com.acooly.module.syncdata.openapi.message.AsyncDataTriggerApiRequest;
import com.acooly.module.syncdata.openapi.message.AsyncDataTriggerApiResponse;
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
@ApiDocNote("通知客户端，立即触发数据同步功能")
@ApiDocType(code = "asyncData", name = "数据同步")
@OpenApiService(name = "asyncDataTrigger", desc = "使用者：服务端；通知客户端，立即触发数据同步功能", owner = "cuifuq", busiType = ApiBusiType.Query)
public class AsyncDataTriggerApiService extends BaseApiService<AsyncDataTriggerApiRequest, AsyncDataTriggerApiResponse> {


    @Autowired
    private AsyncDataClientService asyncDataClientService;

    @Override
    protected void doService(AsyncDataTriggerApiRequest request, AsyncDataTriggerApiResponse response) {
        asyncDataClientService.doAsyncTableData(request.getBusiType());
    }
}

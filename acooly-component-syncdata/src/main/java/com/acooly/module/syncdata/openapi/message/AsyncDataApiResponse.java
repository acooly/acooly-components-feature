package com.acooly.module.syncdata.openapi.message;

import com.acooly.openapi.framework.common.annotation.OpenApiField;
import com.acooly.openapi.framework.common.message.ApiResponse;
import com.acooly.openapi.framework.common.message.PageApiResponse;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 数据同步响应
 */
@Getter
@Setter
public class AsyncDataApiResponse extends PageApiResponse<Object> {


    @OpenApiField(desc = "queryDataResult", constraint = "查询时间" ,demo = "2023-04-26 15:56:00" , ordinal = 2)
    private Date queryStartTime;

//    @OpenApiField(desc = "queryDataResult", constraint = "最后字段值" ,demo = "2023-04-26 15:56:00" , ordinal = 2)
//    private String lastColumeValue;

//    @OpenApiField(desc = "queryDataResult", constraint = "查询数据结果" ,demo = "json格式" , ordinal = 4)
//    private String queryDataResult;

}

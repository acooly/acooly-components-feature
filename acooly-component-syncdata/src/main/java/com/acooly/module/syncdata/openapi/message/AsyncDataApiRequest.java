package com.acooly.module.syncdata.openapi.message;

import com.acooly.module.syncdata.common.enums.QueryColumnTypeEnum;
import com.acooly.module.syncdata.common.enums.QueryTypeEnum;
import com.acooly.openapi.framework.common.annotation.OpenApiField;
import com.acooly.openapi.framework.common.message.ApiRequest;
import com.acooly.openapi.framework.common.message.PageApiRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * 数据同步请求
 */
@Getter
@Setter
public class AsyncDataApiRequest extends PageApiRequest {

    @NotBlank
    @OpenApiField(desc = "tableName", constraint = "表名" ,demo = "user_info" , ordinal = 2)
    private String tableName;

    @NotNull
    @OpenApiField(desc = "queryType", constraint = "类型" ,demo = "=" , ordinal = 3)
    private QueryTypeEnum queryType;

    @NotNull
    @OpenApiField(desc = "queryColumnType", constraint = "类型" ,demo = "date" , ordinal = 5)
    private QueryColumnTypeEnum queryColumnType;

    @NotBlank
    @OpenApiField(desc = "queryColumn", constraint = "查询字段" ,demo = "update_time" , ordinal = 7)
    private String queryColumnName;

    @NotBlank
    @OpenApiField(desc = "queryColumn", constraint = "查询字段" ,demo = "2023-10-10 23:23:59" , ordinal = 9)
    private String queryColumnValue;




}

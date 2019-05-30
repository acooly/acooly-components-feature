package com.acooly.module.cms.openapi.response;

import com.acooly.module.cms.dto.ContentInfo;
import com.acooly.openapi.framework.common.annotation.OpenApiField;
import com.acooly.openapi.framework.common.message.ApiResponse;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhangpu
 * @date 2019-01-02 09:51
 */
@Data
public class ContentInfoResponse extends ApiResponse {

    @OpenApiField(desc = "内容信息对象", constraint = "内容详情信息", ordinal = 1)
    @NotNull
    private ContentInfo contentInfo;
}

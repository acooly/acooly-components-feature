package com.acooly.module.syncdata.openapi.message;

import com.acooly.openapi.framework.common.annotation.OpenApiField;
import com.acooly.openapi.framework.common.message.PageApiRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


/**
 * 快速数据同步请求
 *
 * 通知客户端，立即触发数据同步功能
 */
@Getter
@Setter
public class AsyncDataTriggerApiRequest extends PageApiRequest {

    @NotBlank
    @OpenApiField(desc = "业务类型", constraint = "业务类型：需要同步数据的枚举值" ,demo = "=" , ordinal = 1)
    private String busiType;

}

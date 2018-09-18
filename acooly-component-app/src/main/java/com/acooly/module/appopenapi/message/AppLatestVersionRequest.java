package com.acooly.module.appopenapi.message;


import com.acooly.openapi.framework.common.annotation.OpenApiField;
import com.acooly.openapi.framework.common.message.ApiRequest;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 客户最新版本 请求报文
 *
 * @author zhangpu
 * @note 格式：HTTP-POST格式,如：name=xxx&amp;age=12
 */
@Getter
@Setter
public class AppLatestVersionRequest extends ApiRequest {

    @OpenApiField(desc = "APP编码", constraint = "APP唯一标志,默认为:woldd")
    private String appCode = "woldd";

    @NotEmpty
    @OpenApiField(desc = "设备类型", constraint = "可选值:android和iphone")
    private String deviceType;
}

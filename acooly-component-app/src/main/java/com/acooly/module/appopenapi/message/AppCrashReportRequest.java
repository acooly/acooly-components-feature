/**
 * create by zhangpu date:2015年9月11日
 */
package com.acooly.module.appopenapi.message;


import com.acooly.openapi.framework.common.annotation.OpenApiField;
import com.acooly.openapi.framework.common.message.ApiRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

/**
 * APP崩溃上报 请求
 *
 * @author zhangpu
 * @date 2015年9月11日
 */
@Getter
@Setter
public class AppCrashReportRequest extends ApiRequest {

    @Size(min = 0, max = 32)
    @OpenApiField(desc = "应用名称")
    private String appName;

    @Size(min = 0, max = 16)
    @OpenApiField(desc = "用户名", constraint = "如果用户已登录，则填写")
    private String userName;

    @Size(min = 0, max = 32)
    @OpenApiField(desc = "平台ID")
    private String platformId;

    @Size(min = 0, max = 16)
    @OpenApiField(desc = "Android版本号")
    private String androidVersion;

    @Size(min = 0, max = 16)
    @OpenApiField(desc = "应用的版本代码")
    private String appVersionCode;

    @Size(min = 0, max = 16)
    @OpenApiField(desc = "应用的版本名称")
    private String appVersionName;

    @Size(min = 0, max = 64)
    @OpenApiField(desc = "设备ID")
    private String deviceId;

    @Size(min = 0, max = 32)
    @OpenApiField(desc = "手机/平板的模型")
    private String model;

    @Size(min = 0, max = 32)
    @OpenApiField(desc = "品牌")
    private String brand;

    @Size(min = 0, max = 255)
    @OpenApiField(desc = "Android产品信息")
    private String product;

    @Size(min = 0, max = 128)
    @OpenApiField(desc = "应用的包名")
    private String packageName;

    @OpenApiField(desc = "崩溃的堆栈信息", constraint = "数据格式要求先做base64")
    private String stackTrace;
}

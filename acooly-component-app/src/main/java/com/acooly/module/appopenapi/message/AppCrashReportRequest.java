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
    @OpenApiField(desc = "应用名称", demo = "acoolyPay", ordinal = 1)
    private String appName;

    @Size(min = 0, max = 16)
    @OpenApiField(desc = "用户名", constraint = "如果用户已登录，则填写", demo = "acooly", ordinal = 2)
    private String userName;

    @Size(min = 0, max = 32)
    @OpenApiField(desc = "平台ID", demo = "afintech", ordinal = 3)
    private String platformId;

    @Size(min = 0, max = 16)
    @OpenApiField(desc = "Android版本号", demo = "1.2.0", ordinal = 4)
    private String androidVersion;

    @Size(min = 0, max = 16)
    @OpenApiField(desc = "应用的版本代码", demo = "120", ordinal = 5)
    private String appVersionCode;

    @Size(min = 0, max = 16)
    @OpenApiField(desc = "应用的版本名称", demo = "acoolyPay-1.2.0", ordinal = 6)
    private String appVersionName;

    @Size(min = 0, max = 64)
    @OpenApiField(desc = "设备ID", demo = "asdf2134asdf2134", ordinal = 7)
    private String deviceId;

    @Size(min = 0, max = 32)
    @OpenApiField(desc = "手机/平板的模型", demo = "xxx", ordinal = 8)
    private String model;

    @Size(min = 0, max = 32)
    @OpenApiField(desc = "品牌", demo = "HUAWEI", ordinal = 9)
    private String brand;

    @Size(min = 0, max = 255)
    @OpenApiField(desc = "Android产品信息", demo = "AndroidInfo", ordinal = 10)
    private String product;

    @Size(min = 0, max = 128)
    @OpenApiField(desc = "应用的包名", demo = "com.acooly.andriod.afintech", ordinal = 11)
    private String packageName;

    @OpenApiField(desc = "崩溃的堆栈信息", constraint = "数据格式要求先做base64", demo = "YXNkZmFzZGZhc2RmYXNkZmFzZGZhc2Rm", ordinal = 12)
    private String stackTrace;
}

package com.acooly.module.appopenapi.message;


import com.acooly.openapi.framework.common.annotation.OpenApiField;
import com.acooly.openapi.framework.common.message.ApiResponse;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 客户最新版本 响应报文
 *
 * @author zhangpu
 */
@Getter
@Setter
public class AppLatestVersionResponse extends ApiResponse {
    @OpenApiField(desc = "应用编码")
    private String appCode;

    @OpenApiField(desc = "应用名称")
    private String appName;

    @NotEmpty
    @OpenApiField(desc = "设备类型", constraint = "可选值:android和iphone")
    private String deviceType;

    @NotNull
    @OpenApiField(desc = "版本编码", constraint = "系统返回最大的版本编码，表示最新版本。")
    private int versionCode;

    @NotEmpty
    @OpenApiField(desc = "版本号", constraint = "版本编码对应的版本号，用于显示")
    private String versionName;

    @NotEmpty
    @OpenApiField(desc = "版本说明", constraint = "版本更新说明，可用于changelog")
    private String subject;

    @NotEmpty
    @OpenApiField(desc = "下载地址", constraint = "程序对应的下载地址全URL")
    private String url;

    @NotEmpty
    @OpenApiField(desc = "发布时间")
    private Date pubTime;

    @NotNull
    @OpenApiField(desc = "是否强制更新", constraint = "可选值:0:否，1:是")
    private int forceUpdate;
}

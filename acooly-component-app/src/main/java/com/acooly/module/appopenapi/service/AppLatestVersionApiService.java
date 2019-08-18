package com.acooly.module.appopenapi.service;

import com.acooly.core.utils.Strings;
import com.acooly.core.utils.mapper.BeanMapper;
import com.acooly.module.app.domain.AppVersion;
import com.acooly.module.app.service.AppVersionService;
import com.acooly.module.appopenapi.AppApiDocType;
import com.acooly.module.appopenapi.AppApiErrorCode;
import com.acooly.module.appopenapi.enums.ApiOwners;
import com.acooly.module.appopenapi.message.AppLatestVersionRequest;
import com.acooly.module.appopenapi.message.AppLatestVersionResponse;
import com.acooly.openapi.framework.common.annotation.ApiDocNote;
import com.acooly.openapi.framework.common.annotation.ApiDocType;
import com.acooly.openapi.framework.common.annotation.OpenApiService;
import com.acooly.openapi.framework.common.enums.ApiBusiType;
import com.acooly.openapi.framework.common.enums.ApiServiceResultCode;
import com.acooly.openapi.framework.common.enums.ResponseType;
import com.acooly.openapi.framework.common.exception.ApiServiceException;
import com.acooly.openapi.framework.core.service.base.BaseApiService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * APP最新版本信息 API
 *
 * @author zhangpu
 * @note <p>通过设备类型(deviceType:android/iphone)获取最新版本信息
 */
@ApiDocType(code = AppApiDocType.CODE, name = AppApiDocType.NAME)
@ApiDocNote("提供App发布的最新版本及信息，用于App客户端自动(强制)更新。逻辑如下：" +
        "<li>1、App客户端安装后需要在本地保存当前App的版本号（localVersionCode）</li>" +
        "<li>2、App客户端每次启动时，调用该接口（appLatestVersion）获得最新版本号(versionCode)</li>" +
        "<li>3、比较：如果versionCode大于localVersionCode，则需提示更新，否则忽略" +
        "<li>4、如果需要更新，则弹出提示框提示更新的内容（subject）</li>" +
        "<li>5、如果本版本被标记为强制（forceUpdate=1），App客户端需处理为必须更新才能进入App进行操作，否则退出</li>")
@OpenApiService(
        name = "appLatestVersion",
        desc = "最新版本",
        responseType = ResponseType.SYN,
        owner = ApiOwners.COMMON,
        busiType = ApiBusiType.Query
)
public class AppLatestVersionApiService
        extends BaseApiService<AppLatestVersionRequest, AppLatestVersionResponse> {

    @Autowired
    private AppVersionService appVersionService;

    @Override
    protected void doService(AppLatestVersionRequest request, AppLatestVersionResponse response) {
        try {
            AppVersion version =
                    appVersionService.getLatest(request.getAppCode(), request.getDeviceType());
            if (version == null) {
                throw new ApiServiceException(AppApiErrorCode.VERSION_NOFOUND);
            }
            BeanMapper.copy(version, response);
            if (Strings.equalsIgnoreCase(AppVersion.DEVICE_TYPE_IPHONE, version.getDeviceType())) {
                response.setUrl(version.getAppleUrl());
            }
        } catch (ApiServiceException ae) {
            throw ae;
        } catch (Exception e) {
            throw new ApiServiceException(ApiServiceResultCode.INTERNAL_ERROR, e.getMessage());
        }
    }
}

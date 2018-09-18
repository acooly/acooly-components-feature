package com.acooly.module.appopenapi.service;

import com.acooly.core.utils.Strings;
import com.acooly.core.utils.mapper.BeanMapper;
import com.acooly.module.app.domain.AppVersion;
import com.acooly.module.app.service.AppVersionService;
import com.acooly.module.appopenapi.AppApiErrorCode;
import com.acooly.module.appopenapi.enums.ApiOwners;
import com.acooly.module.appopenapi.message.AppLatestVersionRequest;
import com.acooly.module.appopenapi.message.AppLatestVersionResponse;
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

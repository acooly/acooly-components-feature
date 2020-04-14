/**
 * create by zhangpu date:2015年9月11日
 */
package com.acooly.module.appopenapi.service;

import com.acooly.core.utils.mapper.BeanCopier;
import com.acooly.module.app.domain.AppCrash;
import com.acooly.module.app.service.AppCrashService;
import com.acooly.module.appopenapi.AppApiDocType;
import com.acooly.module.appopenapi.enums.ApiOwners;
import com.acooly.module.appopenapi.message.AppCrashReportRequest;
import com.acooly.module.appopenapi.message.AppCrashReportResponse;
import com.acooly.openapi.framework.common.annotation.ApiDocNote;
import com.acooly.openapi.framework.common.annotation.ApiDocType;
import com.acooly.openapi.framework.common.annotation.OpenApiService;
import com.acooly.openapi.framework.common.enums.ApiServiceResultCode;
import com.acooly.openapi.framework.common.enums.ResponseType;
import com.acooly.openapi.framework.common.exception.ApiServiceException;
import com.acooly.openapi.framework.core.service.base.BaseApiService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * APP崩溃上报
 *
 * @author zhangpu
 * @date 2015年9月11日
 */
@ApiDocType(code = AppApiDocType.CODE, name = AppApiDocType.NAME)
@ApiDocNote("提供App客户端出现程序奔溃时，上传对应的错误和堆栈信息，以便于诊断问题")
@OpenApiService(
        name = "appCrashReport",
        desc = "崩溃上报",
        responseType = ResponseType.SYN,
        owner = ApiOwners.COMMON
)
public class AppCrashReportService
        extends BaseApiService<AppCrashReportRequest, AppCrashReportResponse> {

    @Autowired
    private AppCrashService appCrashService;

    @Override
    protected void doService(AppCrashReportRequest request, AppCrashReportResponse response) {
        try {
            AppCrash appCrash = new AppCrash();
            BeanCopier.copy(request, appCrash);
            appCrash.setCrashDate(new Date());
            appCrash.setStackTrace(new String(Base64.decodeBase64(appCrash.getStackTrace())));
            appCrashService.save(appCrash);
        } catch (ApiServiceException ae) {
            throw ae;
        } catch (Exception e) {
            throw new ApiServiceException(ApiServiceResultCode.INTERNAL_ERROR, e.getMessage());
        }
    }
}

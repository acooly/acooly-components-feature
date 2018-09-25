package com.acooly.core.test.openapi;


import com.acooly.openapi.framework.common.enums.ApiServiceResultCode;
import com.acooly.openapi.framework.common.exception.ApiServiceException;
import com.acooly.openapi.framework.common.message.ApiRequest;
import com.acooly.openapi.framework.common.message.ApiResponse;
import com.acooly.openapi.framework.core.exception.ApiServiceExceptionHander;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author qiubo@yiji.com
 */
@Component
@Primary
@Slf4j
public class CustomApiServiceExceptionHander implements ApiServiceExceptionHander {
    @Override
    public void handleApiServiceException(
            ApiRequest apiRequest, ApiResponse apiResponse, Throwable ase) {
        if (ApiServiceException.class.isAssignableFrom(ase.getClass())) {
            handleApiServiceException(apiResponse, (ApiServiceException) ase);
        } else {
            String serviceName = "";
            if (apiRequest != null) {
                serviceName = apiRequest.getService();
            }
            log.error("处理服务[{}]异常", serviceName, ase);
            handleInternalException(apiResponse);
        }
    }

    /**
     * 服务异常处理
     *
     * @param apiResponse
     * @param ase
     */
    protected void handleApiServiceException(ApiResponse apiResponse, ApiServiceException ase) {
        String resultMessage = ase.getResultMessage();
        if (resultMessage != null && resultMessage.contains("connection holder is null")) {
            resultMessage = "网络异常 请稍后重试";
        }
        apiResponse.setCode(ase.getResultCode());
        apiResponse.setMessage(resultMessage);
        apiResponse.setDetail(ase.getDetail());
    }

    /**
     * 系统异常处理
     *
     * @param apiResponse
     */
    protected void handleInternalException(ApiResponse apiResponse) {
        apiResponse.setCode(ApiServiceResultCode.INTERNAL_ERROR.code());
        apiResponse.setMessage(ApiServiceResultCode.INTERNAL_ERROR.message());
    }
}

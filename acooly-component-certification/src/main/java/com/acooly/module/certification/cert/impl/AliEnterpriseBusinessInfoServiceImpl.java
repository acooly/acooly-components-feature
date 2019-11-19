/**
 * acooly-components-feature-parent
 * <p>
 * Copyright 2019 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhike
 * @date 2019-09-05 17:49
 */
package com.acooly.module.certification.cert.impl;

import com.acooly.core.utils.enums.ResultStatus;
import com.acooly.module.certification.CertificationProperties;
import com.acooly.module.certification.cert.EnterpriseBusinessInfoService;
import com.acooly.module.certification.cert.RealNameAuthenticationException;
import com.acooly.module.certification.enums.EnterpriseBusinessInfoResult;
import com.acooly.module.certification.utils.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhike
 * @date 2019-09-05 17:49
 */
@Slf4j
@Service("aliEnterpriseBusinessInfoService")
public class AliEnterpriseBusinessInfoServiceImpl implements EnterpriseBusinessInfoService {


    private static Map<String, String> messages =
            new LinkedHashMap<String, String>() {
                /** UId */
                private static final long serialVersionUID = -847699194658395108L;

                {
                    put("0", "查询成功");
                    put("50002", "查询无结果");
                    put("80099", "交易失败,请稍候再试");
                }
            };
    @Autowired
    private CertificationProperties certificationProperties;

    @Override
    public EnterpriseBusinessInfoResult enterpriseBusinessInfo(String comInfo) {
        String url = "http://bankpros.market.alicloudapi.com/comdata";
        String params = "com=" + comInfo;
        EnterpriseBusinessInfoResult result = new EnterpriseBusinessInfoResult();
        try {
            String respBody = HttpUtil.aliApiRequest(url, params, certificationProperties.getEnterpriseBsuiInfo().getAppCode(), "POST");
            if (StringUtils.isNotBlank(respBody)) {
                JSONObject resultObj = JSON.parseObject(respBody);
                String errorCode = resultObj.getString("error_code");
                String reason = resultObj.getString("reason");
                String resultData = resultObj.getString("result");
                if (StringUtils.equals(errorCode, "0")) {
                    result.setStatus(ResultStatus.success);
                    result.setCode(reason);
                    result.setDetail(messages.get(errorCode));
                    result = JSON.parseObject(resultData, EnterpriseBusinessInfoResult.class);
                    ;
                    log.info("查询工商信息成功:{}", reason);
                } else {
                    log.warn("查询工商信息失败:{}", reason);
                    result.setStatus(ResultStatus.success);
                    result.setCode(reason);
                    result.setDetail(messages.get(errorCode));
                    throw new RealNameAuthenticationException(
                            ResultStatus.failure.getCode(), reason);
                }
            } else {
                log.warn("查询工商信息失败");
                throw new RealNameAuthenticationException(ResultStatus.failure.getCode(), "查询工商信息失败");
            }
        } catch (RealNameAuthenticationException e) {
            throw e;
        } catch (Exception e) {
            log.info("查询工商信息未知异常:{}", e.getMessage());
            Throwable throwable = Throwables.getRootCause(e);
            if (throwable instanceof ConnectTimeoutException
                    || throwable instanceof java.net.SocketTimeoutException
                    || throwable instanceof ConnectException) {
                throw new RealNameAuthenticationException(
                        ResultStatus.failure.getCode(), "连接超时:" + e.getMessage());
            } else if (throwable instanceof UnknownHostException) {
                throw new RealNameAuthenticationException(
                        ResultStatus.failure.getCode(), "UnknownHost:" + e.getMessage());
            } else {
                throw new RealNameAuthenticationException(ResultStatus.failure.getCode(), e.getMessage());
            }
        }
        return result;
    }
}

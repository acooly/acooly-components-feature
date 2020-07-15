package com.acooly.module.certification.cert.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.enums.ResultStatus;
import com.acooly.module.certification.CertificationProperties;
import com.acooly.module.certification.cert.PhoneCertService;
import com.acooly.module.certification.common.Response;
import com.acooly.module.certification.enums.PhoneCertResult;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author liangsong
 * @date 2020-07-15 15:13
 */
@Slf4j
@Service("aliPhoneCertService")
public class AliPhoneCertServiceImpl implements PhoneCertService {

    @Autowired
    private CertificationProperties certificationProperties;

    public static final String SUCCESS_CODE = "0";
    private static final Map<String, String> RET_CODE =
            new LinkedHashMap<String, String>() {
                {
                    put("0", "认证成功");
                    put("1", "认证失败");
                    put("2", "无该手机号记录");
                    put("11", "手机号、身份证或者姓名为空");
                    put("12", "身份证校验错误");
                    put("13", "手机号校验错误");
                    put("21", "渠道升级暂停服务");
                    put("22", "渠道维护暂停服务");
                }
            };

    private final String phoneCertUrl = "http://phone11.market.alicloudapi.com";

    @Override
    public PhoneCertResult phoneCert(String realName, String certNo, String mobile) {
        PhoneCertResult result;
        String path = "/phoneAudit";
        String appcode = certificationProperties.getPhoneCert().getAppCode();
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> params = new HashMap<String, String>();
        params.put("idCard", certNo);
        params.put("name", realName);
        params.put("needBelongArea", "true");
        params.put("phone", mobile);
        try {
            Response response = HttpUtil.httpGet(phoneCertUrl, path, certificationProperties.getPhoneCert().getTimeout(), headers, params);
            result = unmashall(response);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.info("手机在网三要素校验异常:{}", e.getMessage());
            Throwable throwable = Throwables.getRootCause(e);
            if (throwable instanceof ConnectTimeoutException
                    || throwable instanceof java.net.SocketTimeoutException
                    || throwable instanceof ConnectException) {
                throw new BusinessException(ResultStatus.failure.getCode(), "连接超时:" + e.getMessage());
            } else if (throwable instanceof UnknownHostException) {
                throw new BusinessException(
                        ResultStatus.failure.getCode(), "UnknownHost:" + e.getMessage());
            } else {
                throw new BusinessException(ResultStatus.failure.getCode(), e.getMessage());
            }
        }
        return result;
    }

    private PhoneCertResult unmashall(Response response) {
        log.info("调用手机在网三要素校验接口返回:{}", response.getBody());
        if (StringUtils.isEmpty(response.getBody())) {
            throw new BusinessException(ResultStatus.failure.getCode(), "手机在网三要素校验返回空:" + response.getErrorMessage());
        }
        PhoneCertResult result = new PhoneCertResult();
        JSONObject jsonResult = JSON.parseObject(response.getBody());
        String resCode = jsonResult.getString("showapi_res_code");
        String resError = jsonResult.getString("showapi_res_error");

        if (!SUCCESS_CODE.equals(resCode)) {
            throw new BusinessException(ResultStatus.failure.getCode(), resError);
        }

        if (SUCCESS_CODE.equals(resCode)) {
            JSONObject resBody = jsonResult.getJSONObject("showapi_res_body");
            // 调用成功与否，0为成功，其他值为失败
            String retCode = resBody.getString("ret_code");
            // 0 成功，其他为失败
            String code = resBody.getString("code");
            String msg = resBody.getString("msg");
            String notNullmsg = StringUtils.isEmpty(msg) ? RET_CODE.get(code) : msg;

            if (!SUCCESS_CODE.equals(code)) {
                log.info("手机在网三要素校验失败，结果:{}", notNullmsg);
                throw new BusinessException(ResultStatus.failure.getCode(), notNullmsg);
            }

            JSONObject belongArea = resBody.getJSONObject("belongArea");
            result.setProv(belongArea.getString("prov"));
            result.setCity(belongArea.getString("city"));
            result.setNum(belongArea.getString("num"));
            result.setAreaCode(belongArea.getString("areaCode"));
            result.setProvCode(belongArea.getString("provCode"));
            result.setName(belongArea.getString("name"));
            result.setType(belongArea.getIntValue("type"));
            if (SUCCESS_CODE.equals(code)) {
                result.setStatus(ResultStatus.success);
                log.info("手机在网三要素校验成功{}", result);
            } else {
                result.setStatus(ResultStatus.failure);
                result.setDetail(notNullmsg);
                log.info("手机在网三要素校验失败：{},{}", notNullmsg, result);
            }
        }
        return result;
    }
}

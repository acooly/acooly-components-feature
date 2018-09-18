package com.acooly.module.certification.cert.impl;

import com.acooly.core.utils.enums.ResultStatus;
import com.acooly.module.certification.CertificationProperties;
import com.acooly.module.certification.cert.BankCardCertService;
import com.acooly.module.certification.cert.CertficationException;
import com.acooly.module.certification.common.Response;
import com.acooly.module.certification.enums.BankCardResult;
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
 * @author shuijing
 */
@Slf4j
@Service("aliBankCardCertService")
public class AliBankCardCertServiceImpl implements BankCardCertService {

    public static final String SUCCESS_CODE = "0";
    private static final Map<String, String> RET_CODE =
            new LinkedHashMap<String, String>() {
                {
                    put("0", "匹配");
                    put("1", "交易失败,请联系发卡行");
                    put("4", "此卡被没收,请于发卡方联系");
                    put("5", " 持卡人认证失败");
                    put("14", "无效卡号");
                    put("15", "此卡无对应发卡方");
                    put("21", "该卡未初始化或睡眠卡");
                    put("34", "作弊卡,吞卡 ");
                    put("40", "发卡方不支持的交易");
                    put("41", "此卡已经挂失");
                    put("43", "此卡被没收");
                    put("54", "该卡已过期");
                    put("57", "发卡方不允许此交易");
                    put("62", "受限制的卡");
                    put("75", "密码错误次数超限");
                    put("82", "身份证号码有误");
                    put("83", "银行卡号码有误");
                    put("84", "手机号码不合法");
                    put("86", "持卡人信息有误");
                    put("87", "未开通无卡支付");
                    put("96", "交易失败请重试");
                }
            };
    private final String certUrl = "http://ali-bankcard4.showapi.com";
    @Autowired
    private CertificationProperties certificationProperties;

    @Override
    public BankCardResult bankCardCert(
            String realName, String cardNo, String certId, String phoneNum) {

        String appCode = certificationProperties.getBankcert().getAppCode();
        String path = "/bank4";
        // 二要素
        if (StringUtils.isEmpty(certId) && StringUtils.isEmpty(phoneNum)) {
            path = "/bank2";
        }
        // 三要素
        if (!StringUtils.isEmpty(certId) && StringUtils.isEmpty(phoneNum)) {
            path = "/bank3";
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + appCode);
        Map<String, String> params = new HashMap<>();
        params.put("acct_name", realName);
        params.put("acct_pan", cardNo);
        params.put("cert_id", certId);
        params.put("cert_type", "01");
        params.put("needBelongArea", "true");
        params.put("phone_num", phoneNum);
        BankCardResult result;
        log.info("调用银行卡二三四要素校验接口入参：{}", params.toString());
        try {
            Response response =
                    HttpUtil.httpGet(
                            certUrl, path, certificationProperties.getBankcert().getTimeout(), headers, params);

            result = unmashall(response);

        } catch (CertficationException e) {
            throw e;
        } catch (Exception e) {
            log.info("银行卡二三四要素校验异常:{}", e.getMessage());
            Throwable throwable = Throwables.getRootCause(e);
            if (throwable instanceof ConnectTimeoutException
                    || throwable instanceof java.net.SocketTimeoutException
                    || throwable instanceof ConnectException) {
                throw new CertficationException(ResultStatus.failure.getCode(), "连接超时:" + e.getMessage());
            } else if (throwable instanceof UnknownHostException) {
                throw new CertficationException(
                        ResultStatus.failure.getCode(), "UnknownHost:" + e.getMessage());
            } else {
                throw new CertficationException(ResultStatus.failure.getCode(), e.getMessage());
            }
        }
        return result;
    }

    private BankCardResult unmashall(Response response) throws CertficationException {

        log.info("调用银行卡二三四要素校验接口返回:{}", response.getBody());
        if (StringUtils.isEmpty(response.getBody())) {
            throw new CertficationException(
                    ResultStatus.failure.getCode(), "银行卡二三四要素校验返回空:" + response.getErrorMessage());
        }

        BankCardResult result = new BankCardResult();
        JSONObject total = JSON.parseObject(response.getBody());

        String resCode = total.getString("showapi_res_code");
        String resError = total.getString("showapi_res_error");

        if (!SUCCESS_CODE.equals(resCode)) {
            throw new CertficationException(ResultStatus.failure.getCode(), resError);
        }

        if (SUCCESS_CODE.equals(resCode)) {
            JSONObject resBody = total.getJSONObject("showapi_res_body");
            // 调用成功与否，0为成功，其他值为失败
            String retCode = resBody.getString("ret_code");
            // 0 成功，其他为失败
            String code = resBody.getString("code");
            String msg = resBody.getString("msg");
            String notNullmsg = StringUtils.isEmpty(msg) ? RET_CODE.get(code) : msg;

            if (!SUCCESS_CODE.equals(retCode)) {
                log.info("银行卡二三四要素校验调用接口失败");
                throw new CertficationException(ResultStatus.failure.getCode(), "银行卡二三四要素校验调用接口失败");
            }

            if (!SUCCESS_CODE.equals(code)) {
                log.info("银行卡二三四要素校验失败，结果:{}", notNullmsg);
                throw new CertficationException(ResultStatus.failure.getCode(), notNullmsg);
            }

            JSONObject belong = resBody.getJSONObject("belong");

            result.setBelongArea(belong.getString("area"));
            result.setBankTel(belong.getString("tel"));
            result.setBrand(belong.getString("brand"));
            result.setBankName(belong.getString("bankName"));
            result.setCardType(belong.getString("cardType"));
            result.setBankUrl(belong.getString("url"));
            result.setCardNo(belong.getString("cardNum"));
            if (SUCCESS_CODE.equals(code)) {
                result.setStatus(ResultStatus.success);
                log.info("银行卡二三四要素校验成功{}", result);
            } else {
                result.setStatus(ResultStatus.failure);
                result.setDetail(notNullmsg);
                log.info("银行卡二三四要素校验失败：{},{}", notNullmsg, result);
            }
        }
        return result;
    }
}

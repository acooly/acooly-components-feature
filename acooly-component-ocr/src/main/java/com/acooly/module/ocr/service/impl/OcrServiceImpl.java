package com.acooly.module.ocr.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.enums.ResultStatus;
import com.acooly.core.utils.validate.Validators;
import com.acooly.module.ocr.OcrProperties;
import com.acooly.module.ocr.dto.IdCardOcrBackDto;
import com.acooly.module.ocr.dto.IdCardOcrFrontDto;
import com.acooly.module.ocr.enums.IdCardSideEnum;
import com.acooly.module.ocr.enums.ImageStatusEnum;
import com.acooly.module.ocr.enums.RiskTypeEnum;
import com.acooly.module.ocr.order.DriverLicenseOcrOrder;
import com.acooly.module.ocr.order.IdCardOcrOrder;
import com.acooly.module.ocr.result.DriverLicenseOcrResult;
import com.acooly.module.ocr.result.IdCardOcrResult;
import com.acooly.module.ocr.service.OcrService;
import com.baidu.aip.ocr.AipOcr;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author liangsong
 * @date 2020-03-25 14:45
 */
@Slf4j
@Service
public class OcrServiceImpl implements OcrService {

    private static final String ERROR_CODE = "error_code";

    private static final String ERROR_MSG = "error_msg";

    @Autowired
    private OcrProperties ocrProperties;

    @Override
    public IdCardOcrResult idCardOcr(IdCardOcrOrder order) {
        Validators.assertJSR303(order);
        IdCardOcrResult result = new IdCardOcrResult();
        try {
            AipOcr client = initClient();
            HashMap<String, String> options = Maps.newHashMap();
            options.put("detect_direction", String.valueOf(order.isDetectDirection()));
            options.put("detect_risk", String.valueOf(order.isDetectRisk()));
            JSONObject res = client.idcard(order.getImage(), order.getIdCardSide().getCode(), options);
            log.info("身份证识别结果:{}", res.toString());
            result.setLogId(res.optString("log_id"));
            result.setEditTool(res.optString("edit_tool"));
            result.setImageStatus(res.optEnum(ImageStatusEnum.class, "image_status", null));
            result.setRiskType(res.optEnum(RiskTypeEnum.class, "risk_type", null));
            result.setIdCardNumberType(res.optString("idcard_number_type"));
            if (IdCardSideEnum.front.equals(order.getIdCardSide())) {
                IdCardOcrFrontDto dto = new IdCardOcrFrontDto();
                dto.setName(res.optJSONObject("words_result").optJSONObject("姓名").optString("words"));
                dto.setNation(res.optJSONObject("words_result").optJSONObject("民族").optString("words"));
                dto.setAddress(res.optJSONObject("words_result").optJSONObject("住址").optString("words"));
                dto.setIdCardNo(res.optJSONObject("words_result").optJSONObject("公民身份号码").optString("words"));
                dto.setBirthday(res.optJSONObject("words_result").optJSONObject("出生").optString("words"));
                dto.setSex(res.optJSONObject("words_result").optJSONObject("性别").optString("words"));
                result.setIdCardOcrFrontDto(dto);
            } else {
                IdCardOcrBackDto dto = new IdCardOcrBackDto();
                dto.setIssuanceDate(res.optJSONObject("words_result").optJSONObject("签发日期").optString("words"));
                dto.setExpiryDate(res.optJSONObject("words_result").optJSONObject("失效日期").optString("words"));
                dto.setAuthority(res.optJSONObject("words_result").optJSONObject("签发机关").optString("words"));
                result.setIdCardOcrBackDto(dto);
            }
        } catch (BusinessException e) {
            log.info("业务异常，原因{}", e.getMessage());
            result.setStatus(ResultStatus.failure);
            result.setDetail(e.getMessage());
        } catch (Exception e) {
            log.info("系统异常，原因{}", e.getMessage());
            result.setStatus(ResultStatus.failure);
            result.setDetail("系统异常");
        }
        return result;
    }

    @Override
    public DriverLicenseOcrResult driverLicenseOcr(DriverLicenseOcrOrder order) {
        Validators.assertJSR303(order);
        DriverLicenseOcrResult result = new DriverLicenseOcrResult();
        try {
            AipOcr client = initClient();
            HashMap<String, String> options = Maps.newHashMap();
            options.put("detect_direction", String.valueOf(order.isDetectDirection()));
            JSONObject res = client.drivingLicense(order.getImage(), options);
            log.info("驾驶证识别结果:{}", res.toString());
            result.setLogId(res.optString("log_id"));
            result.setName(res.optJSONObject("words_result").optJSONObject("姓名").optString("words"));
            result.setIdCardNo(res.optJSONObject("words_result").optJSONObject("证号").optString("words"));
            result.setGender(res.optJSONObject("words_result").optJSONObject("性别").optString("words"));
            result.setNationality(res.optJSONObject("words_result").optJSONObject("国籍").optString("words"));
            result.setAddress(res.optJSONObject("words_result").optJSONObject("住址").optString("words"));
            result.setBirthday(res.optJSONObject("words_result").optJSONObject("出生日期").optString("words"));
            result.setFirstIssueDate(res.optJSONObject("words_result").optJSONObject("初次领证日期").optString("words"));
            result.setType(res.optJSONObject("words_result").optJSONObject("准驾车型").optString("words"));
            result.setValidFrom(res.optJSONObject("words_result").optJSONObject("有效期限").optString("words"));
            result.setValidTo(res.optJSONObject("words_result").optJSONObject("至").optString("words"));
        } catch (BusinessException e) {
            log.info("业务异常，原因{}", e.getMessage());
            result.setStatus(ResultStatus.failure);
            result.setDetail(e.getMessage());
        } catch (Exception e) {
            log.info("系统异常，原因{}", e.getMessage());
            result.setStatus(ResultStatus.failure);
            result.setDetail("系统异常");
        }
        return result;
    }

    private AipOcr initClient() {
        return new AipOcr(ocrProperties.getAppId(), ocrProperties.getApiKey(), ocrProperties.getSecretKey());
    }
}

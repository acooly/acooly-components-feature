package com.acooly.module.face.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.enums.ResultStatus;
import com.acooly.core.utils.validate.Validators;
import com.acooly.module.face.FaceProperties;
import com.acooly.module.face.dto.FacePicDto;
import com.acooly.module.face.enums.ImageTypeEnum;
import com.acooly.module.face.order.IdMatchOrder;
import com.acooly.module.face.order.PersonVerifyOrder;
import com.acooly.module.face.order.VideoSessionCodeOrder;
import com.acooly.module.face.order.VideoVerifyOrder;
import com.acooly.module.face.result.IdMatchResult;
import com.acooly.module.face.result.PersonVerifyResult;
import com.acooly.module.face.result.VideoSessionResult;
import com.acooly.module.face.result.VideoVerifyResult;
import com.acooly.module.face.service.BaiduFaceService;
import com.baidu.aip.face.AipFace;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @author liangsong
 * @date 2020-03-20 14:57
 */
@Slf4j
@Service
public class BaiduFaceServiceImpl implements BaiduFaceService {

    private static final String ERROR_CODE = "error_code";

    private static final String ERROR_MSG = "error_msg";

    @Autowired
    private FaceProperties faceProperties;

    @Override
    public VideoSessionResult videoSessionCode(VideoSessionCodeOrder order) {
        Validators.assertJSR303(order);
        VideoSessionResult result = new VideoSessionResult();
        try {
            AipFace client = initClient();
            HashMap<String, String> options = Maps.newHashMap();
            JSONObject res = client.videoSessioncode(options);
            if (0 != res.getInt(ERROR_CODE)) {
                log.info("语音验证码生成失败！原因{}", res.optString(ERROR_MSG));
                throw new BusinessException("语音验证码生成失败");
            }
            log.info("语音验证码结果:{}", res.toString());
            result.setSessionId(res.getJSONObject("result").optString("session_id"));
            result.setCode(res.getJSONObject("result").optString("code"));
        } catch (BusinessException e) {
            result.setStatus(ResultStatus.failure);
            result.setDetail(e.getMessage());
        } catch (Exception e) {
            result.setStatus(ResultStatus.failure);
            result.setDetail("系统异常");
        }
        return result;
    }

    @Override
    public VideoVerifyResult videoVerify(VideoVerifyOrder order) {
        Validators.assertJSR303(order);
        VideoVerifyResult result = new VideoVerifyResult();
        try {
            AipFace client = initClient();
            HashMap<String, String> options = Maps.newHashMap();
            options.put("lip_identify", order.getLipIdentify().getCode());
            options.put("face_field", order.getFaceField());
            JSONObject res = client.videoFaceliveness(order.getSessionId(), order.getVideo(), options);
            //res中含有base64的图片信息，打印出来日志量会比较大，去掉打印日志
//            log.info("视频验证结果:{}", res.toString());
            if (0 != res.getInt(ERROR_CODE)) {
                log.info("视频验证未通过！错误码{}，原因{}", res.optString(ERROR_CODE), res.optString(ERROR_MSG));
                throw new BusinessException("视频验证未通过");
            }
            verify(result, res);
            //如果同步需要进行公安验证，则返回公安验证结果
            if (order.isPersonVerify()) {
                PersonVerifyOrder verifyOrder = new PersonVerifyOrder();
                verifyOrder.setImage(result.getHighScorePicDto().getFaceToken());
                verifyOrder.setImageType(ImageTypeEnum.FACE_TOKEN);
                verifyOrder.setIdCardNumber(order.getIdCardNumber());
                verifyOrder.setName(order.getName());
                verifyOrder.setQualityControl(order.getQualityControl());
                verifyOrder.setLivenessControl(order.getLivenessControl());
                PersonVerifyResult personVerifyResult = personVerify(verifyOrder);
                result.setIdCardScore(personVerifyResult.getScore());
                result.setPass(personVerifyResult.isPass());
            }
        } catch (BusinessException e) {
            result.setPass(false);
            result.setStatus(ResultStatus.failure);
            result.setDetail(e.getMessage());
        } catch (Exception e) {
            result.setPass(false);
            result.setStatus(ResultStatus.failure);
            result.setDetail("系统异常");
        }
        return result;
    }

    @Override
    public PersonVerifyResult personVerify(PersonVerifyOrder order) {
        Validators.assertJSR303(order);
        PersonVerifyResult result = new PersonVerifyResult();
        try {
            AipFace client = initClient();
            HashMap<String, String> options = Maps.newHashMap();
            options.put("quality_control", order.getQualityControl().getCode());
            options.put("liveness_control", order.getLivenessControl().getCode());
            JSONObject res = client.personVerify(order.getImage(), order.getImageType().getCode(), order.getIdCardNumber(), order.getName(), options);
            log.info("公安身份认证结果:{}", res.toString());
            if (0 != res.getInt(ERROR_CODE)) {
                log.info("公安身份认证未通过！原因{}", res.optString(ERROR_MSG));
                throw new BusinessException("公安身份认证未通过");
            }
            BigDecimal score = res.getJSONObject("result").optBigDecimal("score", BigDecimal.ZERO);
            result.setScore(score);
            if (new BigDecimal(faceProperties.getPersonVerifyThreshold()).compareTo(score) > 0) {
                log.info("当前设定身份验证阀值为{},实际结果评分为{},身份验证未通过", faceProperties.getPersonVerifyThreshold(), result.getScore());
                result.setPass(false);
            }
        } catch (BusinessException e) {
            result.setPass(false);
            result.setStatus(ResultStatus.failure);
            result.setDetail(e.getMessage());
        } catch (Exception e) {
            result.setPass(false);
            result.setStatus(ResultStatus.failure);
            result.setDetail("系统异常");
        }
        return result;
    }

    @Override
    public IdMatchResult idMatch(IdMatchOrder order) {
        Validators.assertJSR303(order);
        IdMatchResult result = new IdMatchResult();
        try {
            AipFace client = initClient();
            HashMap<String, Object> options = Maps.newHashMap();
            JSONObject res = client.idMatch(order.getIdCardNum(), order.getName(), options);
            log.info("身份证姓名验证结果:{}", res.toString());
            if (0 != res.getInt(ERROR_CODE)) {
                log.info("身份证姓名验证结果！原因{}", res.optString(ERROR_MSG));
                throw new BusinessException("身份证姓名验证未通过");
            }
        } catch (BusinessException e) {
            result.setPass(false);
            result.setStatus(ResultStatus.failure);
            result.setDetail(e.getMessage());
        } catch (Exception e) {
            result.setPass(false);
            result.setStatus(ResultStatus.failure);
            result.setDetail("系统异常");
        }
        return result;
    }

    private void verify(VideoVerifyResult result, JSONObject res) {
        JSONObject resultJo = res.getJSONObject("result");
        result.setScore(resultJo.optBigDecimal("score", BigDecimal.ZERO));
        result.setCreateCode(resultJo.getJSONObject("code").optString("create"));
        result.setIdentifyCode(resultJo.getJSONObject("code").optString("identify"));
        result.setSimilarity(resultJo.getJSONObject("code").optBigDecimal("similarity", BigDecimal.ZERO));
        String lipLanguage = resultJo.optString("lip_language");
        if (Strings.isNotBlank(lipLanguage) && lipLanguage.equals("pass")) {
            result.setLipLanguage(true);
        }
        JSONArray picArray = resultJo.getJSONArray("pic_list");
        if (picArray != null) {
            FacePicDto highFacePicDto = null;
            for (int i = 0; i < picArray.length(); i++) {
                JSONObject jo = picArray.getJSONObject(i);
                FacePicDto dto = new FacePicDto();
                dto.setFaceId(jo.optString("face_id"));
                dto.setFaceToken(jo.optString("face_token"));
                dto.setPic(jo.optString("pic"));
                dto.setLivenessScore(jo.optBigDecimal("liveness_score", BigDecimal.ZERO));
                dto.setSpoofing(jo.optBigDecimal("spoofing", BigDecimal.ZERO));
                if (highFacePicDto == null) {
                    highFacePicDto = dto;
                } else {
                    if (highFacePicDto.getLivenessScore().compareTo(dto.getLivenessScore()) > 0) {
                        highFacePicDto = dto;
                    }
                }
                result.addFacePicDto(dto);
            }
            result.setHighScorePicDto(highFacePicDto);
        }
        if (new BigDecimal(faceProperties.getFaceThreshold()).compareTo(result.getScore()) > 0) {
            log.info("当前设定人脸阀值为{},实际结果评分为{},人脸识别阀值未达标,人脸识别未通过", faceProperties.getFaceThreshold(), result.getScore());
            throw new BusinessException("视频验证未通过");
        }
        if (new BigDecimal(faceProperties.getCodeThreshold()).compareTo(result.getSimilarity()) > 0) {
            log.info("当前设定验证码阀值为{},实际结果评分为{},验证码阀值未达标,人脸识别未通过", faceProperties.getCodeThreshold(), result.getSimilarity());
            throw new BusinessException("视频验证未通过");
        }
        if (faceProperties.isLipLanguage() && !result.isLipLanguage()) {
            log.info("唇语验证失败,人脸识别未通过");
            throw new BusinessException("视频验证未通过");
        }
        log.info("人脸识别通过,当前最高评分FACE_TOKEN={}", result.getHighScorePicDto().getFaceToken());
    }

    public AipFace initClient() {
        return new AipFace(faceProperties.getAppId(), faceProperties.getApiKey(), faceProperties.getSecretKey());
    }
}

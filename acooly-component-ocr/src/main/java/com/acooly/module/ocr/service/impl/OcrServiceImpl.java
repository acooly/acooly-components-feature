package com.acooly.module.ocr.service.impl;

import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Dates;
import com.acooly.core.utils.enums.ResultStatus;
import com.acooly.core.utils.validate.Validators;
import com.acooly.module.ocr.OcrProperties;
import com.acooly.module.ocr.dto.IdCardOcrBackDto;
import com.acooly.module.ocr.dto.IdCardOcrFrontDto;
import com.acooly.module.ocr.dto.VehicleLicenseOcrBackDto;
import com.acooly.module.ocr.dto.VehicleLicenseOcrFrontDto;
import com.acooly.module.ocr.enums.BankCardTypeOcrEnum;
import com.acooly.module.ocr.enums.IdCardSideEnum;
import com.acooly.module.ocr.enums.ImageStatusEnum;
import com.acooly.module.ocr.enums.RiskTypeEnum;
import com.acooly.module.ocr.enums.VehicleLicenseSideEnum;
import com.acooly.module.ocr.order.BankcardOcrOrder;
import com.acooly.module.ocr.order.BusinessLicenseOcrOrder;
import com.acooly.module.ocr.order.DriverLicenseOcrOrder;
import com.acooly.module.ocr.order.IdCardOcrOrder;
import com.acooly.module.ocr.order.VehicleLicenseOcrOrder;
import com.acooly.module.ocr.result.BankcardOcrResult;
import com.acooly.module.ocr.result.BusinessLicenseOcrResult;
import com.acooly.module.ocr.result.DriverLicenseOcrResult;
import com.acooly.module.ocr.result.IdCardOcrResult;
import com.acooly.module.ocr.result.VehicleLicenseOcrResult;
import com.acooly.module.ocr.service.OcrService;
import com.baidu.aip.ocr.AipOcr;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

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

			JSONObject wordsResult = res.optJSONObject("words_result");
			if (IdCardSideEnum.front.equals(order.getIdCardSide())) {
				IdCardOcrFrontDto dto = new IdCardOcrFrontDto();
				dto.setName(convertResult(wordsResult.optJSONObject("姓名")));
				dto.setNation(convertResult(wordsResult.optJSONObject("民族")));
				dto.setAddress(convertResult(wordsResult.optJSONObject("住址")));
				dto.setIdCardNo(convertResult(wordsResult.optJSONObject("公民身份号码")));
				dto.setBirthday(wordsResult.optJSONObject("出生") != null
						? formatDateStr(wordsResult.optJSONObject("出生").optString("words"))
						: null);
				dto.setSex(convertResult(wordsResult.optJSONObject("性别")));
				result.setIdCardOcrFrontDto(dto);
			} else {
				IdCardOcrBackDto dto = new IdCardOcrBackDto();
				JSONObject issuanceDate = wordsResult.optJSONObject("签发日期");
				JSONObject expiryDate = wordsResult.optJSONObject("失效日期");
				dto.setIssuanceDate(issuanceDate != null ? formatDateStr(issuanceDate.optString("words")) : null);
				dto.setExpiryDate(expiryDate != null ? formatDateStr(expiryDate.optString("words")) : null);
				dto.setAuthority(convertResult(wordsResult.optJSONObject("签发机关")));
				result.setIdCardOcrBackDto(dto);
			}
		} catch (BusinessException e) {
			log.info("身份证识别-业务异常，原因{}", e.getMessage());
			result.setStatus(ResultStatus.failure);
			result.setDetail(e.getMessage());
		} catch (Exception e) {
			log.info("身份证识别-系统异常，原因{}", e.getMessage());
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

			JSONObject wordsResult = res.optJSONObject("words_result");
			JSONObject name = wordsResult.optJSONObject("姓名");
			JSONObject idCardNo = wordsResult.optJSONObject("证号");
			JSONObject gender = wordsResult.optJSONObject("性别");
			JSONObject nationality = wordsResult.optJSONObject("国籍");
			JSONObject address = wordsResult.optJSONObject("住址");
			JSONObject birthday = wordsResult.optJSONObject("出生日期");
			JSONObject firstIssueDate = wordsResult.optJSONObject("初次领证日期");
			JSONObject type = wordsResult.optJSONObject("准驾车型");

			result.setLogId(res.optString("log_id"));
			result.setName(convertResult(name));
			result.setIdCardNo(convertResult(idCardNo));
			result.setGender(convertResult(gender));
			result.setNationality(convertResult(nationality));
			result.setAddress(convertResult(address));
			result.setBirthday(birthday != null ? formatDateStr(birthday.optString("words")) : null);
			result.setFirstIssueDate(firstIssueDate != null ? formatDateStr(firstIssueDate.optString("words")) : null);
			result.setType(convertResult(type));

			JSONObject validTo = wordsResult.optJSONObject("至");
			if (validTo != null) {
				JSONObject validFrom = wordsResult.optJSONObject("有效期限");
				result.setValidFrom(validFrom != null ? formatDateStr(validFrom.optString("words")) : null);
				result.setValidTo(validTo != null ? formatDateStr(validTo.optString("words")) : null);
			} else {
				JSONObject validFrom = wordsResult.optJSONObject("有效起始日期");
				validTo = wordsResult.optJSONObject("有效期限");
				result.setValidFrom(validFrom != null ? formatDateStr(validFrom.optString("words")) : null);
				result.setValidTo(validTo != null ? formatDateStr(validTo.optString("words")) : null);
			}
		} catch (BusinessException e) {
			log.info("驾驶证识别-业务异常，原因{}", e.getMessage());
			result.setStatus(ResultStatus.failure);
			result.setDetail(e.getMessage());
		} catch (Exception e) {
			log.info("驾驶证识别-系统异常，原因{}", e.getMessage());
			result.setStatus(ResultStatus.failure);
			result.setDetail("系统异常");
		}
		return result;
	}

	/**
	 * 营业执照
	 */
	@Override
	public BusinessLicenseOcrResult businessLicenseOcr(BusinessLicenseOcrOrder order) {
		Validators.assertJSR303(order);
		BusinessLicenseOcrResult result = new BusinessLicenseOcrResult();
		try {
			AipOcr client = initClient();
			HashMap<String, String> options = Maps.newHashMap();
			options.put("detect_direction", String.valueOf(order.isDetectDirection()));
			options.put("accuracy", String.valueOf(order.getAccuracy().getCode()));
			JSONObject res = client.businessLicense(order.getImage(), options);

			log.info("营业执照-识别结果:{}", res.toString());
			JSONObject wordsResult = res.optJSONObject("words_result");
			JSONObject buildDate = wordsResult.optJSONObject("成立日期");

			result.setLogId(res.optString("log_id"));
			result.setCompanyName(convertResult(wordsResult.optJSONObject("单位名称")));
			result.setType(convertResult(wordsResult.optJSONObject("类型")));
			result.setLegalPerson(convertResult(wordsResult.optJSONObject("法人")));
			result.setAddress(convertResult(wordsResult.optJSONObject("地址")));
			result.setValidDate(convertResult(wordsResult.optJSONObject("有效期")));
			result.setBuildDate(convertResult(buildDate));
			result.setCertificateNum(convertResult(wordsResult.optJSONObject("证件编号")));
			result.setSocialCreditCode(convertResult(wordsResult.optJSONObject("社会信用代码")));

		} catch (BusinessException e) {
			log.info("营业执照识别-业务异常，原因{}", e.getMessage());
			result.setStatus(ResultStatus.failure);
			result.setDetail(e.getMessage());
		} catch (Exception e) {
			log.info("营业执照识别-系统异常，原因{}", e.getMessage());
			result.setStatus(ResultStatus.failure);
			result.setDetail("系统异常");
		}
		return result;
	}

	@Override
	public BankcardOcrResult bankcardOcr(BankcardOcrOrder order) {
		Validators.assertJSR303(order);
		BankcardOcrResult result = new BankcardOcrResult();
		try {
			AipOcr client = initClient();
			HashMap<String, String> options = Maps.newHashMap();
			options.put("detect_direction", String.valueOf(order.isDetectDirection()));
			JSONObject res = client.bankcard(order.getImage(), options);

			log.info("银行卡-识别结果:{}", res.toString());
			JSONObject wordsResult = res.optJSONObject("result");

			result.setLogId(res.optString("log_id"));
			result.setBankCardNumber(wordsResult.optString("bank_card_number").replace(" ", ""));
			result.setValidDate(wordsResult.optString("valid_date"));

			// 银行卡类型，0:不能识别; 1: 借记卡; 2: 信用卡
			BankCardTypeOcrEnum bankCardType = BankCardTypeOcrEnum.unknown;
			int bankCardTypeInt = Integer.parseInt(wordsResult.optString("bank_card_type"));
			if (bankCardTypeInt == 1) {
				bankCardType = BankCardTypeOcrEnum.debitCard;
			}
			if (bankCardTypeInt == 2) {
				bankCardType = BankCardTypeOcrEnum.creditCard;
			}
			result.setBankCardType(bankCardType);

			result.setBankName(wordsResult.optString("bank_name"));
		} catch (BusinessException e) {
			log.info("银行卡识别-业务异常，原因{}", e.getMessage());
			result.setStatus(ResultStatus.failure);
			result.setDetail(e.getMessage());
		} catch (Exception e) {
			log.info("银行卡识别-系统异常，原因{}", e.getMessage());
			result.setStatus(ResultStatus.failure);
			result.setDetail("系统异常");
		}
		return result;
	}

	@Override
	public VehicleLicenseOcrResult vehicleLicenseOcr(VehicleLicenseOcrOrder order) {
		Validators.assertJSR303(order);
		VehicleLicenseOcrResult result = new VehicleLicenseOcrResult();
		VehicleLicenseSideEnum vehicleLicenseSide = order.getVehicleLicenseSide();
		try {
			AipOcr client = initClient();
			HashMap<String, String> options = Maps.newHashMap();
			options.put("detect_direction", String.valueOf(order.isDetectDirection()));
			options.put("vehicle_license_side", String.valueOf(vehicleLicenseSide.getCode()));
			options.put("unified", String.valueOf(order.isUnified()));
			JSONObject res = client.vehicleLicense(order.getImage(), options);

			log.info("行驶证-{},识别结果:{}", vehicleLicenseSide, res.toString());
			JSONObject wordsResult = res.optJSONObject("words_result");
			result.setLogId(res.optString("log_id"));

			if (vehicleLicenseSide == VehicleLicenseSideEnum.front) {
				VehicleLicenseOcrFrontDto frontDto = new VehicleLicenseOcrFrontDto();
				frontDto.setBrandModel(convertResult(wordsResult.optJSONObject("品牌型号")));
				frontDto.setIssueDate(convertResult(wordsResult.optJSONObject("发证日期")));
				frontDto.setUseNature(convertResult(wordsResult.optJSONObject("使用性质")));
				frontDto.setEngineNum(convertResult(wordsResult.optJSONObject("发动机号码")));
				frontDto.setLicensePlateNum(convertResult(wordsResult.optJSONObject("号牌号码")));
				frontDto.setOwner(convertResult(wordsResult.optJSONObject("所有人")));
				frontDto.setAddress(convertResult(wordsResult.optJSONObject("住址")));
				frontDto.setRegisterDate(convertResult(wordsResult.optJSONObject("注册日期")));
				frontDto.setVIN(convertResult(wordsResult.optJSONObject("车辆识别代号")));
				frontDto.setVehicleType(convertResult(wordsResult.optJSONObject("车辆类型")));
				result.setVehicleLicenseOcrFront(frontDto);
			} else {
				VehicleLicenseOcrBackDto backDto = new VehicleLicenseOcrBackDto();
				backDto.setInspectionRecord(convertResult(wordsResult.optJSONObject("检验记录")));
				backDto.setApprovedLoad(convertResult(wordsResult.optJSONObject("核定载质量 ")));
				backDto.setCurbWeight(convertResult(wordsResult.optJSONObject("整备质量")));
				backDto.setOverallSize(convertResult(wordsResult.optJSONObject("外廓尺寸")));
				backDto.setApprovedCapacityNum(convertResult(wordsResult.optJSONObject("核定载人数")));
				backDto.setTotalMass(convertResult(wordsResult.optJSONObject("总质量")));
				backDto.setFuelType(convertResult(wordsResult.optJSONObject("燃油类型")));
				backDto.setTractionMass(convertResult(wordsResult.optJSONObject("准牵引总质量")));
				backDto.setComments(convertResult(wordsResult.optJSONObject("备注")));
				backDto.setArchivesNum(convertResult(wordsResult.optJSONObject("档案编号")));
				backDto.setLicensePlateNum(convertResult(wordsResult.optJSONObject("号牌号码")));
				result.setVehicleLicenseOcrBack(backDto);
			}

		} catch (BusinessException e) {
			log.info("行驶证识别-{}业务异常，原因{}", vehicleLicenseSide, e.getMessage());
			result.setStatus(ResultStatus.failure);
			result.setDetail(e.getMessage());
		} catch (Exception e) {
			log.info("行驶证识别-{}-系统异常，原因{}", vehicleLicenseSide, e.getMessage());
			result.setStatus(ResultStatus.failure);
			result.setDetail("系统异常");
		}
		return result;
	}

	/**
	 * 时间格式转换
	 * 
	 * @param str
	 * @return
	 */
	private String formatDateStr(String str) {
		try {
			return Dates.format(Dates.parse(str, "yyyyMMdd"), Dates.CHINESE_DATE_FORMAT_LINE);
		} catch (Exception e) {
			return str;
		}
	}

	/**
	 * 百度接口 初始化对象
	 * 
	 * @return
	 */
	private AipOcr initClient() {
		return new AipOcr(ocrProperties.getAppId(), ocrProperties.getApiKey(), ocrProperties.getSecretKey());
	}

	/**
	 * 百度结果转换
	 * 
	 * @param objectName
	 * @return
	 */
	private String convertResult(JSONObject objectName) {
		return objectName != null ? objectName.optString("words") : null;
	}

}

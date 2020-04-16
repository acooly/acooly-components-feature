package com.acooly.module.ocr.service;

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

/**
 * @author liangsong
 * @date 2020-03-25 14:45
 *
 * 
 *       <li>银行卡，
 *       <li>身份证，
 *       <li>营业执照，
 *       <li>驾驶证，
 *       <li>行驶证，
 */
public interface OcrService {

	/**
	 * 身份证识别
	 * 
	 * @param order
	 * @return
	 */
	IdCardOcrResult idCardOcr(IdCardOcrOrder order);

	/**
	 * 驾驶证识别
	 * 
	 * @param order
	 * @return
	 */
	DriverLicenseOcrResult driverLicenseOcr(DriverLicenseOcrOrder order);

	/**
	 * 营业执照
	 * 
	 * @param order
	 * @return
	 */
	BusinessLicenseOcrResult businessLicenseOcr(BusinessLicenseOcrOrder order);

	/**
	 * 银行卡识别接口
	 * 
	 * @param order
	 * @return
	 */
	BankcardOcrResult bankcardOcr(BankcardOcrOrder order);

	/**
	 * 行驶证识别
	 * 
	 * @param order
	 * @return
	 */
	VehicleLicenseOcrResult vehicleLicenseOcr(VehicleLicenseOcrOrder order);
}

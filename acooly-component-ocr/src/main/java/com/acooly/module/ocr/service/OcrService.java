package com.acooly.module.ocr.service;

import com.acooly.module.ocr.order.DriverLicenseOcrOrder;
import com.acooly.module.ocr.order.IdCardOcrOrder;
import com.acooly.module.ocr.result.DriverLicenseOcrResult;
import com.acooly.module.ocr.result.IdCardOcrResult;

/**
 * @author liangsong
 * @date 2020-03-25 14:45
 */
public interface OcrService {

    /**
     * 身份证识别
     * @param order
     * @return
     */
    IdCardOcrResult idCardOcr(IdCardOcrOrder order);

    /**
     * 驾驶证识别
     * @param order
     * @return
     */
    DriverLicenseOcrResult driverLicenseOcr(DriverLicenseOcrOrder order);
}

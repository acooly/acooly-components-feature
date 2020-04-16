package com.acooly.module.ocr.result;

import com.acooly.core.common.facade.ResultBase;
import com.acooly.module.ocr.dto.VehicleLicenseOcrBackDto;
import com.acooly.module.ocr.dto.VehicleLicenseOcrFrontDto;

import lombok.Data;

/**
 * @author cuifuq
 */
@Data
public class VehicleLicenseOcrResult extends ResultBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**百度流水号**/
	private String logId;

	/**行驶证 正面**/
	private VehicleLicenseOcrFrontDto vehicleLicenseOcrFront;

	/**行驶证 背面**/
	private VehicleLicenseOcrBackDto vehicleLicenseOcrBack;

}

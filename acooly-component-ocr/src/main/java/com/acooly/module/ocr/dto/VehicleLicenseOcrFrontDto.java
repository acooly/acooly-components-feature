package com.acooly.module.ocr.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author cuifuq
 * 
 * 行驶证 正面
 */
@Data
public class VehicleLicenseOcrFrontDto implements Serializable {

	/**品牌型号**/
	private String brandModel;

	/**发证日期**/
	private String issueDate;

	/**使用性质**/
	private String useNature;

	/**发动机号码**/
	private String engineNum;

	/**号牌号码**/
	private String licensePlateNum;

	/**所有人**/
	private String owner;

	/**住址**/
	private String address;

	/**注册日期**/
	private String registerDate;

	/**车辆识别代号**/
	private String VIN;

	/**车辆类型**/
	private String vehicleType;
}

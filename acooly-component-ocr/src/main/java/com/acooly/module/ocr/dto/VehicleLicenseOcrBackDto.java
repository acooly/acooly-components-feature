package com.acooly.module.ocr.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author cuifuq 行驶证 背面
 * 
 */
@Data
public class VehicleLicenseOcrBackDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 检验记录 **/
	private String inspectionRecord;

	/** 核定载质量 **/
	private String approvedLoad;

	/** 整备质量 **/
	private String curbWeight;

	/** 外廓尺寸 **/
	private String overallSize;

	/** 核定载人数 **/
	private String approvedCapacityNum;

	/** 总质量 **/
	private String totalMass;

	/** 燃油类型 **/
	private String fuelType;

	/** 准牵引总质量 **/
	private String tractionMass;

	/** 备注 **/
	private String comments;

	/** 档案编号 **/
	private String archivesNum;

	/** 号牌号码 **/
	private String licensePlateNum;
}

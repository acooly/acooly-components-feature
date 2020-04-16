package com.acooly.module.ocr.order;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.acooly.module.ocr.enums.BusinessLicenseAccuracyEnum;

import lombok.Data;

/**
 * @author cuifuq
 * 
 */
@Data
public class BusinessLicenseOcrOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 营业执照图片数据（文件字节流，暂不支持外部url）
	 */
	@NotNull
	private byte[] image;

	/**
	 * 可选值 true,false是否检测图像朝向， <br>
	 * 默认不检测，即：false。可选值包括true - 检测朝向；false - 不检测朝向。 <br>
	 * 朝向是指输入图像是正常方向、逆时针旋转90/180/270度 <br>
	 * - true：检测朝向； <br>
	 * - false：不检测朝向。
	 */
	private boolean detectDirection = false;

	/**
	 * 可选值：normal,high参数选normal或为空使用快速服务；选择high使用高精度服务，但是时延会根据具体图片有相应的增加
	 */
	private BusinessLicenseAccuracyEnum accuracy = BusinessLicenseAccuracyEnum.normal;
}

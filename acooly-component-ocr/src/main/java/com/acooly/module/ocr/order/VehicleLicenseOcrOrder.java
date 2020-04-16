package com.acooly.module.ocr.order;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.acooly.module.ocr.enums.VehicleLicenseSideEnum;

import lombok.Data;

/**
 * @author cuifuq
 * 
 *         行驶证识别接口
 */
@Data
public class VehicleLicenseOcrOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 行驶证-图片数据（文件字节流，暂不支持外部url）
	 */
	@NotNull
	private byte[] image;

	/**
	 * <li>- false：默认值不进行图像方向自动矫正
	 * <li>- true: 开启图像方向自动矫正功能，可对旋转 90/180/270 度的图片进行自动矫正并识别
	 */
	private boolean detectDirection = false;

	/**
	 * <li>- front：默认值，识别行驶证主页
	 * <li>- back：识别行驶证副页
	 */
	@NotNull
	private VehicleLicenseSideEnum vehicleLicenseSide = VehicleLicenseSideEnum.front;

	/**
	 * <li>- false：默认值，不进行归一化处理
	 * <li>- true：对输出字段进行归一化处理，将新/老版行驶证的“注册登记日期/注册日期”统一为”注册日期“进行输出
	 */
	private boolean unified = false;

}

package com.acooly.module.ocr.order;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author cuifuq
 * 
 *         银行卡识别接口
 */
@Data
public class BankcardOcrOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 银行卡图片数据（文件字节流，暂不支持外部url）
	 */
	@NotNull
	private byte[] image;

	/**
	 * 是否检测图像朝向，默认检测；； 朝向是指输入图像是正常方向、逆时针旋转90/180/270度。<br>
	 * - true：检测朝向；<br>
	 * - false：不检测朝向。
	 */
	private boolean detectDirection = true;
}

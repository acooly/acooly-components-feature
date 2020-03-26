package com.acooly.module.ocr.order;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author liangsong
 * @date 2020-03-26 09:40
 */
@Data
public class DriverLicenseOcrOrder implements Serializable {

    /**
     * 身份证图片数据（文件字节流，暂不支持外部url）
     */
    @NotNull
    private byte[] image;

    /**
     * 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     */
    private boolean detectDirection = false;
}

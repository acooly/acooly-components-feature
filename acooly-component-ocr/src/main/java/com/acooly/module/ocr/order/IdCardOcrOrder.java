package com.acooly.module.ocr.order;

import com.acooly.module.ocr.enums.IdCardSideEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author liangsong
 * @date 2020-03-25 14:48
 */
@Data
public class IdCardOcrOrder implements Serializable {

    /**
     * 身份证图片数据（文件字节流，暂不支持外部url）
     */
    @NotNull
    private byte[] image;

    /**
     * 身份证正反面
     */
    @NotNull
    private IdCardSideEnum idCardSide;

    /**
     * 是否检测图像朝向，默认不检测，即：false。朝向是指输入图像是正常方向、逆时针旋转90/180/270度。可选值包括:<br>- true：检测朝向；<br>- false：不检测朝向。
     */
    private boolean detectDirection = false;

    /**
     * 是否开启身份证风险类型(身份证复印件、临时身份证、身份证翻拍、修改过的身份证)功能，默认不开启，即：false。可选值:true-开启；false-不开启
     */
    private boolean detectRisk = false;
}

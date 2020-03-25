package com.acooly.module.face.order;

import com.acooly.module.face.enums.ControlEnum;
import com.acooly.module.face.enums.ImageTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author liangsong
 * @date 2020-03-24 11:14
 */
@Data
public class PersonVerifyOrder implements Serializable {

    /**
     * 图片
     */
    @NotBlank
    private String image;

    /**
     * 图片类型（base64类型时，需去掉编码头data:image/jpg;base64）
     */
    @NotNull
    private ImageTypeEnum imageType;

    /**
     * 证件号
     */
    @NotBlank
    private String idCardNumber;

    /**
     * 姓名
     */
    @NotBlank
    private String name;

    /**
     * 图片质量控制
     */
    private ControlEnum qualityControl = ControlEnum.NONE;

    /**
     * 活体检测控制
     */
    private ControlEnum livenessControl = ControlEnum.NONE;
}

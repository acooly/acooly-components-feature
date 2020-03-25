package com.acooly.module.face.order;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author liangsong
 * @date 2020-03-24 15:16
 */
@Data
public class IdMatchOrder implements Serializable {

    /**
     * 身份证号码
     */
    @NotBlank
    private String idCardNum;

    /**
     * 姓名
     */
    @NotBlank
    private String name;
}

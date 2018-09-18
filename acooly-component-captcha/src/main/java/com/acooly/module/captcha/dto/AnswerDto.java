package com.acooly.module.captcha.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author shuijing
 */
@Data
public class AnswerDto<UA> implements Serializable {

    private String captchaId;
    /**
     * 用户输入的验证码回答
     */
    private UA userAnswer;

    public AnswerDto(String captchaId, UA userAnswer) {
        this.captchaId = captchaId;
        this.userAnswer = userAnswer;
    }
}

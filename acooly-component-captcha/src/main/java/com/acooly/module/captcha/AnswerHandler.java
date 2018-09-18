package com.acooly.module.captcha;

import com.acooly.module.captcha.dto.AnswerDto;
import com.acooly.module.captcha.exception.CaptchaValidateException;

/**
 * @author shuijing
 */
public interface AnswerHandler<UA> {
    boolean isValid(AnswerDto<UA> answerDto) throws CaptchaValidateException;
}

package com.acooly.module.captcha.support;

import com.acooly.module.captcha.Validator;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author shuijing
 */
public class StringCaseSensitiveValidator implements Validator<String, String>, Serializable {
    @Override
    public boolean validate(String answer, String userAnswer) {
        return StringUtils.equals(answer, userAnswer);
    }
}

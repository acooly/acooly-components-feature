package com.acooly.module.captcha.support;

import com.acooly.module.captcha.Validator;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author shuijing
 */
public class StringValidator implements Validator<String, String>, Serializable {
    @Override
    public boolean validate(String answer, String userAnswer) {
        return StringUtils.equalsIgnoreCase(answer, userAnswer);
    }
}

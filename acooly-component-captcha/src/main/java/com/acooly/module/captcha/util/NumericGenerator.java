package com.acooly.module.captcha.util;

/**
 * @author shuijing
 */
public interface NumericGenerator {

    String getNextNumberAsString();

    int maxLength();

    int minLength();
}

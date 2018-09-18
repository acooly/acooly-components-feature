package com.acooly.module.captcha.util;

/**
 * @author shuijing
 */
public interface RandomStringGenerator {

    int getMinLength();

    int getMaxLength();

    String getNewString();

    byte[] getNewStringAsBytes();
}

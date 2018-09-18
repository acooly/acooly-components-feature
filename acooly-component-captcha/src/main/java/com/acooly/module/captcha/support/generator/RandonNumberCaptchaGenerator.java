package com.acooly.module.captcha.support.generator;


import static com.acooly.module.captcha.CaptchaProperties.DEFAULT_LENGTH;

/**
 * @author shuijing
 */
public class RandonNumberCaptchaGenerator extends RandomWordCaptchaGenerator {

    public RandonNumberCaptchaGenerator() {
        this(DEFAULT_LENGTH);
    }

    public RandonNumberCaptchaGenerator(int length) {
        super(length, new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'});
    }
}

package com.acooly.module.captcha;

/**
 * @author shuijing
 */
public interface Validator<V, UA> {
    boolean validate(V value, UA userAnswer);
}

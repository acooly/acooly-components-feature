package com.acooly.module.captcha.support;

import com.acooly.module.captcha.Answer;
import lombok.Data;

/**
 * @author shuijing
 */
@Data
public class DefaultAnswer<A> implements Answer<A> {

    private A value;

    public DefaultAnswer(A value) {
        this.value = value;
    }

    @Override
    public A getValue() {
        return this.value;
    }
}

package com.acooly.module.captcha;

import java.io.Serializable;

/**
 * @author shuijing
 */
public interface Answer<A> extends Serializable {
    A getValue();
}

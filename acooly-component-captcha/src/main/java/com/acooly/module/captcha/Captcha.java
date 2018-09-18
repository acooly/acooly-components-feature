package com.acooly.module.captcha;

import java.io.Serializable;

/**
 * @author shuijing
 */
public interface Captcha<V> extends Serializable {

    String getId();

    V getValue();

    long getExpiredTimeMillis();
}

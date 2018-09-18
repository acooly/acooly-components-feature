package com.acooly.module.captcha.repository;

import com.acooly.module.captcha.Captcha;

/**
 * @author shuijing
 */
public interface CaptchaRepository<K, V extends Captcha> {

    void set(K key, V value, long seconds);

    V get(K key);

    void delete(K key);
}

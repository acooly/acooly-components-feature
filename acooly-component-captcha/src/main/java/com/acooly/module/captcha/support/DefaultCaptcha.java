package com.acooly.module.captcha.support;

import com.acooly.module.captcha.Captcha;
import lombok.Data;

/**
 * @author shuijing
 */
@Data
public class DefaultCaptcha<V> implements Captcha<V> {

    private V value;

    private String id;

    private long expiredTimeMillis;

    //验证次数
    private int validTimes;

    public DefaultCaptcha(String id, V value, long expiredTimeMillis) {
        this.id = id;
        this.value = value;
        this.expiredTimeMillis = expiredTimeMillis;
    }

    public void incrValidTimes() {
        this.validTimes = validTimes + 1;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public long getExpiredTimeMillis() {
        return this.expiredTimeMillis;
    }
}

package com.acooly.module.captcha.support.generator;

import com.acooly.module.captcha.Captcha;
import com.acooly.module.captcha.CaptchaGenerator;
import com.acooly.module.captcha.exception.CaptchaValidateException;
import com.acooly.module.captcha.repository.CaptchaRepository;
import com.acooly.module.captcha.support.DefaultCaptcha;
import lombok.Setter;

/**
 * @author shuijing
 */
@Setter
public abstract class AbstractCaptchaGenerator<V> implements CaptchaGenerator<V> {

    //默认在超过设置的过期时间后，验证码再保存2小时
    public static final long DEFAULT_CACHE_EXPIRED_TIME = 2 * 60 * 60;

    private CaptchaRepository<String, Captcha> captchaRepository;

    private Long defaultSeconds;

    protected Captcha<V> createCaptcha(String id, V value, Long seconds)
            throws CaptchaValidateException {

        seconds = seconds == null ? defaultSeconds : seconds;
        long expiredTimeMillis = System.currentTimeMillis() + seconds * 1000;

        DefaultCaptcha<V> captcha = new DefaultCaptcha(id, value, expiredTimeMillis);

        captchaRepository.set(id, captcha, seconds + DEFAULT_CACHE_EXPIRED_TIME);

        return captcha;
    }
}

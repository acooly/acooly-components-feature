package com.acooly.module.captcha;

import com.acooly.module.captcha.exception.CaptchaGenerateException;
import com.acooly.module.captcha.exception.CaptchaValidateException;

/**
 * 验证码
 *
 * @author shuijing
 */
public interface CaptchaService {
    /**
     * 生成验证码
     *
     * @return Captcha
     * @throws CaptchaGenerateException
     */
    Captcha getCaptcha() throws CaptchaGenerateException;

    /**
     * 生成验证码
     *
     * @param seconds 过期时间，空则用默认配置CaptchaProperties#expireSeconds
     * @return Captcha
     * @throws CaptchaGenerateException
     */
    Captcha getCaptcha(Long seconds) throws CaptchaGenerateException;

    /**
     * 生成验证码
     *
     * @param key     生成Captcha key，{@link Captcha#getId()}=key
     * @param seconds 过期时间，空则用默认配置CaptchaProperties#expireSeconds
     * @return Captcha
     * @throws CaptchaGenerateException
     */
    Captcha getCaptcha(String key, Long seconds) throws CaptchaGenerateException;

    /**
     * 生成验证码
     *
     * @param key          生成Captcha key
     * @param businessCode 业务编码，用于区分key
     * @param seconds      过期时间，空则用默认配置CaptchaProperties#expireSeconds
     * @return Captcha
     * @throws CaptchaGenerateException
     */
    Captcha getCaptcha(String key, String businessCode, Long seconds) throws CaptchaGenerateException;

    /**
     * 验证验校验
     *
     * @param key          生成Captcha key
     * @param businessCode 业务编码，用于区分key
     * @param userAnswer   用户填写的验证码
     * @throws CaptchaValidateException
     */
    void validateCaptcha(String key, String businessCode, String userAnswer) throws CaptchaValidateException;

    /**
     * 验证验校验
     *
     * @param captchaId  Captcha#getId(),如果有businessCode，captchaId可以用{@link CaptchaServiceImpl#mergeKey(java.lang.String, java.lang.String)}获取
     * @param userAnswer 用户填写的验证码
     * @throws CaptchaValidateException
     */
    void validateCaptcha(String captchaId, String userAnswer) throws CaptchaValidateException;
}

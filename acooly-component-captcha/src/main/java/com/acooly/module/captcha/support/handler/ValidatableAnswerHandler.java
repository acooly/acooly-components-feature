package com.acooly.module.captcha.support.handler;

import com.acooly.module.captcha.AnswerHandler;
import com.acooly.module.captcha.Captcha;
import com.acooly.module.captcha.CaptchaProperties;
import com.acooly.module.captcha.Validator;
import com.acooly.module.captcha.dto.AnswerDto;
import com.acooly.module.captcha.exception.CaptchaValidateException;
import com.acooly.module.captcha.repository.CaptchaRepository;
import com.acooly.module.captcha.support.DefaultCaptcha;
import lombok.extern.slf4j.Slf4j;

import static com.acooly.module.captcha.support.generator.AbstractCaptchaGenerator.DEFAULT_CACHE_EXPIRED_TIME;

/**
 * @author shuijing
 */
@Slf4j
public class ValidatableAnswerHandler<V, UA> implements AnswerHandler<UA> {

    private CaptchaRepository repository;

    private Validator<V, UA> validator;

    private CaptchaProperties properties;

    public ValidatableAnswerHandler(
            CaptchaRepository repository, Validator validator, CaptchaProperties properties) {
        this.repository = repository;
        this.validator = validator;
        this.properties = properties;
    }

    @Override
    public boolean isValid(AnswerDto<UA> answerDto) throws CaptchaValidateException {

        Captcha<V> captcha = repository.get(answerDto.getCaptchaId());
        if (captcha == null) {
            throw new CaptchaValidateException("CAPCHA_IS_NULL", "验证码不存在");
        }
        if (System.currentTimeMillis() > captcha.getExpiredTimeMillis()) {
            throw new CaptchaValidateException("CAPCHA_TIMEOUT", "验证码已过期");
        }

        boolean validated = validator.validate(captcha.getValue(), answerDto.getUserAnswer());

        if (!validated) {
            throw new CaptchaValidateException("CAPCHA_VERIFY_FAIL", "验证码不正确");
        }

        if (aboveValidTimes(captcha)) {
            throw new CaptchaValidateException("CAPCHA_VERIFY_ABOVE_TIMES", "验证码验证次超过限制");
        }

        //      if (validated) {
        //        repository.delete(answerDto.getCaptchaId());
        //      }

        return validated;
    }

    boolean aboveValidTimes(Captcha captcha) {

        DefaultCaptcha defaultCaptcha = (DefaultCaptcha) captcha;
        //获取新的保存时间
        long expiredTimeMillis = captcha.getExpiredTimeMillis();
        long leftExpiredTime = expiredTimeMillis - System.currentTimeMillis();
        long newSaveTime = leftExpiredTime + DEFAULT_CACHE_EXPIRED_TIME * 1000;

        int validTimes = defaultCaptcha.getValidTimes();
        if (validTimes > properties.getValidTimes() - 1) {
            return true;
        }
        defaultCaptcha.incrValidTimes();
        repository.set(captcha.getId(), captcha, newSaveTime);
        return false;
    }
}

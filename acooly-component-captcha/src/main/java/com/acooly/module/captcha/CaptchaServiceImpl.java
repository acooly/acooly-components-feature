package com.acooly.module.captcha;

import com.acooly.core.utils.Ids;
import com.acooly.module.captcha.dto.AnswerDto;
import com.acooly.module.captcha.exception.CaptchaGenerateException;
import com.acooly.module.captcha.exception.CaptchaValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shuijing
 */
@Service("captchaService")
public class CaptchaServiceImpl implements CaptchaService {

    //private CaptchaProperties captchaProperties;

    @Autowired
    private CaptchaGenerator captchaGenerator;

    @Autowired
    private AnswerHandler<String> answerHandler;

    public static String mergeKey(String key, String businessCode) {

        if (key == null && businessCode == null) {
            //gen key
            return Ids.getDid();
        }
        if (businessCode == null) {
            return key;
        }
        return key + "," + businessCode;
    }

    @Override
    public Captcha getCaptcha() throws CaptchaGenerateException {
        return getCaptcha(null);
    }

    @Override
    public Captcha getCaptcha(Long seconds) throws CaptchaGenerateException {
        return getCaptcha(null, seconds);
    }

    @Override
    public Captcha getCaptcha(String key, Long seconds) throws CaptchaGenerateException {
        return getCaptcha(key, null, seconds);
    }

    @Override
    public Captcha getCaptcha(String key, String businessCode, Long seconds)
            throws CaptchaGenerateException {
        return captchaGenerator.createCaptcha(mergeKey(key, businessCode), seconds);
    }

    @Override
    public void validateCaptcha(String key, String businessCode, String userAnswer) throws CaptchaValidateException {
        answerHandler.isValid(new AnswerDto<>(mergeKey(key, businessCode), userAnswer));
    }

    @Override
    public void validateCaptcha(String captchaId, String userAnswer) throws CaptchaValidateException {
        answerHandler.isValid(new AnswerDto<>(captchaId, userAnswer));
    }
}

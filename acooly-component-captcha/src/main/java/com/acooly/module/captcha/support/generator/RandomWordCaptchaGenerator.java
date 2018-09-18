package com.acooly.module.captcha.support.generator;

import com.acooly.module.captcha.Captcha;
import com.acooly.module.captcha.exception.CaptchaGenerateException;
import lombok.Setter;

import java.util.concurrent.ThreadLocalRandom;

import static com.acooly.module.captcha.CaptchaProperties.DEFAULT_LENGTH;

/**
 * @author shuijing
 */
@Setter
public class RandomWordCaptchaGenerator extends AbstractCaptchaGenerator<String> {

    private int length;

    private char[] srcChars;

    public RandomWordCaptchaGenerator() {
        this.length = DEFAULT_LENGTH;
        this.srcChars =
                new char[]{
                        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'k', 'm', 'n', 'p', 'r', 'w', 'x', 'y', '2', '3',
                        '4', '5', '6', '7', '8'
                };
    }

    public RandomWordCaptchaGenerator(int length, char[] srcChars) {
        this.length = length;
        this.srcChars = srcChars;
    }

    @Override
    public Captcha<String> createCaptcha(String key, Long seconds) throws CaptchaGenerateException {
        try {
            StringBuilder capText = new StringBuilder();
            for (int i = 0; i < length; i++) {
                capText.append(srcChars[ThreadLocalRandom.current().nextInt(srcChars.length)]);
            }
            String value = capText.toString();
            return createCaptcha(key, value, seconds);
        } catch (Exception e) {
            throw new CaptchaGenerateException(e.getMessage(), e);
        }
    }
}

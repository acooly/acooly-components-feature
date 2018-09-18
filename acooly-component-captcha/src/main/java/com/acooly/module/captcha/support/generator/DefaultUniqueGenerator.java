package com.acooly.module.captcha.support.generator;

import com.acooly.module.captcha.Captcha;
import com.acooly.module.captcha.exception.CaptchaGenerateException;
import com.acooly.module.captcha.util.DefaultLongNumericGenerator;
import com.acooly.module.captcha.util.DefaultRandomStringGenerator;
import com.acooly.module.captcha.util.NumericGenerator;
import com.acooly.module.captcha.util.RandomStringGenerator;
import org.apache.commons.lang3.StringUtils;

/**
 * @author shuijing
 */
public class DefaultUniqueGenerator extends AbstractCaptchaGenerator {

    private NumericGenerator numericGenerator;

    private RandomStringGenerator randomStringGenerator;

    private String suffix;
    private int initialCapacity;

    public DefaultUniqueGenerator() {
        this(DefaultRandomStringGenerator.DEFAULT_MAX_RANDOM_LENGTH);
    }

    public DefaultUniqueGenerator(final int maxLength) {
        this(maxLength, null);
    }

    public DefaultUniqueGenerator(final int maxLength, final String suffix) {
        setMaxLength(maxLength);
        setSuffix(suffix);
    }

    public DefaultUniqueGenerator(
            final NumericGenerator numericGenerator,
            final RandomStringGenerator randomStringGenerator,
            final String suffix) {
        this.randomStringGenerator = randomStringGenerator;
        this.numericGenerator = numericGenerator;
        setSuffix(suffix);
    }

    public String getNewTokenId(final String prefix) {
        final String number = this.numericGenerator.getNextNumberAsString();
        final int capacity = (prefix != null ? prefix.length() : 0) + initialCapacity + number.length();
        StringBuilder stringBuilder = new StringBuilder(capacity);
        if (prefix != null) {
            stringBuilder.append(prefix).append('-');
        }
        return stringBuilder
                .append(number)
                .append('-')
                .append(this.randomStringGenerator.getNewString())
                .append(this.suffix)
                .toString();
    }

    public void setSuffix(final String suffix) {
        this.suffix = StringUtils.isNoneBlank(suffix) ? '-' + suffix : StringUtils.EMPTY;
        initialCapacity = 2 + this.suffix.length() + this.randomStringGenerator.getMaxLength();
    }

    public void setMaxLength(final int maxLength) {
        this.randomStringGenerator = new DefaultRandomStringGenerator(maxLength);
        this.numericGenerator = new DefaultLongNumericGenerator(1);
    }

    @Override
    public Captcha createCaptcha(String key, Long seconds) throws CaptchaGenerateException {
        String tokenId = getNewTokenId(null);
        return createCaptcha(key, tokenId, seconds);
    }
}

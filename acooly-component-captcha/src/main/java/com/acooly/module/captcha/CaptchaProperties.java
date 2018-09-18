package com.acooly.module.captcha;

import com.acooly.core.utils.enums.Messageable;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import static com.acooly.module.captcha.CaptchaProperties.PREFIX;

/**
 * @author shuijing
 */
@Data
@Slf4j
@Validated
@ConfigurationProperties(prefix = PREFIX)
public class CaptchaProperties {
    public static final String PREFIX = "acooly.captcha";

    public static final int DEFAULT_LENGTH = 4;

    public boolean enable;

    /**
     * 过期时间,单位秒,默认300S(5分钟)
     */
    private Long expireSeconds = 300L;

    /**
     * 验证码长度
     */
    private int captchaLength = DEFAULT_LENGTH;

    /**
     * 验证码最大验证次数
     */
    private int validTimes = 200;

    private GeneratorType generatorType = GeneratorType.RANDOM_NUMBER;

    private AnswerHandlerType handlerType = AnswerHandlerType.DEFUALT_ANSWER_HANDLER;

    public enum GeneratorType implements Messageable {
        RANDOM_WORD("randomWordCaptchaGenerator", "随机字符"),

        RANDOM_NUMBER("randonNumberCaptchaGenerator", "随机数字"),

        DEFAULT_UNIQUE_GENERATOR("defaultUniqueGenerator", "唯一字符串(生成token建议使用)");

        private final String code;
        private final String message;

        GeneratorType(String code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public String code() {
            return this.code;
        }

        @Override
        public String message() {
            return this.message;
        }
    }

    public enum AnswerHandlerType implements Messageable {
        DEFUALT_ANSWER_HANDLER("validatableAnswerHandler", "字符验证器"),

        CASE_SENSITIVE_HANDLER("caseSensitiveHandler", "字符大小写敏感验证器");

        private final String code;
        private final String message;

        AnswerHandlerType(String code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public String code() {
            return this.code;
        }

        @Override
        public String message() {
            return this.message;
        }
    }
}

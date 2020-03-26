package com.acooly.module.ocr;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import static com.acooly.module.ocr.OcrProperties.PREFIX;

/**
 * @author liangsong
 * @date 2020-03-20 09:44
 */
@ConfigurationProperties(prefix = PREFIX)
@Data
@Slf4j
@Validated
public class OcrProperties implements InitializingBean {

    public static final String PREFIX = "acooly.ocr";

    private String appId;

    private String apiKey;

    private String secretKey;

    public boolean enable = true;

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}

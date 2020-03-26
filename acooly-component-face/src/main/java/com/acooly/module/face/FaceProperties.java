package com.acooly.module.face;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import static com.acooly.module.face.FaceProperties.PREFIX;


/**
 * @author liangsong
 * @date 2020-03-20 09:44
 */
@ConfigurationProperties(prefix = PREFIX)
@Data
@Slf4j
@Validated
public class FaceProperties implements InitializingBean {

    public static final String PREFIX = "acooly.face";

    private String appId;

    private String apiKey;

    private String secretKey;

    public boolean enable = true;

    /**
     * 人脸识别误拒的阀值
     * 0.05 万分之一误拒率的阈值
     * 0.3 千分之一误拒率的阈值 （百度推荐默认值）
     * 0.9 百分之一误拒率的阈值
     */
    private String faceThreshold = "0.3";

    /**
     * 验证码相似程度 0：完全不一致，1：完全一致
     * 0.75 （百度推荐默认值）
     */
    private String codeThreshold = "0.75";

    /**
     * 是否要求唇语必须验证通过
     */
    private boolean lipLanguage = false;

    /**
     * 身份校验通过阀值，范围[0,100]推荐值80
     */
    private String personVerifyThreshold = "80";

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}

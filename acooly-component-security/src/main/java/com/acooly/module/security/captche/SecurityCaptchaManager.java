/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2020-04-22 11:07
 */
package com.acooly.module.security.captche;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.system.IPUtil;
import com.acooly.module.security.config.SecurityProperties;
import com.acooly.module.security.shiro.exception.InvaildCaptchaException;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * 内置kaptcha验证码管理实现
 * <p>
 * 因等保3要求，登录前后会话ID会更新，摒弃使用session记录验证次数，使用redis控制验证次数。
 *
 * @author zhangpu
 * @date 2020-04-22 11:07
 */
@Slf4j
@Component
public class SecurityCaptchaManager {

    public static final String VERIFY_CODE_KEY = "security.captcha.verify_code_key";
    public static final String VERIFY_TIMES_KEY = "security.captcha.verify_times_key";
    public static final String VERIFY_FIRST_KEY = "security.captcha.verify_first_key";

    private DefaultKaptcha kaptcha = new DefaultKaptcha();
    private int maxTryCount = 3;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private SecurityProperties securityProperties;

    public SecurityCaptchaManager() {
    }

    @PostConstruct
    private void initConfig() {
        SecurityProperties.Captcha.Kaptcha kaptchaConfig = securityProperties.getCaptcha().getKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.image.width", "" + kaptchaConfig.getWidth());
        properties.setProperty("kaptcha.image.height", "" + kaptchaConfig.getHeight());
        properties.setProperty(
                "kaptcha.textproducer.char.string", "ABCDEHJKMNRSTUWXY235689acdehkmntuwxy");
        properties.setProperty("kaptcha.textproducer.font.size", "" + kaptchaConfig.getFontSize());
        properties.setProperty("kaptcha.textproducer.font.color", "" + kaptchaConfig.getFontColor());
        properties.setProperty("kaptcha.textproducer.char.space", "2");
        properties.setProperty("kaptcha.textproducer.char.length", "" + kaptchaConfig.getCharCount());
        properties.setProperty(
                "kaptcha.textproducer.font.names",
                "defhollow,customizeHollow,customizeBlack,customizeBold");
        properties.setProperty("kaptcha.word.impl", WordRender.class.getName());
        properties.setProperty("kaptcha.obscurificator.impl", WaterRender.class.getName());
        properties.setProperty("kaptcha.background.clear.from", "white");
        properties.setProperty("kaptcha.background.clear.to", "white");
        properties.setProperty("kaptcha.noise.color", "34,114,200");
        Config conf = new Config(properties);
        kaptcha.setConfig(conf);
        this.maxTryCount = securityProperties.getCaptcha().getMaxTry();
    }


    public boolean isFirstVerify(HttpServletRequest request){
        String key = cachekey(request,VERIFY_FIRST_KEY);
        return redisTemplate.opsForValue().get(key) == null;
    }

    public void setFirstVerify(HttpServletRequest request){
        String key = cachekey(request,VERIFY_FIRST_KEY);
        redisTemplate.opsForValue().set(key, "false", 5, TimeUnit.MINUTES);
    }

    public void removeFirstVerify(HttpServletRequest request){
        String key = cachekey(request,VERIFY_FIRST_KEY);
        redisTemplate.delete(key);
    }

    public boolean verify(HttpServletRequest request, String input) {
        if (Strings.isNullOrEmpty(input)) {
            return false;
        }
        String verifyTimesKey = cachekey(request, VERIFY_TIMES_KEY);
        Integer time = (Integer) redisTemplate.opsForValue().get(verifyTimesKey);
        if (time == null) {
            time = 0;
        }
        if (time <= maxTryCount) {
            String verifyCodeKey = cachekey(request, VERIFY_CODE_KEY);
            String saved = (String) redisTemplate.opsForValue().get(verifyCodeKey);
            if (input.equalsIgnoreCase(saved)) {
                redisTemplate.delete(verifyTimesKey);
                return true;
            } else {
                redisTemplate.opsForValue().set(verifyTimesKey, Integer.valueOf(time + 1), 5, TimeUnit.MINUTES);
                log.info("tims:{}",redisTemplate.opsForValue().get(verifyTimesKey));
            }
        } else {
            log.warn("客户端ip:{}，重试验证码次数:{} ,请稍后再试。", request.getRemoteAddr(), time);
            throw new InvaildCaptchaException("验证码错误尝试次数过多，请稍后再试。");
        }
        return false;
    }

    private String cachekey(HttpServletRequest request, String postfix) {
        String salt = IPUtil.getIpAddr(request);
        if (Strings.isNullOrEmpty(salt)) {
            salt = Servlets.getParameter("_csrf");
        }

        return Apps.getAppName() + "." + salt + "." + postfix;

    }


    /**
     * 生成验证码
     *
     * @param session
     * @return
     * @throws Exception
     */
    public byte[] generateImage(HttpServletRequest request) throws Exception {
        CaptchaResult captchaResult = generate();
        redisTemplate.opsForValue().set(cachekey(request, VERIFY_CODE_KEY), captchaResult.getAnswer(), 5, TimeUnit.MINUTES);
        return captchaResult.getImageByte();
    }

    public CaptchaResult generate(String imageType) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String word = kaptcha.createText();
        BufferedImage image = kaptcha.createImage(word);
        try {
            ImageIO.write(image, imageType, baos);
        } finally {
            baos.flush();
            baos.close();
        }
        return new CaptchaResult(baos.toByteArray(), imageType, word);
    }

    public CaptchaResult generate() throws Exception {
        return generate("png");
    }


}

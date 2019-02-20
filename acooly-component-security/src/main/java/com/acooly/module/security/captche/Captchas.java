/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2016-10-29 22:59 创建
 */
package com.acooly.module.security.captche;

import com.acooly.core.common.boot.Apps;
import com.acooly.module.security.config.SecurityProperties;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author qiubo
 */
public class Captchas {
    public static final String VERIFY_CODE_KEY = "VERIFY_CODE_KEY";
    public static final int MAX_TRY = 10;
    private static final Logger logger = LoggerFactory.getLogger(Captchas.class);
    private static final String VERIFY_TIMES_KEY = "VERIFY_TIMES_KEY";
    private static final Captchas DAFULAT = new Captchas();

    /* image height */
    private int height;

    /* image width */
    private int width;

    /* rgb color  use "," to split like 110,21,32 */
    private String fontColor;

    /* font display size */
    private int fontSize;

    /* the number of chars*/
    private int charCount;

    private DefaultKaptcha kaptcha;


    public DefaultKaptcha getKaptcha() {
        return kaptcha;
    }

    public void setKaptcha(DefaultKaptcha kaptcha) {
        this.kaptcha = kaptcha;
    }

    public Captchas(int height, int width, String fontColor, int fontSize, int charCount) {
        this.height = height;
        this.width = width;
        this.fontColor = fontColor;
        this.charCount = charCount;
        this.fontSize = fontSize;
        initConfig();
    }

    public Captchas() {
        SecurityProperties.Captcha.Kaptcha kaptcha =
                Apps.buildProperties(SecurityProperties.class).getCaptcha().getKaptcha();
        this.height = kaptcha.getHeight();
        this.width = kaptcha.getWidth();
        this.fontColor = kaptcha.getFontColor();
        this.charCount = kaptcha.getCharCount();
        this.fontSize = kaptcha.getFontSize();
        initConfig();
    }

    public static byte[] generateImage(HttpSession session) throws Exception {
        CaptchaResult captchaResult = DAFULAT.generate();
        session.setAttribute(VERIFY_CODE_KEY, captchaResult.getAnswer());
        session.setAttribute(VERIFY_TIMES_KEY, 0);
        return captchaResult.getImageByte();
    }

    public static boolean verify(HttpServletRequest request, String input) {
        if (Strings.isNullOrEmpty(input)) {
            return false;
        }
        HttpSession session = request.getSession();
        int time = (int) session.getAttribute(VERIFY_TIMES_KEY);
        if (time <= MAX_TRY) {
            String saved = (String) session.getAttribute(VERIFY_CODE_KEY);
            if (input.equalsIgnoreCase(saved)) {
                session.setAttribute(VERIFY_TIMES_KEY, 0);
                return true;
            } else {
                session.setAttribute(VERIFY_TIMES_KEY, time + 1);
            }
        } else {
            logger.warn("客户端ip={}，重试验证码次数={}", request.getRemoteAddr(), time);
        }
        return false;
    }

    private void initConfig() {
        kaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.image.width", "" + width);
        properties.setProperty("kaptcha.image.height", "" + height);
        properties.setProperty(
                "kaptcha.textproducer.char.string", "ABCDEHJKMNRSTUWXY235689acdehkmntuwxy");
        properties.setProperty("kaptcha.textproducer.font.size", "" + fontSize);
        properties.setProperty("kaptcha.textproducer.font.color", "" + fontColor);
        properties.setProperty("kaptcha.textproducer.char.space", "2");
        properties.setProperty("kaptcha.textproducer.char.length", "" + charCount);
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

    public static class CaptchaResult {

        private byte[] imageByte;
        private String imageType;
        private String answer;

        public CaptchaResult(byte[] imageByte, String imageType, String answer) {
            this.imageByte = imageByte;
            this.imageType = imageType;
            this.answer = answer;
        }

        public byte[] getImageByte() {
            return imageByte;
        }

        public void setImageByte(byte[] imageByte) {
            this.imageByte = imageByte;
        }

        public String getImageType() {
            return imageType;
        }

        public void setImageType(String imageType) {
            this.imageType = imageType;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        @Override
        public String toString() {
            return "Yaptcha{"
                    + "imageByte="
                    + Arrays.toString(imageByte)
                    + ", imageType='"
                    + imageType
                    + '\''
                    + ", answer='"
                    + answer
                    + '\''
                    + '}';
        }
    }
}

package com.acooly.core.test.web;

import com.acooly.module.captcha.Captcha;
import com.acooly.module.captcha.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shuijing
 */
@Slf4j
@RestController
@RequestMapping(value = "/captcha")
public class CaptchaTestController {
    @Autowired
    private CaptchaService captchaService;

    @RequestMapping("/get")
    public void getCaptcha() {
        Captcha captcha = captchaService.getCaptcha();
        log.info(
                "id:{},answer:{},getExpiredTime:{}",
                captcha.getId(),
                captcha.getValue(),
                captcha.getExpiredTimeMillis());
        captchaService.validateCaptcha(captcha.getId(), (String) captcha.getValue());
    }

    @RequestMapping("/val")
    public void validateCaptchaNull() {
        Captcha captcha = captchaService.getCaptcha(600L);
        log.info(
                "id:{},answer:{},getExpiredTime:{}",
                captcha.getId(),
                captcha.getValue(),
                captcha.getExpiredTimeMillis());
        captchaService.validateCaptcha(captcha.getId() + "aaa", captcha.getValue() + "aaa");
    }

    @RequestMapping("/vali")
    public void validateCaptcha() {
        Captcha captcha = captchaService.getCaptcha("18612288888", "openapi", null);
        log.info(
                "id:{},answer:{},getExpiredTime:{}",
                captcha.getId(),
                captcha.getValue(),
                captcha.getExpiredTimeMillis());
        captchaService.validateCaptcha(captcha.getId(), (String) captcha.getValue());
    }

    @RequestMapping("/valid")
    public void validateCaptchaHander() {
        Captcha captcha = captchaService.getCaptcha("18612288888", "openapi", null);
        log.info(
                "id:{},answer:{},getExpiredTime:{}",
                captcha.getId(),
                captcha.getValue(),
                captcha.getExpiredTimeMillis());
        captchaService.validateCaptcha(captcha.getId(), ((String) captcha.getValue()).toLowerCase());
    }
}

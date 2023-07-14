/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2023-07-14 18:17
 */
package com.acooly.module.test.capcha;

import com.acooly.module.captcha.Captcha;
import com.acooly.module.captcha.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangpu
 * @date 2023-07-14 18:17
 */
@Slf4j
@RestController
@RequestMapping("/test/captcha")
public class CaptchaTestController {

    @Autowired
    private CaptchaService captchaService;

    @RequestMapping("/get")
    public Captcha test() {
        Captcha captcha = captchaService.getCaptcha();
        captchaService.validateCaptcha(captcha.getId(), "121212");
        return captcha;
    }

}

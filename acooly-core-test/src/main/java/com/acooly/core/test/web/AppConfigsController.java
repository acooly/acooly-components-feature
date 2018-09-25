package com.acooly.core.test.web;

import com.acooly.core.common.facade.ResultBase;
import com.acooly.module.config.Configs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiuboboy@qq.com
 * @date 2018-06-20 01:33
 */
@Slf4j
@RestController
@RequestMapping(value = "/appConfig")
public class AppConfigsController {


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get() {
        String value = Configs.get("b");
        value = Configs.get("b");
        log.info("{}", value);
        Boolean c = Configs.get("c", Boolean.class);
        log.info("{}", c);
        Configs.get("b");
        ResultBase resultBase = Configs.getJsonObject("x", ResultBase.class);
        log.info("{}", resultBase);
        return value;
    }
}

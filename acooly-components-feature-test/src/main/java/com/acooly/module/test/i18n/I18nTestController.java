/**
 * openapi-framework
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2024-01-14 20:22
 */
package com.acooly.module.test.i18n;

import com.acooly.component.i18n.support.I18nMessages;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author zhangpu
 * @date 2024-01-14 20:22
 */
@Slf4j
@RestController
@RequestMapping("/i18n/test/")
public class I18nTestController {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/test")
    public ResponseEntity test1() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("当前语言", LocaleContextHolder.getLocale());
        data.put("username", I18nMessages.getMessage("username.label"));
        data.put("枚举UserTypes.personal", I18nMessages.getMessage(UserTypes.personal));
        data.put("枚举UserType.company", I18nMessages.getMessage(UserTypes.company));
        return ResponseEntity.ok(data);
    }

    @RequestMapping("/support")
    public ResponseEntity locales() {
        Set<Locale> locales = I18nMessages.supports();
        locales.add(new Locale("zh", "TW"));
        locales.add(new Locale("zh", "HK"));
        locales.add(new Locale("en", "AU"));
        List<String> list = new ArrayList<>();
        for (Locale l : locales) {
            list.add(l.getDisplayLanguage(l) + "-" + l.getDisplayCountry(l));
        }
        log.info("当前环境支持的国际化语言：{}", list);
        return ResponseEntity.ok(list);
    }

    @RequestMapping("/supportLanguages")
    public ResponseEntity supportLanguages() {
        Map<String, String> languages = I18nMessages.supportLanguages();
        return ResponseEntity.ok(languages);
    }

}

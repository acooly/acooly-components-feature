/**
 * openapi-framework
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2024-01-14 22:32
 */
package com.acooly.module.test.i18n;

import com.acooly.component.i18n.support.I18nMessages;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangpu
 * @date 2024-01-14 22:32
 */
@Slf4j
public class I18nTester {

    @Test
    public void testSupports() {
        Set<Locale> locales = I18nMessages.supports();
        locales.add(new Locale("zh", "TW"));
        locales.add(new Locale("zh", "HK"));
        locales.add(new Locale("en", "AU"));
        locales.add(Locale.UK);
        locales.add(Locale.JAPAN);
        locales.add(Locale.FRANCE);
        locales.add(Locale.KOREA);
        locales.add(Locale.GERMANY);
        log.info("当前环境支持的国际化语言：");
        for (Locale l : locales) {
            log.info("{}-{}", l.getDisplayLanguage(l), l.getDisplayCountry(l));
        }
    }

    /**
     * 测试获取本环境支持的国际化语言
     */
    @Test
    public void testSupportLanguages() {
        Map<String, String> locales = I18nMessages.supportLanguages();
        Set<String> localeKeys = locales.keySet();
        Set<String> localeDisplays = Sets.newLinkedHashSet(locales.values());
        log.info("locales data: {}", locales);
        log.info("locale keys, 用于传递给服务器端:  {}", localeKeys);
        log.info("locale displays, 用于给前端用户显示和选择 {}", localeDisplays);
    }

}

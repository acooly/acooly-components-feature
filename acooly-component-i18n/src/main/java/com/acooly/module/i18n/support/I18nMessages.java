/**
 * acooly-ms
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-10-06 17:37
 */
package com.acooly.module.i18n.support;

import com.acooly.core.utils.Strings;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Locale;
import java.util.Set;

/**
 * @author zhangpu
 * @date 2021-10-06 17:37
 */
@Slf4j
@Component
public class I18nMessages {
    private static MessageSource messageSource;

    public I18nMessages(MessageSource messageSource) {
        I18nMessages.messageSource = messageSource;
    }

    /**
     * 获取单个国际化翻译值
     */
    public static String get(String msgKey) {
        try {
            return messageSource.getMessage(msgKey, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return msgKey;
        }
    }

    public static Set<Locale> supports() {
        ResourceBundleMessageSource resourceBundleMessageSource = (ResourceBundleMessageSource) messageSource;
        Set<String> basenames = resourceBundleMessageSource.getBasenameSet();
        Set<Locale> locales = Sets.newLinkedHashSet();
        for (String basename : basenames) {
            Set<Resource> resources = getResources(basename);
            for (Resource resource : resources) {
                try {
                    URL url = resource.getURL();
                    String path = url.getPath();
                    String localeInfo = Strings.substringBetween(path, basename + "_", ".properties");
                    if (Strings.isNotBlank(localeInfo) && Strings.contains(localeInfo, "_")) {
                        String[] localeArray = localeInfo.split("_");
                        locales.add(new Locale(localeArray[0], localeArray[1]));
                    }
                } catch (Exception e) {
                    // ig
                }

            }
        }
        return locales;
    }

    private static Set<Resource> getResources(String basename) {
        Set resourceSet = Sets.newLinkedHashSet();
        try {
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resourcePatternResolver.getResources("classpath:" + basename + "*.properties");
            if (resources != null && resources.length > 0) {
                for (Resource resource : resources) {
                    resourceSet.add(resource);
                }
            }
        } catch (Exception e) {
            // ig
            log.warn("国际化 加载指定basename({})下的资源文件失败。原因: {}", basename, e.getMessage());
        }
        return resourceSet;
    }
}

/**
 * acooly-ms
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-10-06 17:37
 */
package com.acooly.component.i18n.support;

import com.acooly.core.utils.Strings;
import com.acooly.core.utils.enums.Messageable;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * SpringBoot环境的国际化静态工具类
 *
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
     * 该静态方法必须在Spring容器初始化完成后才能调用
     *
     * @param code
     * @param args
     * @param defaultMessage
     * @return
     */
    public static String getMessage(String code, Object[] args, String defaultMessage) {
        return messageSource.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
    }

    /**
     * 获取单个国际化翻译值
     * 该静态方法必须在Spring容器初始化完成后才能调用
     *
     * @param code
     * @param defaultMessage
     * @return
     */
    public static String getMessage(String code, String defaultMessage) {
        return messageSource.getMessage(code, null, defaultMessage, LocaleContextHolder.getLocale());
    }

    /**
     * 获取单个国际化翻译值
     * 该静态方法必须在Spring容器初始化完成后才能调用
     *
     * @param code
     * @return
     */
    public static String getMessage(String code) {
        return messageSource.getMessage(code, null, null, LocaleContextHolder.getLocale());
    }

    /**
     * 获取框架枚举接口的国际化翻译值
     * <p>
     * 注意：如果采用传入`Messageable`接口的方式，需要在messages*.properties中定义对应的
     * key的格式为：{Messageable的实现类的类全名}.{枚举的name}
     * 例如：com.acooly.module.test.i18n.UserTypes.personal=个人
     *
     * @param messageable
     * @return
     */
    public static String getMessage(Messageable messageable) {
        String key = messageable.getClass().getName() + "." + messageable.code();
        return messageSource.getMessage(key, null, null, LocaleContextHolder.getLocale());
    }

    /**
     * 获取单个国际化翻译值
     * 该静态方法必须在Spring容器初始化完成后才能调用
     */
    public static String get(String code) {
        try {
            return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return code;
        }
    }


    public static Map<String, String> supportLanguages() {
        return supportLanguages("-");
    }

    /**
     * 获取支持的国际化语言列表（根据目标语言显示）
     * <p>
     * 应用案例：
     *
     * <pre>
     *  Map<String, String> locales = I18nMessages.supportLanguages();
     *  Set<String> localeKeys = locales.keySet();
     *  Set<String> localeDisplays = Sets.newLinkedHashSet(locales.values());
     *  log.info("locales data: {}", locales);
     *  log.info("locale keys, 用于传递给服务器端:  {}", localeKeys);
     *  log.info("locale displays, 用于给前端用户显示和选择 {}", localeDisplays);
     * </pre>
     *
     * @param split
     * @return key:语言编码（例如：zh_CN, en_US）,value:语言显示名称（例如：中文-中国，English-United States）
     */
    public static Map<String, String> supportLanguages(String split) {
        split = Strings.defaultString(split, "-");
        Set<Locale> locales = supports();
        Map<String, String> data = new LinkedHashMap<>();
        for (Locale l : locales) {
            data.put(l.toString(), l.getDisplayLanguage(l) + split + l.getDisplayCountry(l));
        }
        return data;
    }

    /**
     * 获取当前环境支持的国际化语言
     * <p>
     * 根据classpath*:messages_*.properties文件名匹配语言信息
     *
     * @return
     */
    public static Set<Locale> supports() {
        Set<String> baseNames = Sets.newLinkedHashSet();
        ResourceBundleMessageSource resourceBundleMessageSource = (ResourceBundleMessageSource) messageSource;
        if (resourceBundleMessageSource != null) {
            baseNames.addAll(resourceBundleMessageSource.getBasenameSet());
        } else {
            baseNames.add("messages");
        }
        Set<Locale> locales = Sets.newLinkedHashSet();
        Locale locale = null;
        for (String baseName : baseNames) {
            Set<Resource> resources = getResources(baseName);
            for (Resource resource : resources) {
                try {
                    String localeInfo = Strings.substringBetween(resource.getFilename(), baseName + "_", ".properties");
                    if (Strings.isNotBlank(localeInfo)) {
                        locale = StringUtils.parseLocale(localeInfo);
                        if (locale != null) {
                            locales.add(locale);
                        }
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
            Resource[] resources = resourcePatternResolver.getResources("classpath*:" + basename + "*.properties");
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

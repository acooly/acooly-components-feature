/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2024-01-18 15:51
 */
package com.acooly.component.i18n.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * 国际Locale切换服务
 * <p>
 * 1. 支持LocaleContextHolder的更新（静态获取线程变量的值）
 * 2. 支持LocaleResolver的更新（当前国际化环境内）
 *
 * @author zhangpu
 * @date 2024-01-18 15:51
 */
@Component
public class LocaleChangeService {

    @Autowired
    private LocaleResolver localeResolver;

    /**
     * 修改当前环境的Locale
     *
     * @param request
     * @param response
     * @param locale
     */
    public void changeLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        LocaleContext localeContext = null;
        LocaleResolver lr = this.localeResolver;
        if (lr instanceof LocaleContextResolver) {
            if (locale != null) {
                localeResolver.setLocale(request, response, locale);
            }
            localeContext = ((LocaleContextResolver) lr).resolveLocaleContext(request);
        } else {
            localeContext = () -> (lr != null ? lr.resolveLocale(request) : request.getLocale());
        }
        LocaleContextHolder.setLocaleContext(localeContext, false);
    }

}

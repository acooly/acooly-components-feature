/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2024-01-19 10:24
 */
package com.acooly.component.i18n;

import com.acooly.core.common.boot.EnvironmentHolder;
import com.acooly.core.common.boot.component.ComponentInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 国际化组件初始化
 *
 * @author zhangpu
 * @date 2024-01-19 10:24
 */
@Slf4j
public class I18nComponentInitializer implements ComponentInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // 如果开启了国际化，则设置系统属性，用于其他基础设施（Messageable.message(), BusinessException等）根据此状态`高效`判断是否走国际化逻辑
        I18nProperties i18nProperties = new I18nProperties();
        EnvironmentHolder.buildProperties(i18nProperties);
        if (i18nProperties.isEnable()) {
            System.setProperty("acooly.i18n.enable", "true");
        }

    }

}

package com.acooly.module.sso;

import com.acooly.core.common.boot.EnvironmentHolder;
import com.acooly.core.common.boot.component.ComponentInitializer;
import com.acooly.core.utils.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author shuijing
 */
@Slf4j
public class SSOComponentInitializer implements ComponentInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        String property = EnvironmentHolder.get().getProperty("acooly.sso.enable");
        if (property == null) {
            System.setProperty("acooly.security.shiro.auth.enable", "false");
            System.setProperty("acooly.sso.freemarker.include", "true");
        } else {
            Boolean enable = Boolean.valueOf(property);
            if (enable) {
                System.setProperty("acooly.security.shiro.auth.enable", "false");
                System.setProperty("acooly.sso.freemarker.include", "true");
            } else {
                System.setProperty("acooly.security.shiro.auth.enable", "true");
                System.setProperty("acooly.sso.freemarker.include", "false");
            }
        }

        // 设置ssoServerUrl到System Properties,用于security模块的tag解耦方式获得该参数
        String ssoServerUrl = EnvironmentHolder.get().getProperty("acooly.sso.ssoServerUrl");
        if (Strings.isBlank(ssoServerUrl)) {
            ssoServerUrl = EnvironmentHolder.get().getProperty("acooly.sso.sso-server-url");
        }
        if (Strings.isNotBlank(ssoServerUrl)) {
            System.setProperty("acooly.sso.ssoServerUrl", ssoServerUrl);
        }

    }
}

package com.acooly.module.security.service.impl;

import com.acooly.module.security.config.SecurityProperties;
import com.acooly.module.security.domain.User;
import com.acooly.module.security.service.SSOAuthzService;
import com.acooly.module.security.service.UserService;
import com.acooly.module.security.shiro.cache.ShiroCacheManager;
import com.acooly.module.security.shiro.realm.PathMatchPermission;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import static com.acooly.module.security.config.SecurityComponentInitializer.DUBBO_SSO_CONFIG_PACKAGE;

/**
 * @author shuijing
 */
@Service(version = "1.0", group = DUBBO_SSO_CONFIG_PACKAGE)
@org.springframework.stereotype.Service
@ConditionalOnProperty(
        value = SecurityProperties.PREFIX + ".shiro.auth.enable",
        matchIfMissing = true
)
public class SSOAuthzServiceImpl implements SSOAuthzService {

    @Autowired
    private UserService userService;
    @Autowired
    private WebSecurityManager shiroSecurityManager;

    @Override
    public boolean permitted(String uri, String username) throws Exception {
        boolean result = false;
        if (!StringUtils.isEmpty(username)) {
            User user = userService.getAndCheckUser(username);
            if (user != null) {
                ThreadContext.bind(shiroSecurityManager);
                SimplePrincipalCollection simplePrincipal =
                        new SimplePrincipalCollection(user, ShiroCacheManager.KEY_AUTHC);
                Subject subject =
                        new Subject.Builder().principals(simplePrincipal).authenticated(true).buildSubject();
                ThreadContext.bind(subject);
                String permission = "do" + PathMatchPermission.PART_DIVIDER_TOKEN + uri;
                result = subject.isPermitted(permission);
            }
        }
        return result;
    }
}

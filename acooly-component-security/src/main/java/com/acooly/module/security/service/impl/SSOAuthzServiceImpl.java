package com.acooly.module.security.service.impl;

import com.acooly.core.utils.Strings;
import com.acooly.module.security.config.SecurityProperties;
import com.acooly.module.security.domain.User;
import com.acooly.module.security.service.SSOAuthzService;
import com.acooly.module.security.service.UserService;
import com.acooly.module.security.shiro.cache.ShiroCacheManager;
import com.acooly.module.security.shiro.realm.PathMatchPermission;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import static com.acooly.module.security.config.SecurityComponentInitializer.DUBBO_SSO_CONFIG_PACKAGE;

/**
 * SSO shiro远程认证服务 实现
 *
 * @author shuijing
 * @author zhangpu for 重构支持：url和按钮权限；支持角色验证。
 */
@Slf4j
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
    public boolean permitted(String permission, String username) {
        boolean result = false;
        if (Strings.isBlank(username)) {
            log.warn("SSO远程权限认证 失败 用户名为空");
            return false;
        }
        Subject subject = buildUserSubject(username);
        if (subject == null) {
            return false;
        }
        String p = permission;
        if (Strings.startsWith(permission, "/")) {
            p = "do" + PathMatchPermission.PART_DIVIDER_TOKEN + permission;
        }
        result = subject.isPermitted(p);
        return result;
    }

    @Override
    public boolean hasAnyRoles(String roleNames, String username) {
        boolean result = false;
        if (Strings.isBlank(username)) {
            log.warn("SSO远程角色认证 失败 用户名为空");
            return false;
        }
        Subject subject = buildUserSubject(username);
        if (subject == null) {
            return false;
        }

        for (String roleName : Strings.split(roleNames, PathMatchPermission.PART_DIVIDER_TOKEN)) {
            if (subject.hasRole(roleName)) {
                result = true;
                break;
            }
        }
        return result;
    }

    protected Subject buildUserSubject(String username) {
        User user = userService.getAndCheckUser(username);
        if (user != null) {
            User sessionUser = new User();
            sessionUser.setUsername(user.getUsername());
            sessionUser.setRealName(user.getRealName());
            sessionUser.setPinyin(user.getPinyin());
            sessionUser.setUserType(user.getUserType());
            sessionUser.setEmail(user.getEmail());
            sessionUser.setMobileNo(user.getMobileNo());
            sessionUser.setOrgId(user.getOrgId());
            sessionUser.setOrgName(user.getOrgName());
            sessionUser.setStatus(user.getStatus());
            sessionUser.setId(user.getId());
            sessionUser.setCreateTime(user.getCreateTime());
            sessionUser.setUpdateTime(user.getUpdateTime());
            ThreadContext.bind(shiroSecurityManager);
            SimplePrincipalCollection simplePrincipal = new SimplePrincipalCollection(sessionUser, ShiroCacheManager.KEY_AUTHC);
            Subject subject = new Subject.Builder().principals(simplePrincipal).authenticated(true).buildSubject();
            ThreadContext.bind(subject);
            return subject;
        }
        return null;
    }

}

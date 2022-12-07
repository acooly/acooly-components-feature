package com.acooly.module.security.shiro.realm;

import com.acooly.core.common.boot.ApplicationContextHolder;
import com.acooly.core.utils.Encodes;
import com.acooly.core.utils.mapper.BeanCopier;
import com.acooly.module.security.config.FrameworkProperties;
import com.acooly.module.security.config.SecurityProperties;
import com.acooly.module.security.domain.Resource;
import com.acooly.module.security.domain.Role;
import com.acooly.module.security.domain.User;
import com.acooly.module.security.service.UserService;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class ShiroDbRealm extends AuthorizingRealm {

    public static final String SESSION_USER = "user";
    public static final String IS_FUNCTION_PREFIX = "IS_FUNCTION_PREFIX";
    private static final Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);
    @Autowired
    protected UserService userService;
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(UserService.HASH_ALGORITHM);
        matcher.setHashIterations(UserService.HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 认证回调函数,登录时调用.
     */
    @Override
    @Transactional
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        UsernamePasswordToken captchaToken = (UsernamePasswordToken) token;
        User user = getUserService().getAndCheckUser(captchaToken.getUsername());
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
            // 设置用户
//            SecurityUtils.getSubject().getSession().setAttribute(SESSION_USER, user);
            if (Strings.isNullOrEmpty(user.getSalt())) {
                return new SimpleAuthenticationInfo(sessionUser, user.getPassword(), null, getName());
            } else {
                byte[] salt = Encodes.decodeHex(user.getSalt());
                return new SimpleAuthenticationInfo(
                        sessionUser, user.getPassword(), ByteSource.Util.bytes(salt), getName());
            }
        } else {
            return null;
        }
    }

    public UserService getUserService() {
        if (userService == null) {
            userService = ApplicationContextHolder.get().getBean(UserService.class);
        }
        return userService;
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    @Transactional
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User sessionUser = (User) principals.getPrimaryPrincipal();
        User user;
        try {
            user = getUserService().findUserByUsername(sessionUser.getUsername());
        } catch (Exception e) {
            logger.info("根据登录名[" + sessionUser.getUsername() + "]获取用户失败:" + e.getMessage());
            return null;
        }

        String contextPath = "";
        try {
            WebDelegatingSubject wdSubject = (WebDelegatingSubject) SecurityUtils.getSubject();
            contextPath = ((HttpServletRequest) wdSubject.getServletRequest()).getContextPath();
        } catch (Exception e) {
            // ig
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for (Role role : user.getRoles()) {
            // 基于Role的权限信息
            info.addRole(role.getName());
            // 基于Permission的权限信息
            Set<Resource> resources = role.getRescs();
            Set<String> urls = new HashSet<String>();
            for (Resource resource : resources) {
                if (!resource.getType().equalsIgnoreCase(FrameworkProperties.RESOURCE_TYPE_MENU)
                        && Strings.isNullOrEmpty(resource.getValue())) {
                    continue;
                }
                if (resource.getType().equalsIgnoreCase(FrameworkProperties.RESOURCE_TYPE_URL)) {
                    urls.add("*" + PathMatchPermission.PART_DIVIDER_TOKEN
                            + getCanonicalResource(contextPath, resource.getValue()));
                } else if (resource.getType().equalsIgnoreCase(FrameworkProperties.RESOURCE_TYPE_FUNCTION)) {
                    urls.add(IS_FUNCTION_PREFIX + resource.getValue());
                }
            }
            info.addStringPermissions(urls);
        }
        return info;
    }

    private String getCanonicalResource(String contextPath, String resource) {
        if (resource.contains("://")) {
            try {
                URL url = new URL(resource);
                resource = url.getPath();
            } catch (MalformedURLException e) {
                logger.error("获取权限url异常", e);
            }
        }
        return contextPath + StringUtils.left(resource, resource.lastIndexOf("/") + 1) + "*";
    }

    @Override
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info)
            throws AuthenticationException {
        if (securityProperties.getShiro().isEnableBlankSaltUseMd5Matcher()) {
            if (info instanceof SimpleAuthenticationInfo) {
                ByteSource credentialsSalt = ((SimpleAuthenticationInfo) info).getCredentialsSalt();
                if (credentialsSalt == null) {
                    Object credentials = info.getCredentials();
                    String hashed =
                            DigestUtils.md5DigestAsHex(
                                    new String((char[]) token.getCredentials()).getBytes(Charsets.UTF_8));
                    if (credentials.equals(hashed)) {
                        return;
                    } else {
                        String msg =
                                "Submitted credentials for token ["
                                        + token
                                        + "] did not match the expected credentials.";
                        throw new IncorrectCredentialsException(msg);
                    }
                }
            }
        }
        super.assertCredentialsMatch(token, info);
    }
}

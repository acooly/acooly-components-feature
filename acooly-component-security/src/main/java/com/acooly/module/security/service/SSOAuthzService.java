package com.acooly.module.security.service;

/**
 * Shiro的SSO安全服务
 * <p>
 * 提供以传入用户名构建shiro的subject对象，进行安全相关的认证和授权服务
 *
 * @author shuijing
 * @author zhangpu
 */
public interface SSOAuthzService {

    /**
     * 判断是否有权限
     *
     * @param permission
     * @param username
     * @return
     * @throws Exception
     */
    boolean permitted(String permission, String username);


    /**
     * 判断用户是否有对应的角色身份
     *
     * @param roleNames 多个逗号分隔
     * @param username
     * @return
     */
    boolean hasAnyRoles(String roleNames, String username);
}

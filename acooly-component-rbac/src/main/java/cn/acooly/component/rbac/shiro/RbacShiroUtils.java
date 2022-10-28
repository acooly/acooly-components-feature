/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-10-11 18:08
 */
package cn.acooly.component.rbac.shiro;

import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

/**
 * @author zhangpu
 * @date 2022-10-11 18:08
 */
@Slf4j
public class RbacShiroUtils {

    private static final String URL_CHECK_PREFIX = "https://acooly.cn";

    /**
     * 不认证直接判断用户对资源的权限
     * (未登录认证，直接判断权限，没有subject.login)
     *
     * <li>存在资源，不存在角色：false</li>
     * <li>不存在资源：true</li>
     * <li>存在资源，存在角色：true</li>
     *
     * @param memberNo
     * @param resource
     * @return
     */
    public static boolean isPermittedUnAuthe(Object principal, String resource) {
        return RbacSecurityUtils.getSecurityManager().isPermitted(getPrincipals(principal), normalizeResource(resource));
    }

    /**
     * 不认证直接判断用户是否有对应的角色
     * (未登录认证，直接判断权限，没有subject.login)
     *
     * @param memberNo
     * @param roleName
     * @return
     */
    public static boolean hasRoleUnAuthe(Object principal, String roleName) {
        return RbacSecurityUtils.getSecurityManager().hasRole(getPrincipals(principal), roleName);
    }


    /**
     * 清除当前用户所有的缓存（新增用户，删除用户和修改用户角色）
     */
    public static void clearCache(Object principal) {
        clearAutheCache(principal);
        clearAuthoCache(principal);
    }

    /**
     * 情况全局缓存（管理配置修改了Resource和Role的权限定义）
     *
     * @param principal
     */
    public static void clearAllCache(Object principal) {
        clearAllAutheCache();
        clearAllAuthoCache();
    }

    /**
     * 清除当前用户认证缓存
     */
    public static void clearAutheCache(Object principal) {
        getRealm().clearCachedAuthenticationInfo(getPrincipals(principal));
    }

    public static void clearAllAutheCache() {
        getRealm().getAuthenticationCache().clear();
    }

    /**
     * 清除当前用户授权缓存
     */
    public static void clearAuthoCache(Object principal) {
        getRealm().clearCachedAuthorizationInfo(getPrincipals(principal));
    }

    public static void clearAllAuthoCache() {
        getRealm().getAuthorizationCache().clear();
    }

    protected static RbacShiroRealm getRealm() {
        DefaultSecurityManager defaultSecurityManager = (DefaultSecurityManager) RbacSecurityUtils.getSecurityManager();
        RbacShiroRealm realm = (RbacShiroRealm) Collections3.getFirst(defaultSecurityManager.getRealms());
        return realm;
    }

    protected static PrincipalCollection getPrincipals(Object principal) {
        PrincipalCollection principalCollection = new SimplePrincipalCollection(principal, getRealm().getName());
        return principalCollection;
    }

    /**
     * 获取当前线程绑定的Subject并直接认证
     *
     * @param memberNo
     * @return
     */
    protected static Subject getSubject(String memberNo) {
        UsernamePasswordToken token = new UsernamePasswordToken(memberNo, "");
        Subject subject = RbacSecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            throw new RbacAuthException(RbacErrors.USER_NOT_EXISTS, e.getMessage());
        } catch (Exception e) {
            throw new RbacAuthException(RbacErrors.AUTHENTICATION_ERROR, e.getMessage());
        }
        return subject;
    }

    /**
     * 规范化资源
     * 如果是URL，则规范化为："*:url"
     *
     * @param resource
     * @return
     */
    private static String normalizeResource(String resource) {
        if (Strings.isHttpUrl(resource) || Strings.startsWith(resource, "/")) {
            return "*:" + resource;
        }
        return resource;
    }


}

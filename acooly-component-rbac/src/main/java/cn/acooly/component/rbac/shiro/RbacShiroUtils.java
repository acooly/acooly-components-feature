/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-10-11 18:08
 */
package cn.acooly.component.rbac.shiro;

import cn.acooly.component.rbac.entity.RbacUser;
import com.acooly.core.utils.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 * @author zhangpu
 * @date 2022-10-11 18:08
 */
@Slf4j
public class RbacShiroUtils {

    private static final String URL_CHECK_PREFIX = "https://acooly.cn";

    /**
     * 判断用户对资源的权限
     *
     * @param memberNo
     * @param resource
     * @return
     */
    public static boolean isPermitted(String memberNo, String resource) {
        return getSubject(memberNo).isPermitted(normalizeResource(resource));
    }

    /**
     * 判断用户是否有对应的角色
     *
     * @param memberNo
     * @param roleName
     * @return
     */
    public static boolean hasRole(String memberNo, String roleName) {
        return getSubject(memberNo).hasRole(roleName);
    }


    /**
     * 获取会话User对象
     * 线程绑定的Scope
     *
     * @return
     */
    public static RbacUser getSessionUser() {
        return (RbacUser) RbacSecurityUtils.getSubject().getPrincipal();
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

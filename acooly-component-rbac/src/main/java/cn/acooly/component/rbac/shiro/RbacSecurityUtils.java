/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-10-11 17:25
 */
package cn.acooly.component.rbac.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;

/**
 * Shiro for RBAC 静态工具类
 *
 * @author zhangpu
 * @date 2022-10-11 17:25
 */
@Slf4j
public final class RbacSecurityUtils {

    private static volatile SecurityManager securityManager;

    public static SecurityManager getSecurityManager() throws UnavailableSecurityManagerException {
        SecurityManager securityManager = ThreadContext.getSecurityManager();
        if (securityManager == null) {
            securityManager = RbacSecurityUtils.securityManager;
        }
        if (securityManager == null) {
            String msg = "No SecurityManager accessible to the calling code, either bound to the " +
                    RbacSecurityUtils.class.getName() + " or as a vm static singleton.  This is an invalid application " +
                    "configuration.";
            throw new UnavailableSecurityManagerException(msg);
        }
        return securityManager;
    }

    public static void setSecurityManager(SecurityManager securityManager) {
        RbacSecurityUtils.securityManager = securityManager;
    }

    public static Subject getSubject() {
        Subject subject = RbacThreadContext.getRbacSubject();
        if (subject == null) {
            subject = (new Subject.Builder(getSecurityManager())).buildSubject();
            RbacThreadContext.rbacBind(subject);
        }
        return subject;
    }

}

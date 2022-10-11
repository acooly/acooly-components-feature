/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-10-11 17:32
 */
package cn.acooly.component.rbac.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;

/**
 * @author zhangpu
 * @date 2022-10-11 17:32
 */
@Slf4j
public class RbacThreadContext extends ThreadContext {

    public static final String RBAC_SECURITY_MANAGER_KEY = RbacThreadContext.class.getName() + "_SECURITY_MANAGER_KEY";
    public static final String RBAC_SUBJECT_KEY = RbacThreadContext.class.getName() + "_SUBJECT_KEY";

    public static Subject getRbacSubject() {
        return (Subject) get(RBAC_SUBJECT_KEY);
    }

    public static void rbacBind(Subject subject) {
        if (subject != null) {
            put(RBAC_SUBJECT_KEY, subject);
        }
    }

    public static SecurityManager getRbacSecurityManager() {
        return (SecurityManager) get(RBAC_SECURITY_MANAGER_KEY);
    }

    public static Subject getSubject(String subjectKey) {
        return (Subject) get(subjectKey);
    }

    public static SecurityManager getSecurityManager(String securityManagerKey) {
        return (SecurityManager) get(securityManagerKey);
    }

}

package com.acooly.module.security.shiro.freemarker;

import com.acooly.module.security.shiro.freemarker.tags.*;
import freemarker.template.SimpleHash;

/**
 * Freemarker tag for shiro, support sso
 *
 * <p>Usage: cfg.setSharedVeriable("shiro", new ShiroFreemarkerTags());</p>
 *
 * @author zhangpu
 */
public class ShiroFreemarkerTags extends SimpleHash {
    public ShiroFreemarkerTags() {
        put("authenticated", new AuthenticatedTag());
        put("guest", new GuestTag());
        put("hasPermission", new HasPermissionTag());
        put("lacksPermission", new LacksPermissionTag());
        put("hasAnyRoles", new HasRoleTag());
        put("hasRole", new HasRoleTag());
        put("lacksRole", new LacksRoleTag());
        put("notAuthenticated", new NotAuthenticatedTag());
        put("principal", new PrincipalTag());
        // 兼容原有在acooly-component-web中声明的标签名称
        put("shiroPrincipal", new PrincipalTag());
    }
}
package com.acooly.module.security.shiro.realm;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;

public class PathMatchPermissionResolver implements PermissionResolver {

    @Override
    public Permission resolvePermission(String permissionString) {
        if (permissionString.startsWith(ShiroDbRealm.IS_FUNCTION_PREFIX)) {
            return new PathMatchPermission(
                    permissionString.substring(ShiroDbRealm.IS_FUNCTION_PREFIX.length()), true);
        }
        return new PathMatchPermission(permissionString, false);
    }
}

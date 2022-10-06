/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-10-06 00:31
 */
package cn.acooly.component.rbac.shiro;

import cn.acooly.component.rbac.entity.RbacUser;
import cn.acooly.component.rbac.entity.RbacResource;
import cn.acooly.component.rbac.entity.RbacRole;
import cn.acooly.component.rbac.enums.ResourceType;
import cn.acooly.component.rbac.service.RbacRoleService;
import cn.acooly.component.rbac.service.RbacUserService;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhangpu
 * @date 2022-10-06 00:31
 */
@Slf4j
public class RbacShiroRealm extends AuthorizingRealm {

    @Autowired
    protected RbacUserService rbacUserService;

    @Autowired
    protected RbacRoleService rbacRoleService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        RbacUser rbacUser = rbacUserService.findUserByUsername(username);
        if (rbacUser == null) {
            return null;
        }
        // 这里可以查询数据库，是否有该用户注册,找不到就返回null
        // 这里是永远认证通过
        return new SimpleAuthenticationInfo(rbacUser, token.getCredentials(), null, getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        RbacUser rbacUser = (RbacUser) principals.getPrimaryPrincipal();
        List<RbacRole> rbacRoles = rbacRoleService.getRolesCascadeResources(rbacUser.getId());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for (RbacRole rbacRole : rbacRoles) {
            // 基于Role的权限信息
            info.addRole(rbacRole.getName());
            Set<String> urls = new HashSet<String>();
            for (RbacResource rbacResource : rbacRole.getRbacResources()) {
                if (rbacResource.getType() != ResourceType.MENU && Strings.isNullOrEmpty(rbacResource.getValue())) {
                    continue;
                }
                if (rbacResource.getType() == ResourceType.URL) {
                    urls.add("*:" + rbacResource.getValue());
                } else if (rbacResource.getType() == ResourceType.FUNC) {
                    urls.add(rbacResource.getValue());
                }
            }
            info.addStringPermissions(urls);
        }
        return info;
    }

}

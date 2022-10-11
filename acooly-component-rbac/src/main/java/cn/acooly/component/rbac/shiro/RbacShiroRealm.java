/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-10-06 00:31
 */
package cn.acooly.component.rbac.shiro;

import cn.acooly.component.rbac.RbacProperties;
import cn.acooly.component.rbac.entity.RbacResource;
import cn.acooly.component.rbac.entity.RbacRole;
import cn.acooly.component.rbac.entity.RbacUser;
import cn.acooly.component.rbac.enums.ResourceType;
import cn.acooly.component.rbac.service.RbacResourceService;
import cn.acooly.component.rbac.service.RbacRoleService;
import cn.acooly.component.rbac.service.RbacUserService;
import com.acooly.core.utils.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
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

    private static final String URL_RESOURCE_PREFIX = "*:";

    @Autowired
    protected RbacUserService rbacUserService;
    @Autowired
    protected RbacRoleService rbacRoleService;
    @Autowired
    protected RbacResourceService rbacResourceService;
    @Autowired
    private RbacProperties rbacProperties;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String memberNo = (String) token.getPrincipal();
        RbacUser rbacUser = rbacUserService.findUserByMemberNo(memberNo);
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
                if (rbacResource.getType() == ResourceType.MENU || Strings.isBlank(rbacResource.getValue())) {
                    continue;
                }
                if (rbacResource.getType() == ResourceType.URL) {
                    urls.add(URL_RESOURCE_PREFIX + rbacResource.getValue());
                } else if (rbacResource.getType() == ResourceType.FUNC) {
                    urls.add(rbacResource.getValue());
                }
            }
            info.addStringPermissions(urls);
        }
        return info;
    }


    /**
     * 权限认证实现
     * <p>
     * 扩展：根据配置开关，针对未被受控的资源是否放行（true）。
     *
     * @param permission
     * @param info
     * @return
     */
    @Override
    protected boolean isPermitted(Permission permission, AuthorizationInfo info) {

        boolean permitted = super.isPermitted(permission, info);
        // 授权判断通过，则直接返回
        if (permitted) {
            return true;
        }

        // 未通过的情况，判断是否全局不存在该资源的权限
        if (rbacProperties.isNoResourcePermitted()) {
            String resourceStr = permission.toString();
            if (Strings.startsWith(resourceStr, URL_RESOURCE_PREFIX)) {
                resourceStr = Strings.removeStart(resourceStr, URL_RESOURCE_PREFIX);
            }
            final String permissionValue = resourceStr;
            List<RbacResource> resources = rbacResourceService.getAll();
            boolean exists = resources.stream().anyMatch(e -> Strings.equals(e.getValue(), permissionValue));
            // 不存在该资源
            if (!exists) {
                return true;
            }
        }
        return false;
    }
}

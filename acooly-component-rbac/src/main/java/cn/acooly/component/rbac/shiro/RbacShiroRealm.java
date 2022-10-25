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
        // 不认证直接授权判断模式，该部分实现代码无效，永不执行
        String memberNo = (String) token.getPrincipal();
        return new SimpleAuthenticationInfo(memberNo, token.getCredentials(), null, memberNo);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String memberNo = (String) principals.getPrimaryPrincipal();
        RbacUser rbacUser = rbacUserService.findUserByMemberNo(memberNo);
        if (rbacUser == null) {
            log.warn("RBAC Principal: {} 用户不存在，直接返回没权限", memberNo);
            // 这里返回null，则会判断为没有权限
            return null;
//            throw new RbacAuthException(RbacErrors.USER_NOT_EXISTS, memberNo);
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<RbacRole> rbacRoles = rbacRoleService.getRolesCascadeResources(rbacUser.getId());
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

        return super.isPermitted(permission, info);
    }


    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }
}

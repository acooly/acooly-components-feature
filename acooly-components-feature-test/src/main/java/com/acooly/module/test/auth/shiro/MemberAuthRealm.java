/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-10-04 23:04
 */
package com.acooly.module.test.auth.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhangpu
 * @date 2022-10-04 23:04
 */
@Slf4j
public class MemberAuthRealm extends AuthorizingRealm {


    public MemberAuthRealm() {
        setAuthenticationTokenClass(MemberAuthToken.class);
    }

    /**
     * 认证
     *
     * @param token the authentication token containing the user's principal and credentials.
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        MemberAuthToken memberToken = (MemberAuthToken) token;
        // 这里可以查询数据库，是否有该用户注册,找不到就返回null
        // 这里是永远认证通过
        return new SimpleAuthenticationInfo(memberToken.getPrincipal(), memberToken.getCredentials(), null, getName());
    }

    /**
     * 授权
     *
     * @param principals the primary identifying principals of the AuthorizationInfo that should be retrieved.
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 认证对象
        MemberInfo memberInfo = (MemberInfo) principals.getPrimaryPrincipal();
        String memberNo = memberInfo.getMemberNo();
        // 从数据库查询出会员对应的角色，然后获取角色对应的权限，加入到权限信息列表中
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        info.addRole("SYSTEM_ADMIN");
        // 使用Set剔除多个角色中重复的资源权限配置
        Set<String> permissions = new HashSet<String>();
        // 循环添加URL权限
        permissions.add("*:/portal/seller/order/index1.html");
        permissions.add("*:/portal/seller/order/index2.html");
        permissions.add("*:/portal/seller/order/index3.html");
        // 循环添加功能权限（按钮,数据等）
        permissions.add("user:create");
        permissions.add("user:delete");
        permissions.add("member:*");
        info.addStringPermissions(permissions);

        return info;
    }

}

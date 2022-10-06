/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-10-06 01:12
 */
package com.acooly.module.test.auth;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangpu
 * @date 2022-10-06 01:12
 */
@Slf4j
@RestController
@RequestMapping("/rbac/")
public class RbacShiroController {


    @Autowired
    private DefaultSecurityManager rbacShiroSecurityManager;

    @RequestMapping("auth")
    public Object testAuth() {

        UsernamePasswordToken token = new UsernamePasswordToken("admin", "");
        Subject subject = rbacShiroSecurityManager.createSubject(new DefaultSubjectContext());
//        Subject subject = SecurityUtils.getSubject();
        try {
           subject.login(token);
        } catch (UnknownAccountException e) {
            System.out.println("用户名错误!!");
        } catch (Exception e) {
            System.out.println("其他错误!!");
        }

        // 判断请求是否有权限
        if (subject.isAuthenticated()) {
            //基于角色权限管理
            boolean admin = subject.hasRole("ROLE_ADMIN");
            log.info("角色认证(ROLE_ADMIN): {}", admin);
            boolean roleMember = subject.hasRole("ROLE_MEMBER");
            log.info("角色认证(ROLE_MEMBER): {}", roleMember);
            // 基于URL权限的判断
            boolean urlPermitted = subject.isPermitted("*:/manage/rbac/role/index.html");
            log.info("URL权限认证(/portal/seller/order/index1.html): {}", urlPermitted);
            urlPermitted = subject.isPermitted("*:/portal/seller/order/index8.html");
            log.info("URL权限认证(/portal/seller/order/index8.html): {}", urlPermitted);
            // 基于URL权限的判断
            boolean funcPermitted = subject.isPermitted("resource:create");
            log.info("功能权限认证(resource:create): {}", funcPermitted);
        }
        return null;
    }

}

/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-10-04 23:03
 */
package com.acooly.module.test.auth.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author zhangpu
 * @date 2022-10-04 23:03
 */
@Slf4j
public class MemberNoWebAuthTest {

    @Test
    public void testAuth() {

        // 1、系统启动的时候初始化SecurityManager，设置好认证和授权的环境
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        // 从数据库读取认证和权限信息(RBCA)
        securityManager.setRealm(new MemberAuthRealm());
        // 这里可以换为RedisCacheManager
        securityManager.setCacheManager(new MemoryConstrainedCacheManager());
        // 将安全工具类中设置默认安全管理器
        // 如果要与后台的shiro公用，则需要另外写个ThreadContextHolder
        SecurityUtils.setSecurityManager(securityManager);
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();

        // 2、用户请求的认证（每次都过）
        MemberAuthToken token = new MemberAuthToken("acooly", "12345678901234567890");
        try {
            //用户登录
            subject.login(token);
            System.out.println("登录成功~~");
        }
        //这些错误抛出顺序有要求的
        catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户名错误!!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("其他错误!!");
        }

        // 3、判断请求是否有权限
        //认证通过
        if (subject.isAuthenticated()) {
            //基于角色权限管理
            boolean admin = subject.hasRole("SYSTEM_ADMIN");
            log.info("角色认证(SYSTEM_ADMIN): {}", admin);
            // 基于权限的判断
            boolean urlPermitted = subject.isPermitted("*:/portal/seller/order/index1.html");
            log.info("URL权限认证(/portal/seller/order/index1.html): {}", urlPermitted);
            urlPermitted = subject.isPermitted("*:/portal/seller/order/index8.html");
            log.info("URL权限认证(/portal/seller/order/index8.html): {}", urlPermitted);
            boolean funcPermitted = subject.isPermitted("member:create");
            log.info("功能权限认证(member:create): {}", funcPermitted);
        }
    }



}

/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-10-05 23:08
 */
package com.acooly.module.test;

import cn.acooly.component.rbac.shiro.RbacShiroRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangpu
 * @date 2022-10-05 23:08
 */
@Slf4j
@Configuration
public class FeatureAutoConfig {

//    @Bean
//    public Realm rbacShiroRealm() {
//        RbacShiroRealm rbacShiroRealm = new RbacShiroRealm();
//        return rbacShiroRealm;
//    }
//
//    @Bean
//    public DefaultSecurityManager rbacShiroSecurityManager(Realm rbacShiroRealm) {
//        DefaultSecurityManager securityManager = new DefaultSecurityManager();
//        securityManager.setCacheManager(new MemoryConstrainedCacheManager());
//        securityManager.setRealm(rbacShiroRealm);
//        // 将安全工具类中设置默认安全管理器
//        // 如果要与后台的shiro公用，则需要另外写个ThreadContextHolder
////        SecurityUtils.setSecurityManager(securityManager);
//        return securityManager;
//    }

}

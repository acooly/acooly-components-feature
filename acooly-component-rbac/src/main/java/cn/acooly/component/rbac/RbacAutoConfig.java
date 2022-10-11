/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-10-05 23:08
 */
package cn.acooly.component.rbac;

import cn.acooly.component.rbac.shiro.RbacSecurityUtils;
import cn.acooly.component.rbac.shiro.RbacShiroRealm;
import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author zhangpu
 * @date 2022-10-05 23:08
 *
 * todo_OK: 1、采用memberNo方式的授权静态工具类
 * todo: 2、redis缓存
 * todo_ok: 3、非受控管理的资源可以忽略权限控制(是否存在的判断不支持AntPath)
 * todo_OK: 4、提供根据用户查询可访问资源的列表: `RbacResourceService.getAuthorizedResourceNode(...)`
 *
 */
@Slf4j
@ComponentScan
@Configuration
@EnableConfigurationProperties(RbacProperties.class)
@ConditionalOnProperty(value = RbacProperties.PREFIX + ".enable", matchIfMissing = true)
@MapperScan("cn.acooly.component.rbac.dao")
public class RbacAutoConfig {

    @Autowired
    private RbacProperties rbacProperties;


    /**
     * Realm实现
     * <p>
     * RBAC的认证和授权实现
     *
     * @return
     */
    @Bean
    public Realm rbacShiroRealm() {
        RbacShiroRealm rbacShiroRealm = new RbacShiroRealm();
        return rbacShiroRealm;
    }


    /**
     * RBAC-Shiro的SecurityManager实现
     *
     * @param rbacShiroRealm
     * @return
     */
    @Bean
    public DefaultSecurityManager rbacShiroSecurityManager(Realm rbacShiroRealm) {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
//        securityManager.setCacheManager(new MemoryConstrainedCacheManager());
        securityManager.setRealm(rbacShiroRealm);
        RbacSecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    /**
     * 自动初始化数据库DDL
     *
     * @return
     */
    @Bean
    public StandardDatabaseScriptIniter rbacDbScriptIniter() {
        return new StandardDatabaseScriptIniter() {

            @Override
            public String getEvaluateTable() {
                return "rbac_user";
            }

            @Override
            public String getComponentName() {
                return "rbac";
            }

            @Override
            public List<String> getInitSqlFile() {
                return Lists.newArrayList("rbac");
            }
        };
    }
}

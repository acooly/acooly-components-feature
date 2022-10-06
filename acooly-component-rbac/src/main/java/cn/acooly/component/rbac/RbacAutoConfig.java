/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-10-05 23:08
 */
package cn.acooly.component.rbac;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangpu
 * @date 2022-10-05 23:08
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
}

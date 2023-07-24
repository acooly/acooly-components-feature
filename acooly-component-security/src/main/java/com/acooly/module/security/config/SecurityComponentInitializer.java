/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-03-15 18:44 创建
 */
package com.acooly.module.security.config;

import com.acooly.core.common.boot.Env;
import com.acooly.core.common.boot.EnvironmentHolder;
import com.acooly.core.common.boot.component.ComponentInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author qiubo@yiji.com
 * @author zhangpu
 */
public class SecurityComponentInitializer implements ComponentInitializer {
    public static final String DUBBO_CUMSTOM_CONFIG_PACKAGE = "acooly.dubbo.customPackagesToScan.sso";

    public static final String DUBBO_SSO_CONFIG_PACKAGE = "com.acooly.module.security.service";

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        setPropertyIfMissing("acooly.security.csrf.exclusions.druid", "/manage/druid/*");

        setPropertyIfMissing("acooly.ds.dbPatchs.sys_user[0].columnName", "pinyin");
        setPropertyIfMissing("acooly.ds.dbPatchs.sys_user[0].patchSql", "ALTER TABLE `sys_user` ADD COLUMN `pinyin` VARCHAR(16) NULL COMMENT '姓名拼音';");

        setPropertyIfMissing("acooly.ds.Checker.excludedColumnTables.security", "sys_role_resc, sys_user_role");
        SecurityProperties securityProperties = new SecurityProperties();
        EnvironmentHolder.buildProperties(securityProperties);
        if (securityProperties.isEnableSsoAuth()) {
            System.setProperty(DUBBO_CUMSTOM_CONFIG_PACKAGE, DUBBO_SSO_CONFIG_PACKAGE);
            setPropertyIfMissing("acooly.olog.storage.enable", "false");
        }

        // org的管理用户
        setPropertyIfMissing("acooly.ds.dbPatchs.sys_org[0].columnName", "username");
        setPropertyIfMissing("acooly.ds.dbPatchs.sys_org[0].patchSql", "ALTER TABLE `sys_org` ADD COLUMN `username` VARCHAR(32) NULL COMMENT '管理用户' AFTER `name`;");
        setPropertyIfMissing("acooly.ds.dbPatchs.sys_org[1].columnName", "sort_time");
        setPropertyIfMissing("acooly.ds.dbPatchs.sys_org[1].patchSql", "ALTER TABLE `sys_org` ADD COLUMN `sort_time` BIGINT NULL COMMENT '排序值' AFTER `email`; update `sys_org` set sort_time =  (UNIX_TIMESTAMP(NOW()) + id) where id > 0;");

        // 线上系统，密码强度自少为: usually
        if (Env.isOnline()) {
            setPropertyIfMissing("acooly.framework.password-strength", "usually");
        }
    }
}

/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-16 17:04 创建
 */
package com.acooly.module.olog.config;

import com.acooly.core.common.boot.component.ComponentInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author qiubo@yiji.com
 * @author zhangpu@acooly.cn
 */
@Slf4j
public class OLogComponentInitializer implements ComponentInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // 临时方案：启动后每次会执行，修改operate_message长度为4096
        setPropertyIfMissing("acooly.ds.dbPatchs. sys_olog[0].columnName", "nonexistent_column");
        setPropertyIfMissing("acooly.ds.dbPatchs.sys_olog[0].patchSql", "ALTER TABLE `sys_olog` CHANGE COLUMN `operate_message` `operate_message` VARCHAR(4096) NULL DEFAULT NULL COMMENT '消息' ;\n");
    }
}

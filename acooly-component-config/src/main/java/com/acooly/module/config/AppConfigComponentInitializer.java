
package com.acooly.module.config;

import com.acooly.core.common.boot.component.ComponentInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author qiubo
 * @author zhangpu
 */
public class AppConfigComponentInitializer implements ComponentInitializer {

    private static final Logger logger = LoggerFactory.getLogger(AppConfigComponentInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        // 升级数据库，增加两个字段。
        setPropertyIfMissing("acooly.ds.dbPatchs.sys_app_config[0].columnName", "title");
        setPropertyIfMissing("acooly.ds.dbPatchs.sys_app_config[0].patchSql", "ALTER TABLE `sys_app_config` ADD COLUMN `title` VARCHAR(45) NULL COMMENT '标题' AFTER `id`;");
        setPropertyIfMissing("acooly.ds.dbPatchs.sys_app_config[1].columnName", "config_type");
        setPropertyIfMissing("acooly.ds.dbPatchs.sys_app_config[1].patchSql", "ALTER TABLE `sys_app_config` ADD COLUMN `config_type` VARCHAR(16) NULL COMMENT '配置类型{text:文本,option:选项,json:JSON}' AFTER `config_value`;");


    }
}

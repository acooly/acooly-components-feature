ALTER TABLE `acooly-pm`.`sys_app_config`
ADD COLUMN `title` VARCHAR(45) NULL COMMENT '标题' AFTER `id`,
ADD COLUMN `config_type` VARCHAR(16) NULL COMMENT '配置类型{text:文本,option:选项,json:JSON}' AFTER `config_value`;



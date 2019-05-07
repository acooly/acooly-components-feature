package com.acooly.module.data.region;

import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author zhangpu
 *
 * todo: 1、沟通管理
 * todo: 2、用于web界面三级联动的对应的js库封装
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = "com.acooly.module.data.region")
public class DataRegionAutoConfig {

    @Bean
    public StandardDatabaseScriptIniter appConfigScriptIniter() {
        return new StandardDatabaseScriptIniter() {
            @Override
            public String getEvaluateTable() {
                return "data_region";
            }

            @Override
            public String getComponentName() {
                return "data-region";
            }

            @Override
            public List<String> getInitSqlFile() {
                return Lists.newArrayList("ddl", "init");
            }
        };
    }

}

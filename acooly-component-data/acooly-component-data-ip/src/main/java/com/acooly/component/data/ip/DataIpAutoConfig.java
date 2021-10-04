package com.acooly.component.data.ip;

import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author zhangpu
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = "com.acooly.component.data.ip")
public class DataIpAutoConfig {

//    @Bean
//    public StandardDatabaseScriptIniter dataRegionConfigScriptIniter() {
//        return new StandardDatabaseScriptIniter() {
//            @Override
//            public String getEvaluateTable() {
//                return "data_region";
//            }
//
//            @Override
//            public String getComponentName() {
//                return "data-region";
//            }
//
//            @Override
//            public List<String> getInitSqlFile() {
//                return Lists.newArrayList("ddl");
//            }
//        };
//    }

}

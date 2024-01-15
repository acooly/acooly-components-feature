package com.acooly.module.syncdata;

import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
import com.acooly.module.security.config.SecurityAutoConfig;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.acooly.module.syncdata.SyncDataProperties.PREFIX;

/**
 * @author cuifuqiang
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({SyncDataProperties.class})
@ConditionalOnProperty(value = PREFIX + ".enable", matchIfMissing = true)
@ComponentScan(basePackages = "com.acooly.module.syncdata")
@AutoConfigureAfter(SecurityAutoConfig.class)
public class SyncDataAutoConfig {

    @Bean
    public StandardDatabaseScriptIniter syncDataConfigScriptIniter() {
        log.info("加载[acooly-component-syncdata]组件。。。。。。");

        return new StandardDatabaseScriptIniter() {

            @Override
            public String getEvaluateTable() {
                return "table_async_data";
            }

            @Override
            public String getComponentName() {
                return "syncdata";
            }

            @Override
            public List<String> getInitSqlFile() {
                return Lists.newArrayList("syncdata", "syncdata_urls");
            }
        };
    }
}

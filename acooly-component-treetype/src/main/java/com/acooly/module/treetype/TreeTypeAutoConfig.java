/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2019-11-03 12:16
 */
package com.acooly.module.treetype;

import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author zhangpu
 * @date 2019-11-03 12:16
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({TreeTypeProperties.class})
@ConditionalOnProperty(value = TreeTypeProperties.PREFIX + ".enable", matchIfMissing = true)
@ComponentScan
public class TreeTypeAutoConfig {

    @Bean
    public StandardDatabaseScriptIniter treeTypeScriptIniter() {
        return new StandardDatabaseScriptIniter() {
            @Override
            public String getEvaluateTable() {
                return "ac_tree_type";
            }

            @Override
            public String getComponentName() {
                return "treetype";
            }

            @Override
            public List<String> getInitSqlFile() {
                return Lists.newArrayList("treetype", "treetype_urls");
            }
        };
    }


}

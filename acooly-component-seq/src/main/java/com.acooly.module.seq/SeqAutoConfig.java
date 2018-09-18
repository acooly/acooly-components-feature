package com.acooly.module.seq;

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
 * @author qiubo@yiji.com
 */
@Configuration
@EnableConfigurationProperties({SeqProperties.class})
@ConditionalOnProperty(value = "acooly.seq.enable", matchIfMissing = true)
@Slf4j
@ComponentScan
public class SeqAutoConfig {

    @Bean
    public StandardDatabaseScriptIniter seqScriptIniter() {
        return new StandardDatabaseScriptIniter() {
            @Override
            public String getEvaluateTable() {
                return "sys_seq";
            }

            @Override
            public String getComponentName() {
                return "seq";
            }

            @Override
            public List<String> getInitSqlFile() {
                return Lists.newArrayList("seq");
            }
        };
    }
}

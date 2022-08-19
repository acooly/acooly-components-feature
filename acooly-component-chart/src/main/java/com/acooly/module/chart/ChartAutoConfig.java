package com.acooly.module.chart;

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

import static com.acooly.module.chart.ChartProperties.PREFIX;

/**
 * @author cuifuqiang
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({ChartProperties.class})
@ConditionalOnProperty(value = PREFIX + ".enable", matchIfMissing = true)
@ComponentScan(basePackages = "com.acooly.module.chart")
@AutoConfigureAfter(SecurityAutoConfig.class)
public class ChartAutoConfig {

    @Bean
    public StandardDatabaseScriptIniter chartScriptIniter() {
        log.info("加载[acooly-component-chart]组件。。。。。。");
        return new StandardDatabaseScriptIniter() {
            @Override
            public String getEvaluateTable() {
                return "c_chart";
            }

            @Override
            public String getComponentName() {
                return "chart";
            }

            @Override
            public List<String> getInitSqlFile() {
                return Lists.newArrayList("chart", "chart_urls");
            }
        };
    }
}

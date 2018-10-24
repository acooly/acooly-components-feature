package com.acooly.module.chat;


import static com.acooly.module.chat.ChatProperties.PREFIX;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
import com.acooly.module.security.config.SecurityAutoConfig;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cuifuqiang
 */
@Configuration
@EnableConfigurationProperties({ChatProperties.class})
@ConditionalOnProperty(value = PREFIX + ".enable", matchIfMissing = true)
@ComponentScan(basePackages = "com.acooly.module.chat")
@AutoConfigureAfter(SecurityAutoConfig.class)
@Slf4j
public class ChatAutoConfig {
    @Autowired
    private ChatProperties appProperties;

    @Bean
    public StandardDatabaseScriptIniter chatScriptIniter() {
        return new StandardDatabaseScriptIniter() {
            @Override
            public String getEvaluateTable() {
                return "im_chat_user";
            }

            @Override
            public String getComponentName() {
                return "chat";
            }

            @Override
            public List<String> getInitSqlFile() {
                return Lists.newArrayList("chat", "chat_urls");
            }
        };
    }
}

package com.acooly.module.ocr;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static com.acooly.module.ocr.OcrProperties.PREFIX;

/**
 * @author liangsong
 * @date 2020-03-23 13:39
 */
@Configuration
@EnableConfigurationProperties({OcrProperties.class})
@ConditionalOnProperty(value = PREFIX + ".enable", matchIfMissing = true)
@ComponentScan(basePackages = "com.acooly.module.ocr")
public class OcrAutoConfig {

    //todo 后续再考虑存储问题
//    @Bean
//    public StandardDatabaseScriptIniter ocrScriptIniter() {
//        return new StandardDatabaseScriptIniter() {
//            @Override
//            public String getEvaluateTable() {
//                return "ac_ocr";
//            }
//
//            @Override
//            public String getComponentName() {
//                return "ocr";
//            }
//
//            @Override
//            public List<String> getInitSqlFile() {
//                return Lists.newArrayList("ocr", "ocr_urls");
//            }
//        };
//    }
}

package com.acooly.module.face;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static com.acooly.module.face.FaceProperties.PREFIX;

/**
 * @author liangsong
 * @date 2020-03-23 13:39
 */
@Configuration
@EnableConfigurationProperties({FaceProperties.class})
@ConditionalOnProperty(value = PREFIX + ".enable", matchIfMissing = true)
@ComponentScan(basePackages = "com.acooly.module.face")
public class FaceAutoConfig {

    //todo 后续再考虑存储问题
//    @Bean
//    public StandardDatabaseScriptIniter faceScriptIniter() {
//        return new StandardDatabaseScriptIniter() {
//            @Override
//            public String getEvaluateTable() {
//                return "ac_face";
//            }
//
//            @Override
//            public String getComponentName() {
//                return "face";
//            }
//
//            @Override
//            public List<String> getInitSqlFile() {
//                return Lists.newArrayList("face", "face_urls");
//            }
//        };
//    }
}

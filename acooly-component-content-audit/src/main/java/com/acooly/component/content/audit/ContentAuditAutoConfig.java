package com.acooly.component.content.audit;

import com.acooly.component.content.audit.service.ContentAuditService;
import com.acooly.component.content.audit.service.impl.ContentAuditServiceMockImpl;
import com.acooly.component.content.audit.service.impl.ContentAuditServiceSdkImpl;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangpu@acooly.cn
 */
@Configuration
@EnableConfigurationProperties({ContentAuditProperties.class})
@ConditionalOnProperty(value = ContentAuditProperties.PREFIX + ".enable", matchIfMissing = true)
@ComponentScan(basePackages = "com.acooly.component.content.audit")
public class ContentAuditAutoConfig {

    @Autowired
    private ContentAuditProperties contentAuditProperties;

    @Bean
    public IAcsClient iAcsClient() {
        ContentAuditProperties.Aliyun aliyun = contentAuditProperties.getAliyun();
        IClientProfile profile = DefaultProfile
                .getProfile(aliyun.getRegionId(), aliyun.getAccessKey(), aliyun.getSecretKey());
        return new DefaultAcsClient(profile);
    }

    @Bean
    public ContentAuditService contentAuditService(IAcsClient iAcsClient) {
        if (contentAuditProperties.getMock()) {
            return new ContentAuditServiceMockImpl();
        }
        return new ContentAuditServiceSdkImpl(iAcsClient);
    }


}

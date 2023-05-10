/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2021-07-23 22:53
 */
package com.acooly.module.test.contentaduit;

import com.acooly.component.content.audit.domain.ImageAuditRequest;
import com.acooly.component.content.audit.domain.TextAuditExtRequest;
import com.acooly.component.content.audit.domain.TextAuditExtResponse;
import com.acooly.component.content.audit.enums.ImageScenes;
import com.acooly.component.content.audit.service.ContentAuditService;
import com.acooly.component.content.audit.service.impl.ContentAuditServiceSdkImpl;
import com.acooly.core.common.exception.BusinessException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhangpu
 * @date 2021-07-23 22:53
 */
@Slf4j
public class ContentAuditServiceTest {

    @Autowired
    ContentAuditService contentAuditService;

    String accessKey = "";
    String secretKey = "";

    @Before
    public void init() {
        IClientProfile profile = DefaultProfile
                .getProfile("cn-shanghai", accessKey, secretKey);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        contentAuditService = new ContentAuditServiceSdkImpl(acsClient);
    }


    @Test
    public void testTextScan() {
        try {
            TextAuditExtRequest request = new TextAuditExtRequest("我爱中国共产党，下载有车云App");
            request.addTask("汽车炸弹，自杀攻击");
            TextAuditExtResponse response = contentAuditService.textScanExt(request);
            log.info("Response: {}", response);
        } catch (BusinessException be) {
            log.warn("异常：{}", be.toString());
        } catch (Exception e) {
            throw new BusinessException();
        }


    }

    @Test
    public void imageTextScan() {

        try {
            ImageAuditRequest request = new ImageAuditRequest("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg6.cache.netease.com%2Fphoto%2F0006%2F2014-03-10%2F900x600_9MVNJHDQ17KK0006.jpg&refer=http%3A%2F%2Fimg6.cache.netease.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1629706886&t=70dfe8f4f9a03db1c627530c4b0aa212");
            request.addScenes(ImageScenes.porn, ImageScenes.terrorism);
            contentAuditService.imageScan(request);
        } catch (BusinessException be) {
            log.warn("异常：{}", be.toString());
        } catch (Exception e) {
            throw new BusinessException();
        }


    }


}

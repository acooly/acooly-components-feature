/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * kuli@yiji.com 2017-05-14 15:23 创建
 */
package com.acooly.module.portlet.integration.impl;

import com.acooly.module.portlet.entity.Feedback;
import com.acooly.module.portlet.integration.FeedbackHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author acooly
 */
@Slf4j
public class DefaultFeedbackHandler implements FeedbackHandler {

    @Override
    public void handle(Feedback feedback) {
        log.info("acooly-component-portlet组件默认FeedbackHandler实现:{}", feedback);
    }
}

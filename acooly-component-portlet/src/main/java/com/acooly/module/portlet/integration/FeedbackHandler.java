/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * kuli@yiji.com 2017-03-29 16:08 创建
 */
package com.acooly.module.portlet.integration;

import com.acooly.module.portlet.entity.Feedback;

/**
 * 客户反馈处理接口
 *
 * <p>集成项目可根据自己的需求实现该接口，组件的处理功能会回调该接口进行处理。
 *
 * @author acooly
 */
public interface FeedbackHandler {

    /**
     * 处理接口，如果抛出异常，则表示该项的处理失败。
     *
     * @param feedback
     */
    void handle(Feedback feedback);
}

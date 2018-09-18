package com.acooly.module.portlet.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.portlet.entity.Feedback;
import com.acooly.module.portlet.enums.FeedbackStatusEnum;
import com.acooly.module.portlet.enums.FeedbackTypeEnum;

import javax.annotation.Nullable;

/**
 * 客户反馈 Service
 *
 * <p>Date: 2015-05-19 21:58:49
 *
 * @author Acooly Code Generator
 */
public interface FeedbackService extends EntityService<Feedback> {

    /**
     * 提交反馈
     */
    Feedback submit(
            FeedbackTypeEnum type,
            String content,
            @Nullable String title,
            @Nullable String phoneNo,
            @Nullable String address,
            @Nullable String contactInfo,
            @Nullable String comments,
            @Nullable String userName);

    Feedback submit(FeedbackTypeEnum type, String content);

    /**
     * 处理反馈
     *
     * @param id
     * @param status
     * @param comments
     */
    void handle(Long id, FeedbackStatusEnum status, String comments);
}

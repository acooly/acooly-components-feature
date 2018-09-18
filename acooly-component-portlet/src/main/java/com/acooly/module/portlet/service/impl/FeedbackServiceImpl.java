package com.acooly.module.portlet.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Strings;
import com.acooly.module.portlet.dao.FeedbackDao;
import com.acooly.module.portlet.entity.Feedback;
import com.acooly.module.portlet.enums.FeedbackStatusEnum;
import com.acooly.module.portlet.enums.FeedbackTypeEnum;
import com.acooly.module.portlet.integration.FeedbackHandler;
import com.acooly.module.portlet.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

import javax.annotation.Nullable;
import javax.transaction.Transactional;

@Service("feedbackService")
public class FeedbackServiceImpl extends EntityServiceImpl<Feedback, FeedbackDao>
        implements FeedbackService {

    private static final int CONTENT_PREFIX_LENGTH = 8;

    @Autowired
    private FeedbackHandler feedbackHandler;

    @Override
    public Feedback submit(FeedbackTypeEnum type, String content) {
        return submit(type, content, null, null, null, null, null, null);
    }

    @Override
    public Feedback submit(
            FeedbackTypeEnum type,
            String content,
            @Nullable String title,
            @Nullable String phoneNo,
            @Nullable String address,
            @Nullable String contactInfo,
            @Nullable String comments,
            @Nullable String userName) {
        Assert.notNull(type);
        Assert.notNull(content);
        Feedback feedback = new Feedback();
        feedback.setType(type);
        feedback.setContent(content);
        if (Strings.isBlank(title)) {
            title = Strings.substring(content, 0, CONTENT_PREFIX_LENGTH);
        }
        feedback.setTitle(title);
        feedback.setUserName(userName);
        feedback.setTelephone(phoneNo);
        feedback.setAddress(address);
        feedback.setComments(comments);
        feedback.setContactInfo(contactInfo);
        save(feedback);
        return feedback;
    }

    @Transactional
    @Override
    public void handle(Long id, FeedbackStatusEnum status, String comments) {
        Feedback feedback = get(id);
        if (feedback == null) {
            throw new BusinessException("数据不存在", "Not_Exsit_Data");
        }
        feedback.setStatus(status);
        feedback.setComments(comments);
        update(feedback);

        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCommit() {
                        feedbackHandler.handle(feedback);
                    }
                });
    }
}

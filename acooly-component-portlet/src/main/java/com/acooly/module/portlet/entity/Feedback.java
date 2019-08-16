package com.acooly.module.portlet.entity;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.portlet.enums.FeedbackStatusEnum;
import com.acooly.module.portlet.enums.FeedbackTypeEnum;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 客户反馈 Entity
 *
 * <p>Date: 2015-05-19 21:58:49
 *
 * @author Acooly Code Generator
 */
@Table(name = "portlet_feedback")
public class Feedback extends AbstractEntity {

    /**
     * 类型
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private FeedbackTypeEnum type = FeedbackTypeEnum.suggest;
    /**
     * 标题
     */
    @NotBlank
    private String title;
    /**
     * 内容
     */
    @NotBlank
    private String content;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 联系电话
     */
    private String telephone;
    /**
     * 联系地址
     */
    private String address;
    /**
     * 联系信息
     */
    private String contactInfo;

    /**
     * 处理状态
     */
    private FeedbackStatusEnum status = FeedbackStatusEnum.submit;

    /**
     * 备注
     */
    private String comments;

    public FeedbackTypeEnum getType() {
        return type;
    }

    public void setType(FeedbackTypeEnum type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public FeedbackStatusEnum getStatus() {
        return status;
    }

    public void setStatus(FeedbackStatusEnum status) {
        this.status = status;
    }
}

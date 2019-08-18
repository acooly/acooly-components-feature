package com.acooly.module.app.domain;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.app.enums.AppMessageContentType;
import com.acooly.module.app.enums.AppMessageStatus;
import com.acooly.module.app.enums.AppMessageType;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Date;

/**
 * 系统消息 Entity
 *
 * @author Acooly Code Generator Date: 2015-11-04 13:30:36
 */
@Entity
@Table(name = "app_message")
public class AppMessage extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 发送时间
     */
    private Date sendTime;
    /**
     * 内容类型
     */
    @Enumerated(EnumType.STRING)
    private AppMessageContentType contentType;
    /**
     * 群发类型
     */
    @Enumerated(EnumType.STRING)
    private AppMessageType type;
    /**
     * 定时发送时间
     */
    private Date schedulerTime;
    /**
     * 发送内容
     */
    private String content;

    /**
     * 会话内容
     */
    private String context;

    /**
     * 发送人
     */
    private String sender;
    /**
     * 类型为group时有效。多个用户使用逗号分隔
     */
    private String receivers;
    /**
     * 状态.
     */
    @Enumerated(EnumType.STRING)
    private AppMessageStatus status;
    /**
     * 备注
     */
    private String comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public AppMessageType getType() {
        return type;
    }

    public void setType(AppMessageType type) {
        this.type = type;
    }

    public Date getSchedulerTime() {
        return this.schedulerTime;
    }

    public void setSchedulerTime(Date schedulerTime) {
        this.schedulerTime = schedulerTime;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return this.sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceivers() {
        return this.receivers;
    }

    public void setReceivers(String receivers) {
        this.receivers = receivers;
    }

    public AppMessageStatus getStatus() {
        return status;
    }

    public void setStatus(AppMessageStatus status) {
        this.status = status;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public AppMessageContentType getContentType() {
        return contentType;
    }

    public void setContentType(AppMessageContentType contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

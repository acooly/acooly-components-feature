/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-03-20
 */
package com.acooly.module.portlet.entity;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.portlet.enums.ActionChannelEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * portlet_action_log Entity
 *
 * @author acooly Date: 2017-03-20 23:36:29
 */
@Entity
@Table(name = "portlet_action_log")
public class ActionLog extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 操作
     */
    private String actionKey;

    /**
     * 操作名称
     */
    private String actionName;

    /**
     * URL连接
     */
    private String actionUrl;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 渠道
     */
    @Enumerated(EnumType.STRING)
    private ActionChannelEnum channel;

    /**
     * 渠道信息
     */
    private String channelInfo;

    /**
     * 渠道版本
     */
    private String channelVersion;

    /**
     * 访问IP
     */
    private String userIp;

    /**
     * 备注
     */
    private String comments;

    public String getActionKey() {
        return this.actionKey;
    }

    public void setActionKey(String actionKey) {
        this.actionKey = actionKey;
    }

    public String getActionName() {
        return this.actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionUrl() {
        return this.actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ActionChannelEnum getChannel() {
        return channel;
    }

    public void setChannel(ActionChannelEnum channel) {
        this.channel = channel;
    }

    public String getChannelInfo() {
        return this.channelInfo;
    }

    public void setChannelInfo(String channelInfo) {
        this.channelInfo = channelInfo;
    }

    public String getChannelVersion() {
        return this.channelVersion;
    }

    public void setChannelVersion(String channelVersion) {
        this.channelVersion = channelVersion;
    }

    public String getUserIp() {
        return this.userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}

/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-03-21
 */
package com.acooly.module.portlet.entity;

import com.acooly.core.common.domain.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 访问映射 Entity
 *
 * @author acooly Date: 2017-03-21 00:34:47
 */
@Entity
@Table(name = "portlet_action_mapping")
public class ActionMapping extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 连接
     */
    private String url;

    /**
     * 名称
     */
    private String title;
    /**
     * 备注
     */
    private String comments;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}

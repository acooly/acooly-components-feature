package com.acooly.module.app.domain;

import com.acooly.core.common.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * app_banner Entity
 *
 * <p>Date: 2015-05-12 13:39:31
 *
 * @author Acooly Code Generator
 */
@Entity
@Table(name = "app_banner")
public class AppBanner extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -8431750621851935776L;

    /**
     * 标题
     */
    @Column(
            name = "title",
            nullable = false,
            length = 64,
            columnDefinition = "varchar(64) not null COMMENT '标题'"
    )
    private String title;

    /**
     * 广告栏图片
     */
    @Column(
            name = "media_url",
            nullable = false,
            length = 128,
            columnDefinition = "varchar(128) not null COMMENT '广告图片'"
    )
    private String mediaUrl;

    /**
     * 文字内容
     */
    @Column(
            name = "content",
            nullable = true,
            length = 255,
            columnDefinition = "varchar(255) COMMENT '文字内容'"
    )
    private String content;

    /**
     * 链接内容
     */
    @Column(
            name = "link",
            nullable = true,
            length = 128,
            columnDefinition = "varchar(128) not null COMMENT '链接内容'"
    )
    private String link;

    /**
     * 排序时间
     */
    @Column(
            name = "sort_time",
            columnDefinition = "datetime NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '排序时间'"
    )
    private Date sortTime = new Date();

    /**
     * 备注
     */
    @Column(
            name = "comments",
            nullable = true,
            length = 255,
            columnDefinition = "varchar(255) COMMENT '备注'"
    )
    private String comments;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMediaUrl() {
        return this.mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getSortTime() {
        return this.sortTime;
    }

    public void setSortTime(Date sortTime) {
        this.sortTime = sortTime;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}

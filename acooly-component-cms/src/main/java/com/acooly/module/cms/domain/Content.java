package com.acooly.module.cms.domain;

import com.acooly.core.common.domain.Entityable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 内容主表 Entity
 *
 * <p>Date: 2013-07-12 15:06:46
 *
 * @author Acooly Code Generator
 */
@Getter
@Setter
@Entity
@Table(name = "CMS_CONTENT")
public class Content implements Entityable {

    public static final int STATUS_ENABLED = 1; // 1:正常
    public static final int STATUS_DISABLED = 2; // 2:禁用

    /**
     * uid
     */
    private static final long serialVersionUID = 7860091097881184418L;

    @Id
    @GeneratedValue(generator = "incrementGenerator", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "incrementGenerator", strategy = "increment")
    private Long id;

    /**
     * 编码 唯一
     */
    private String keycode;
    /**
     * 标题
     */
    private String title;
    /**
     * 封面
     */
    private String cover;
    /**
     * app封面
     */
    private String appcover;
    /**
     * 发布时间
     */
    private Date pubDate = new Date();

    @Transient
    private String pubDateStr;

    /**
     * 页面标题(网页的title)
     */
    private String webTitle;
    /**
     * 关键字 (SEO的keywords)
     */
    private String keywords;
    /**
     * 主题介绍(SEO的description)
     */
    private String subject;
    /**
     * 作者
     */
    private String author;
    /**
     * 点击数
     */
    private Long hits = 1L;
    /**
     * 状态 (1:正常,2:禁用)
     */
    private int status = STATUS_ENABLED;
    /**
     * 备注
     */
    private String comments;

    /**
     * 外链连接
     */
    private String link;

    private Date createTime;

    private Date updateTime;

    /**
     * 所属分类
     */
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH}
    )
    @JoinColumn(name = "type")
    private ContentType contentType;

    /**
     * 内容主体
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @PrimaryKeyJoinColumn
    @JoinColumn(name = "id")
    private ContentBody contentBody;

    /**
     * 附件列表
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "content")
    private Set<Attachment> attachments = new HashSet<Attachment>();

    @Transient
    private String body_;



    @Transient
    public String getBody_() {
        return body_;
    }

    public void setBody_(String body) {
        body_ = body;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

}

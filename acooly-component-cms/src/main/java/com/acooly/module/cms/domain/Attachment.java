package com.acooly.module.cms.domain;

import com.acooly.core.common.domain.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 内容附件 Entity
 *
 * <p>Date: 2013-07-12 15:06:46
 *
 * @author Acooly Code Generator
 */
@Entity
@Table(name = "CMS_ATTACHMENT")
@JsonIgnoreProperties(value = {"content"})
public class Attachment extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3836741638106403157L;

    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件大小
     */
    private Long fileSize;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 备注
     */
    private String comments;

    @ManyToOne
    @JoinColumn(name = "contentid")
    private Content content;

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

package com.acooly.module.app.domain;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.app.enums.EntityStatus;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.*;

/**
 * app_start_guide Entity
 *
 * <p>Date: 2015-05-22 14:44:16
 *
 * @author Acooly Code Generator
 */
@Entity
@Table(name = "app_start_guide")
public class AppStartGuide extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -134075544760362347L;
    /**
     * 排序（大小顺序）
     */
    @Column(
            name = "sort_order",
            nullable = false,
            columnDefinition = "bigint not null COMMENT '排序（大小顺序）'"
    )
    private long sortOrder = 1L;
    /**
     * 默认图片
     */
    @Column(
            name = "image_default",
            nullable = false,
            length = 255,
            columnDefinition = "varchar(255) not null COMMENT '默认图片'"
    )
    private String imageDefault;
    /**
     * IPHONE4
     */
    @Column(
            name = "image_iphone4",
            nullable = true,
            length = 255,
            columnDefinition = "varchar(255) COMMENT 'IPHONE4图片'"
    )
    private String imageIphone4;
    /**
     * iphone5/6: 1242*2208
     */
    @Column(
            name = "image_iphone6",
            nullable = true,
            length = 255,
            columnDefinition = "varchar(255) COMMENT 'iphone5/6图片'"
    )
    private String imageIphone6;
    /**
     * android: 1080 * 1920
     */
    @Column(
            name = "image_android",
            nullable = true,
            length = 255,
            columnDefinition = "varchar(255) COMMENT 'android图片'"
    )
    private String imageAndroid;
    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 16,
            columnDefinition = "varchar(16) not null COMMENT '状态'"
    )
    private EntityStatus status = EntityStatus.Enable;

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

    public long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(long sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Enumerated(EnumType.STRING)
    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }

    public String getImageDefault() {
        return this.imageDefault;
    }

    public void setImageDefault(String imageDefault) {
        this.imageDefault = imageDefault;
    }

    public String getImageIphone4() {
        return this.imageIphone4;
    }

    public void setImageIphone4(String imageIphone4) {
        this.imageIphone4 = imageIphone4;
    }

    public String getImageIphone6() {
        return this.imageIphone6;
    }

    public void setImageIphone6(String imageIphone6) {
        this.imageIphone6 = imageIphone6;
    }

    public String getImageAndroid() {
        return this.imageAndroid;
    }

    public void setImageAndroid(String imageAndroid) {
        this.imageAndroid = imageAndroid;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

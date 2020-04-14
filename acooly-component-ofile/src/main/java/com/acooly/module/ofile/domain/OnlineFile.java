/**
 * create by zhangpu date:2015年5月16日
 */
package com.acooly.module.ofile.domain;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.ofile.OFileProperties;
import com.acooly.module.ofile.enums.AccessTypeEnum;
import com.acooly.module.ofile.enums.OFileType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zhangpu
 */
@Entity
@Table(name = "ofile")
@Getter
@Setter
public class OnlineFile extends AbstractEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2665757624295221489L;
    private static OFileProperties oFileProperties;
    @Id
    @GeneratedValue
    protected Long id;
    /**
     * 文件对象ID(唯一标识)
     */
    @Column(
            name = "object_id",
            nullable = false,
            length = 64,
            columnDefinition = "varchar(64) not null COMMENT '对象ID'"
    )
    private String objectId;
    /**
     * 表单名称
     */
    @Column(
            name = "input_name",
            nullable = false,
            length = 64,
            columnDefinition = "varchar(64) not null COMMENT '表单名称'"
    )
    private String inputName;
    /**
     * 文件名
     */
    @Column(
            name = "file_name",
            nullable = false,
            length = 64,
            columnDefinition = "varchar(64) not null COMMENT '文件名'"
    )
    private String fileName;
    /**
     * 文件类型
     */
    @Column(
            name = "file_type",
            nullable = false,
            length = 16,
            columnDefinition = "varchar(16) not null COMMENT '文件类型'"
    )
    @Enumerated(EnumType.STRING)
    private OFileType fileType;
    /**
     * 模块分类,如:客户，积分等根据业务的分类
     */
    @Column(
            name = "module",
            nullable = true,
            length = 16,
            columnDefinition = "varchar(16) COMMENT '模块分类'"
    )
    private String module;
    /**
     * 扩展名
     */
    @Column(
            name = "file_ext",
            nullable = true,
            length = 8,
            columnDefinition = "varchar(8)  COMMENT '扩展名'"
    )
    private String fileExt;
    /**
     * 文件大小(byte)
     */
    @Column(name = "file_size", nullable = false, columnDefinition = "bigint COMMENT '文件大小'")
    private long fileSize;
    /**
     * 存储位置
     */
    @Column(
            name = "file_path",
            nullable = false,
            length = 128,
            columnDefinition = "varchar(128) not null COMMENT '图片路径'"
    )
    private String filePath;
    /**
     * 图片缩略图
     */
    @Column(
            name = "thumbnail",
            nullable = true,
            length = 128,
            columnDefinition = "varchar(128) COMMENT '缩略图路径'"
    )
    private String thumbnail;
    @Column(
            name = "user_name",
            nullable = true,
            length = 32,
            columnDefinition = "varchar(32) COMMENT '用户名'"
    )
    private String userName;
    /**
     * 上传时间
     */
    @Column(
            name = "create_time",
            columnDefinition = "datetime NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间'"
    )
    private Date createTime = new Date();
    /**
     * 更新时间
     */
    @Column(name = "update_time", columnDefinition = "datetime COMMENT '最后更新时间'")
    private Date updateTime;
    @Column(
            name = "status",
            nullable = true,
            length = 16,
            columnDefinition = "varchar(16) DEFAULT 'enable' COMMENT '状态'"
    )
    private String status = "enable";
    /**
     * 原始文件名
     */
    @Column(
            name = "original_name",
            nullable = false,
            length = 255,
            columnDefinition = "varchar(64) not null COMMENT '原始文件名'"
    )
    private String originalName;
    /**
     * 文件元数据
     */
    @Column(
            name = "metadatas",
            nullable = true,
            length = 255,
            columnDefinition = "varchar(255) COMMENT '元数据，可以是JSON格式'"
    )
    private String metadatas;
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

    /**
     * obs桶名称
     */
    @Column(
            name = "bucket_name",
            nullable = true,
            length = 64,
            columnDefinition = "varchar(64) COMMENT 'obs桶名称'"
    )
    private String bucketName;

    /**
     * 文件访问类型
     */
    @Column(
            name = "access_type",
            nullable = false,
            length = 16,
            columnDefinition = "varchar(16) not null COMMENT '文件访问类型'"
    )
    @Enumerated(EnumType.STRING)
    private AccessTypeEnum accessType = AccessTypeEnum.LOCAL_STORAGE;

    /**
     * 可访问的文件地址
     */
    @Transient
    private String accessUrl;

    /**
     * 可访问的缩略图地址
     */
    @Transient
    private String accessThumbnailUrl;

    public void init() {
        if (oFileProperties == null) {
            oFileProperties = Apps.getApplicationContext().getBean(OFileProperties.class);
        }
    }

    /**
     * 获取文件绝对路径
     */
    @JsonIgnore
    public String getAbsolutePath() {
        init();
        return oFileProperties.getStorageRoot() + filePath;
    }

    /**
     * 获取缩略图绝对路径
     *
     * @return
     */
    @JsonIgnore
    public String getThumbnailAbsolutePath() {
        init();
        return oFileProperties.getStorageRoot() + thumbnail;
    }

    /**
     * 获取文件访问地址
     *
     * @return
     */
    public String getAccessUrl() {
        if (AccessTypeEnum.LOCAL_STORAGE.equals(accessType)) {
            init();
            return oFileProperties.getServerRoot() + filePath;
        }
        return accessUrl;
    }

    /**
     * 获取缩略图文件访问地址
     * 历史使用过的方法，将在不使用后移除
     *
     * @return
     */
    @Deprecated
    public String getThumbnailAccessUrl() {
        if (AccessTypeEnum.LOCAL_STORAGE.equals(accessType)) {
            init();
            return oFileProperties.getServerRoot() + thumbnail;
        }
        return accessThumbnailUrl;
    }

    public String getAccessThumbnailUrl() {
        if (AccessTypeEnum.LOCAL_STORAGE.equals(accessType)) {
            init();
            return oFileProperties.getServerRoot() + thumbnail;
        }
        return accessThumbnailUrl;
    }
}

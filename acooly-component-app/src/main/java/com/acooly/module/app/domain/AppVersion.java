package com.acooly.module.app.domain;

import com.acooly.core.common.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 手机客户端版本 Entity
 *
 * <p>Date: 2014-10-25 23:16:14
 *
 * @author Acooly Code Generator
 */
@Entity
@Table(name = "app_version")
public class AppVersion extends AbstractEntity {
    public static final String DEVICE_TYPE_ANDROID = "android";
    public static final String DEVICE_TYPE_IPHONE = "iphone";
    /**
     * UID
     */
    private static final long serialVersionUID = 803284446362117189L;

    @Column(
            name = "app_code",
            nullable = false,
            length = 32,
            columnDefinition = "varchar(32) not null COMMENT '应用编码'"
    )
    private String appCode;

    @Column(
            name = "app_name",
            nullable = false,
            length = 32,
            columnDefinition = "varchar(32) not null COMMENT '应用名称'"
    )
    private String appName;

    /**
     * 设备类型
     */
    @Column(
            name = "device_type",
            nullable = false,
            length = 32,
            columnDefinition = "varchar(32) not null COMMENT '设备类型{android:android,iphone:iphone}'"
    )
    private String deviceType;

    /**
     * 版本号 如：1.0.0, 用于显示
     */
    @Column(
            name = "version_name",
            nullable = false,
            length = 16,
            columnDefinition = "varchar(16) not null COMMENT '版本号(如：1.0.0, 用于显示)'"
    )
    private String versionName;

    /**
     * 版本编码 通过这个的最大值判断最新版本
     */
    @Column(
            name = "version_code",
            nullable = false,
            columnDefinition = "int not null COMMENT '版本编码(通过这个的最大值判断最新版本)'"
    )
    private int versionCode = 0;

    /**
     * 版本说明
     */
    @Column(
            name = "subject",
            nullable = true,
            length = 255,
            columnDefinition = "varchar(255) COMMENT '版本说明'"
    )
    private String subject;

    /**
     * 真实下载URL，全URL
     */
    @Column(
            name = "url",
            nullable = true,
            length = 255,
            columnDefinition = "varchar(255) COMMENT '下载URL'"
    )
    private String url;

    /**
     * 物理路径
     */
    @Column(
            name = "path",
            nullable = true,
            length = 255,
            columnDefinition = "varchar(255) COMMENT '物理路径'"
    )
    private String path;

    /**
     * 苹果安装地址
     */
    @Column(
            name = "apple_url",
            nullable = true,
            length = 255,
            columnDefinition = "varchar(255) COMMENT '苹果安装地址'"
    )
    private String appleUrl;

    /**
     * 发布时间
     */
    @Column(
            name = "pub_time",
            nullable = false,
            columnDefinition = "datetime NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '发布时间'"
    )
    private Date pubTime = new Date();

    /**
     * 是否强制更新 0：否，1：是
     */
    @Column(
            name = "force_update",
            nullable = false,
            columnDefinition = "int not null COMMENT '是否强制更新{0:否,1:是}'"
    )
    private int forceUpdate = 0;

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

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getPubTime() {
        return this.pubTime;
    }

    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }

    public int getForceUpdate() {
        return this.forceUpdate;
    }

    public void setForceUpdate(int forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppleUrl() {
        return appleUrl;
    }

    public void setAppleUrl(String appleUrl) {
        this.appleUrl = appleUrl;
    }
}

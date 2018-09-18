package com.acooly.module.app.domain;

import com.acooly.core.common.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * app_crash Entity
 *
 * @author Acooly Code Generator Date: 2015-09-11 23:05:38
 */
@Entity
@Table(name = "app_crash")
public class AppCrash extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 应用名称
     */
    @Column(
            name = "app_name",
            nullable = true,
            length = 32,
            columnDefinition = "varchar(32) COMMENT '应用名称'"
    )
    private String appName;

    /**
     * 用户名
     */
    @Column(
            name = "user_name",
            nullable = true,
            length = 16,
            columnDefinition = "varchar(16) COMMENT '用户名'"
    )
    private String userName;
    /**
     * 平台ID
     */
    @Column(
            name = "platform_id",
            nullable = true,
            length = 64,
            columnDefinition = "varchar(64) COMMENT '平台ID'"
    )
    private String platformId;
    /**
     * Android版本号
     */
    @Column(
            name = "android_version",
            nullable = true,
            length = 16,
            columnDefinition = "varchar(16) COMMENT 'Android版本号'"
    )
    private String androidVersion;

    /**
     * 应用的版本代码
     */
    @Column(
            name = "app_version_code",
            nullable = true,
            length = 16,
            columnDefinition = "varchar(16) COMMENT '应用版本代码'"
    )
    private String appVersionCode;

    /**
     * 应用的版本名称
     */
    @Column(
            name = "app_version_name",
            nullable = true,
            length = 16,
            columnDefinition = "varchar(16) COMMENT '应用版本名称'"
    )
    private String appVersionName;
    /**
     * 设备ID
     */
    @Column(
            name = "device_id",
            nullable = true,
            length = 64,
            columnDefinition = "varchar(64) COMMENT '设备ID'"
    )
    private String deviceId;
    /**
     * 手机/平板的模型
     */
    @Column(
            name = "model",
            nullable = true,
            length = 32,
            columnDefinition = "varchar(32) COMMENT '模型'"
    )
    private String model;
    /**
     * 品牌
     */
    @Column(
            name = "brand",
            nullable = true,
            length = 32,
            columnDefinition = "varchar(32) COMMENT '品牌'"
    )
    private String brand;

    /**
     * Android产品信息
     */
    @Column(
            name = "product",
            nullable = true,
            length = 255,
            columnDefinition = "varchar(255) COMMENT '设备产品信息'"
    )
    private String product;
    /**
     * 应用的包名
     */
    @Column(
            name = "package_name",
            nullable = true,
            length = 128,
            columnDefinition = "varchar(128) COMMENT '应用的包名'"
    )
    private String packageName;
    /**
     * 崩溃的堆栈信息
     */
    @Column(name = "stack_trace", columnDefinition = "text COMMENT '堆栈信息'")
    private String stackTrace;

    /**
     * 崩溃的时间点
     */
    @Column(
            name = "crash_date",
            updatable = false,
            nullable = false,
            columnDefinition = "datetime NOT NULL comment '崩溃时间'"
    )
    private Date crashDate = new Date();

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPlatformId() {
        return this.platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getAndroidVersion() {
        return this.androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getAppVersionCode() {
        return this.appVersionCode;
    }

    public void setAppVersionCode(String appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public String getAppVersionName() {
        return this.appVersionName;
    }

    public void setAppVersionName(String appVersionName) {
        this.appVersionName = appVersionName;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProduct() {
        return this.product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Date getCrashDate() {
        return this.crashDate;
    }

    public void setCrashDate(Date crashDate) {
        this.crashDate = crashDate;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getStackTrace() {
        return this.stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

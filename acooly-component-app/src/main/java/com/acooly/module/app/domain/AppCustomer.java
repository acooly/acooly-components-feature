package com.acooly.module.app.domain;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.module.app.enums.EntityStatus;
import com.acooly.openapi.framework.common.enums.DeviceType;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.persistence.*;

/**
 * app_customer Entity
 *
 * <p>Date: 2015-05-12 13:39:30
 *
 * @author Acooly Code Generator
 */
@Entity
@Table(name = "app_customer")
public class AppCustomer extends AbstractEntity {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 330662474736234327L;

    /**
     * 用户名
     */
    @Column(
            name = "user_name",
            nullable = false,
            length = 16,
            columnDefinition = "varchar(16) not null COMMENT '用户名'"
    )
    private String userName;

    /**
     * 访问码
     */
    @Column(
            name = "access_key",
            nullable = false,
            length = 64,
            columnDefinition = "varchar(64) not null COMMENT '访问码'"
    )
    private String accessKey;

    /**
     * 安全码
     */
    @Column(
            name = "secret_key",
            nullable = false,
            length = 64,
            columnDefinition = "varchar(64) not null COMMENT '安全码'"
    )
    private String secretKey;

    /**
     * 设备类型
     */
    @Enumerated(EnumType.STRING)
    @Column(
            name = "device_type",
            nullable = true,
            length = 16,
            columnDefinition = "varchar(16) COMMENT '设备类型'"
    )
    private DeviceType deviceType;

    /**
     * 设备型号
     */
    @Column(
            name = "device_model",
            nullable = true,
            length = 64,
            columnDefinition = "varchar(64) COMMENT '设备型号'"
    )
    private String deviceModel;

    /**
     * 设备标识
     */
    @Column(
            name = "device_id",
            nullable = false,
            length = 64,
            columnDefinition = "varchar(64) not null COMMENT '设备标识'"
    )
    private String deviceId;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = true,
            length = 16,
            columnDefinition = "varchar(16) COMMENT '状态'"
    )
    private EntityStatus status;

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public DeviceType getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

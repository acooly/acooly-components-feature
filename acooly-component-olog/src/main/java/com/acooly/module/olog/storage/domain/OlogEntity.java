package com.acooly.module.olog.storage.domain;

import com.acooly.core.common.domain.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 操作日志 Entity
 *
 * <p>Date: 2013-02-28 22:59:16
 *
 * @author Acooly Code Generator
 */
@Entity
@Table(name = "sys_olog")
@Getter
@Setter
public class OlogEntity extends AbstractEntity {
    public static final int OPERATE_RESULT_SUCCESS = 1;
    public static final int OPERATE_RESULT_FAILURE = 2;

    /**
     * 所属系统标志
     */
    @Column(
            name = "system",
            nullable = true,
            length = 32,
            columnDefinition = "varchar(32) COMMENT '所属系统'"
    )
    private String system = "BOSS";

    /**
     * 模块名称
     */
    @Column(
            name = "module_name",
            nullable = true,
            length = 255,
            columnDefinition = "varchar(255) COMMENT '模块名称'"
    )
    private String moduleName;

    /**
     * 功能模块
     */
    @Column(
            name = "module",
            nullable = true,
            length = 255,
            columnDefinition = "varchar(255) COMMENT '模块标志'"
    )
    private String module;

    /**
     * 操作名称
     */
    @Column(
            name = "action_name",
            nullable = true,
            length = 32,
            columnDefinition = "varchar(32) COMMENT '操作名称'"
    )
    private String actionName;

    /**
     * 操作
     */
    @Column(
            name = "action",
            nullable = true,
            length = 32,
            columnDefinition = "varchar(32) COMMENT '操作标志'"
    )
    private String action;

    /**
     * 操作时间
     */
    @Column(
            name = "operate_time",
            columnDefinition = "datetime NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '操作时间'"
    )
    private Date operateTime = new Date();

    /**
     * 操作员
     */
    @Column(
            name = "operate_user",
            nullable = true,
            length = 64,
            columnDefinition = "varchar(32) COMMENT '操作员'"
    )
    private String operateUser;

    /**
     * 操作员ID
     */
    @Column(name = "operate_user_id", nullable = true, columnDefinition = "bigint COMMENT '操作员ID'")
    private Long operateUserId;

    /**
     * 操作结果
     */
    @Column(name = "operate_result", nullable = false, columnDefinition = "int COMMENT '操作结果'")
    private int operateResult = OPERATE_RESULT_SUCCESS;

    @Column(
            name = "operate_message",
            nullable = true,
            length = 4096,
            columnDefinition = "varchar(512) COMMENT '操作结果消息'"
    )
    private String operateMessage;

    /**
     * 备注
     */
    @Column(
            name = "descn",
            nullable = true,
            length = 255,
            columnDefinition = "varchar(255) COMMENT '备注'"
    )
    private String descn;

    /**
     * 请求参数
     */
    @Column(
            name = "request_parameters",
            nullable = true,
            length = 512,
            columnDefinition = "varchar(512) COMMENT '请求参数'"
    )
    private String requestParameters;

    /**
     * 客户端信息
     */
    @Column(
            name = "client_informations",
            nullable = true,
            length = 255,
            columnDefinition = "varchar(255) COMMENT '客户端信息'"
    )
    private String clientInformations;

    /**
     * 执行时间
     */
    @Column(name = "execute_milliseconds", nullable = false, columnDefinition = "int COMMENT '执行时长'")
    private long executeMilliseconds = 0;
}

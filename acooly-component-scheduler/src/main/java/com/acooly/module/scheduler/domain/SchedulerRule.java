package com.acooly.module.scheduler.domain;

import com.acooly.core.common.domain.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author shuijing
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "scheduler_rule")
public class SchedulerRule extends AbstractEntity {

    public static int MAX_LENGTH = 256;

    @Column(name = "cron_string")
    private String cronString;

    @Column(name = "properties")
    private String properties;

    @Column(name = "action_type")
    private String actionType;

    @Column(name = "memo")
    private String memo;

    @Column(name = "creater")
    private String creater;

    @Column(name = "modifyer")
    private String modifyer;

    @Column(name = "validity_start")
    private Date validityStart;

    @Column(name = "validity_end")
    private Date validityEnd;

    @Column(name = "last_execute_time")
    private Date lastExecuteTime;

    @Column(name = "execute_num")
    private Integer executeNum;

    @Column(name = "status")
    private String status;

    @Column(name = "className")
    private String className;

    @Column(name = "methodName")
    private String methodName;

    @Column(name = "d_group")
    private String dGroup;

    @Column(name = "d_version")
    private String dVersion;

    @Column(name = "d_param")
    private String dParam;

    @Column(name = "exceptionAtLastExecute")
    private String exceptionAtLastExecute;

    @Column(name = "retryTimeOnException")
    private Integer retryTimeOnException;

    @Column(name = "isDel", columnDefinition = " bit(1) DEFAULT 0 COMMENT '是否删除' ")
    private Boolean isDel;
}

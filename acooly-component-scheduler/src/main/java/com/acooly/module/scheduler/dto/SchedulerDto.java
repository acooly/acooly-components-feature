package com.acooly.module.scheduler.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shuijing
 */
@Getter
@Setter
public class SchedulerDto implements Serializable {

    private String cronString;

    private String properties;

    private String actionType;

    private String memo;

    private String creater;

    private String modifyer;

    private Date validityStart;

    private Date validityEnd;

    private Date lastExecuteTime;

    private Integer executeNum;

    private String status;

    private String className;

    private String methodName;

    private String dGroup;

    private String dVersion;

    private String dParam;

    private String exceptionAtLastExecute;

    private Integer retryTimeOnException;

    private Boolean isDel;

    //展示用的地址
    private String url;

    private String validityStartStr;

    private String validityEndStr;
}

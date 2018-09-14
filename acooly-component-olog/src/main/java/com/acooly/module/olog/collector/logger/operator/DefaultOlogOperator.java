/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * kuli@yiji.com 2016-11-01 02:47 创建
 */
package com.acooly.module.olog.collector.logger.operator;

/**
 * @author acooly
 */
public class DefaultOlogOperator implements OlogOperator {

    private Long operatorUserId;
    private String operatorUserName;


    @Override
    public Long getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Long operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    @Override
    public String getOperatorUserName() {
        return operatorUserName;
    }

    public void setOperatorUserName(String operatorUserName) {
        this.operatorUserName = operatorUserName;
    }
}

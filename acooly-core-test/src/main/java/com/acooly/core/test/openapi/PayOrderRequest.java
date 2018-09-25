/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-02-19 22:04 创建
 */
package com.acooly.core.test.openapi;

import com.acooly.core.utils.Money;
import com.acooly.openapi.framework.common.annotation.OpenApiField;
import com.acooly.openapi.framework.common.message.ApiRequest;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author qiubo@yiji.com
 */
@Getter
@Setter
public class PayOrderRequest extends ApiRequest {

    @NotNull
    @OpenApiField(desc = "金额")
    private Money amount;

    @NotEmpty
    @OpenApiField(desc = "买家用户ID")
    private String payerUserId;
}

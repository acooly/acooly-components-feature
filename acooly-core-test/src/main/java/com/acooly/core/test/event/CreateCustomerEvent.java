/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2017-03-10 16:09 创建
 */
package com.acooly.core.test.event;

import lombok.Data;

/**
 * @author qiubo@yiji.com
 */
@Data
public class CreateCustomerEvent {
    private Long id;
    private String userName;
}

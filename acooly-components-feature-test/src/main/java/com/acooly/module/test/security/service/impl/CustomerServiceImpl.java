/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by zhangpu
 * date:2020-04-06
 */
package com.acooly.module.test.security.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.test.security.dao.CustomerDao;
import com.acooly.module.test.security.entity.Customer;
import com.acooly.module.test.security.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * acoolycoder测试 Service实现
 * <p>
 * Date: 2020-04-06 23:23:46
 *
 * @author zhangpu
 */
@Slf4j
@Service("customerService")
public class CustomerServiceImpl extends EntityServiceImpl<Customer, CustomerDao> implements CustomerService {

    @Override
    public void justDoIt() {
        System.out.println("Just do test for the local task of scheduler");
    }
}

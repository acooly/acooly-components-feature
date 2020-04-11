/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by zhangpu
 * date:2020-04-06
 */
package com.acooly.module.test.security.service.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.test.security.service.CustomerService;
import com.acooly.module.test.security.dao.CustomerDao;
import com.acooly.module.test.security.entity.Customer;

/**
 * acoolycoder测试 Service实现
 *
 * Date: 2020-04-06 23:23:46
 *
 * @author zhangpu
 *
 */
@Service("customerService")
public class CustomerServiceImpl extends EntityServiceImpl<Customer, CustomerDao> implements CustomerService {

}

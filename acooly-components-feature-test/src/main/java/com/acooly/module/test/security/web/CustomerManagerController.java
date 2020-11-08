/*
 * acooly.cn Inc.
 * Copyright (c) 2020 All Rights Reserved.
 * create by zhangpu
 * date:2020-04-06
 */
package com.acooly.module.test.security.web;

import com.acooly.core.common.enums.Gender;
import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.module.test.security.entity.Customer;
import com.acooly.module.test.security.enums.CustomerTypeEnum;
import com.acooly.module.test.security.enums.IdcardTypeEnum;
import com.acooly.module.test.security.service.CustomerService;
import com.google.common.collect.Maps;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * acoolycoder测试 管理控制器
 *
 * @author zhangpu
 * Date: 2020-04-06 23:23:45
 */
@Controller
@RequestMapping(value = "/manage/module/test/security/customer")
public class CustomerManagerController extends AbstractJsonEntityController<Customer, CustomerService> {

    private static Map<Integer, String> allStatuss = Maps.newLinkedHashMap();

    static {
        allStatuss.put(0, "无效");
        allStatuss.put(1, "有效");
    }

    {
        allowMapping = "*";
    }

    @SuppressWarnings("unused")
    @Autowired
    private CustomerService customerService;


    @RequiresPermissions("customer:create")
    @Override
    public String create(HttpServletRequest request, HttpServletResponse response, Model model) {
        return super.create(request, response, model);
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allGenders", Gender.mapping());
        model.put("allIdcardTypes", IdcardTypeEnum.mapping());
        model.put("allCustomerTypes", CustomerTypeEnum.mapping());
        model.put("allStatuss", allStatuss);
    }

}

/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-06-29 09:18
 */
package com.acooly.module.test.olog;

import com.acooly.module.test.security.entity.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangpu
 * @date 2022-06-29 09:18
 */
@Slf4j
@RequestMapping("/manage/feature/olog/")
@RestController
public class OlogTestController {

    @RequestMapping("data")
    public Object testRest(HttpServletRequest request) {
        Customer customer = new Customer();
        customer.setUsername("zhangpu1");
        request.getSession().setAttribute("customer1", customer);
        return customer;
    }


}

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

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangpu
 * @date 2022-06-29 09:18
 */
@Slf4j
@RequestMapping("/manage/feature/olog/")
@Controller
public class OlogTestController {

    @RequestMapping("data")
    public Object testRest(HttpServletRequest request) {

        Customer customer = new Customer();


        return null;

    }


}

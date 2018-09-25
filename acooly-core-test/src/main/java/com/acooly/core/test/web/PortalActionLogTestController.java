/*
 * www.yiji.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * kuli@yiji.com 2017-05-15 01:31 创建
 */
package com.acooly.core.test.web;

import com.acooly.core.test.dao.CityMybatisDao;
import com.acooly.core.test.domain.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author acooly
 */
@Controller
@RequestMapping("/portal/acionlog")
public class PortalActionLogTestController {

    @Autowired
    private CityMybatisDao cityDao;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    public List<City> index(HttpServletRequest request) {
        request.getSession().setAttribute("sessionCustomer", "zhangpu(张浦)");
        return cityDao.getAll();
    }
}

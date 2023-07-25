/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-06-29 09:18
 */
package com.acooly.module.test.security;

import com.acooly.core.utils.Servlets;
import com.acooly.module.security.dto.OrgManagers;
import com.acooly.module.security.service.OrgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangpu
 * @date 2022-06-29 09:18
 */
@Slf4j
@RequestMapping("/test/org")
@RestController
public class OrgTestController {

    @Autowired
    private OrgService orgService;

    @RequestMapping("getOrgManagers")
    public Object getOrgManagers(HttpServletRequest request) {
        Long id = Servlets.getLongParameter(request, "id");
        OrgManagers orgManagers = orgService.getOrgManagers(id);
        return orgManagers;
    }


}

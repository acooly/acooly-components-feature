package com.acooly.core.test.portlet;

import com.acooly.core.utils.Strings;
import com.acooly.module.portlet.service.ActionLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangpu
 */
@Slf4j
@Controller
@RequestMapping("/portal/actionlog")
public class PortletActionLogTestController {

    @Autowired(required = false)
    private ActionLogService actionLogService;

    @RequestMapping("log")
    @ResponseBody
    public Object log(HttpServletRequest request) {
        return "just test log";
    }


    @RequestMapping("batch")
    @ResponseBody
    public Object batch(HttpServletRequest request) {

        int count = Integer.parseInt(Strings.isBlankDefault(request.getParameter("count"), "10"));
        for (int i = 0; i < count; i++) {
            actionLogService.logger(request, "testUserName");
        }
        return "just batch log: " + count;
    }

}

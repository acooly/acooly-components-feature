package com.acooly.module.security.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manage/druid")
public class DruidController {

    @RequestMapping("/index")
    public String index() {
        return "redirect:/manage/druid/index.html";
    }
}

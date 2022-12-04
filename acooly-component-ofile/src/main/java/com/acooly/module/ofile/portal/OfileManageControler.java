/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-12-04 15:14
 */
package com.acooly.module.ofile.portal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Shiro后台BOSS专用上传配套控制器
 * <p>
 * 切换Shiro-session后，只有/manage/下的访问才能获取后台Shiro的session值
 *
 * @author zhangpu
 * @date 2022-12-04 15:14
 */
@Slf4j
@Controller
@RequestMapping("/manage/ofile")
public class OfileManageControler extends OfilePortalController {

    @RequestMapping("kindEditor")
    @ResponseBody
    public Map<String, Object> kindEditor(HttpServletRequest request, HttpServletResponse response) {
        return super.kindEditor(request, response);
    }
}

package com.acooly.core.test.openapi;

import com.acooly.openapi.framework.common.ApiConstants;
import com.acooly.openapi.framework.common.utils.Servlets;
import com.acooly.openapi.framework.facade.api.OpenApiRemoteService;
import com.acooly.openapi.framework.facade.order.ApiNotifyOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @author shuijing
 */
@Slf4j
@RestController
@RequestMapping(value = "/notify")
public class OpenapiController implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @RequestMapping("sender")
    @ResponseBody
    public void send(HttpServletRequest req, HttpServletResponse resp, Model model)
            throws IOException {

        Map<String, String> requestData = Servlets.getParameters(req);
        log.info("MOCK下层调用OpenApiArchServcie：{}", requestData);
        String partnerId = req.getParameter(ApiConstants.PARTNER_ID);
        String gid = req.getParameter(ApiConstants.GID);
        String type = req.getParameter("type");
        if (StringUtils.isBlank(type)) {
            type = "async";
        }
        requestData.remove(ApiConstants.PARTNER_ID);
        requestData.remove(ApiConstants.GID);

        Writer w = resp.getWriter();
        Object result = null;
        try {
            w.write("call :" + type);
            OpenApiRemoteService openApiRemoteService =
                    applicationContext.getBean(OpenApiRemoteService.class);
            if (StringUtils.equals(type, "async")) {
                ApiNotifyOrder order = new ApiNotifyOrder();
                order.setParameters(requestData);
                order.setPartnerId(partnerId);
                order.setGid(gid);
                openApiRemoteService.asyncNotify(order);
                w.write("success. result:" + result);
            } else {
            }
            log.info("MOCK调用通知结果:{}", result);
        } catch (Exception e) {
            w.write("failure:" + e.getMessage());
            log.info("MOCK调用通知失败, result:{},error:{}", result, e.getMessage());
        } finally {
            IOUtils.closeQuietly(w);
        }
    }

    @RequestMapping("receiver")
    @ResponseBody
    public String receiver(HttpServletRequest req, HttpServletResponse resp, Model model) {
        String signType = req.getParameter("signType");
        log.info("success. signType:{}", signType);
        return "success";
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

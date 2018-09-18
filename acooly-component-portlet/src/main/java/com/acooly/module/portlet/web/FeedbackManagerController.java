package com.acooly.module.portlet.web;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.module.portlet.entity.Feedback;
import com.acooly.module.portlet.enums.FeedbackStatusEnum;
import com.acooly.module.portlet.enums.FeedbackTypeEnum;
import com.acooly.module.portlet.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping(value = "/manage/module/portlet/feedback")
public class FeedbackManagerController
        extends AbstractJQueryEntityController<Feedback, FeedbackService> {

    private static Map<String, String> allTypes = FeedbackTypeEnum.mapping();
    @Autowired
    private FeedbackService feedbackService;

    {
        allowMapping = "*";
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allTypes", allTypes);
        model.put("allStatuss", FeedbackStatusEnum.mapping());
    }

    @Override
    protected Map<String, Boolean> getSortMap(HttpServletRequest request) {
        Map<String, Boolean> sortMap = super.getSortMap(request);
        sortMap.put("status", false);
        sortMap.put("id", false);
        return sortMap;
    }

    @RequestMapping(value = "handle")
    @ResponseBody
    public JsonEntityResult<Feedback> handle(
            HttpServletRequest request, HttpServletResponse response) {
        JsonEntityResult<Feedback> result = new JsonEntityResult<Feedback>();
        try {
            String requId = request.getParameter(getEntityIdName());
            String requStatus = request.getParameter("status");
            String requComments = request.getParameter("comments");
            feedbackService.handle(
                    Long.valueOf(requId), FeedbackStatusEnum.find(requStatus), requComments);
            result.setMessage("处理成功");
        } catch (Exception e) {
            handleException(result, "处理", e);
        }
        return result;
    }
}

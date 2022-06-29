package com.acooly.module.olog.storage.web;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.olog.annotation.Olog;
import com.acooly.core.common.olog.annotation.OlogAction;
import com.acooly.core.common.olog.annotation.OlogParameterMapping;
import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.module.olog.storage.domain.OlogEntity;
import com.acooly.module.olog.storage.service.OlogService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/manage/component/olog/olog")
@Olog.Ignore
public class OlogManageController extends AbstractJsonEntityController<OlogEntity, OlogService> {

    private static Map<Integer, String> allOperateResults = Maps.newTreeMap();

    static {
        allOperateResults.put(1, "成功");
        allOperateResults.put(2, "失败");
    }

    @RequestMapping("archive")
    @ResponseBody
    @OlogAction(action = "archive", actionName = "手动归档")
    public JsonResult archive(HttpServletRequest request, HttpServletResponse response, Model model) {
        JsonResult result = new JsonResult();
        try {
            this.getEntityService().archive();
            result.setMessage("手动归档成功");
        } catch (Exception e) {
            handleException(result, "手动归档", e);
        }
        return result;
    }

    @OlogParameterMapping({"name:名称"})
    @Override
    protected PageInfo<OlogEntity> doList(
            HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

        return this.getEntityService()
                .query(getPageInfo(request), getSearchParams(request), getSortMap(request));
    }

    @Override
    protected List<OlogEntity> doQuery(
            HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        return this.getEntityService().query(getSearchParams(request), null);
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allOperateResults", allOperateResults);
    }
}

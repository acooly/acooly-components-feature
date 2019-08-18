package com.acooly.module.app.web;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.module.app.domain.AppStartGuide;
import com.acooly.module.app.enums.EntityStatus;
import com.acooly.module.app.service.AppStartGuideService;
import com.acooly.module.ofile.OFileProperties;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.Serializable;
import java.util.Map;

@Controller
@RequestMapping(value = "/manage/module/app/appStartGuide")
public class AppStartGuideManagerController
        extends AppAbstractManageController<AppStartGuide, AppStartGuideService> {

    private static Map<String, String> allStatuss = EntityStatus.mapping();

    @Autowired
    private OFileProperties oFileProperties;
    @Autowired
    private AppStartGuideService appStartGuideService;

    @Override
    protected AppStartGuide onSave(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model,
            AppStartGuide entity,
            boolean isCreate)
            throws Exception {
        Map<String, UploadResult> uploadResults = doUpload(request);
        if (uploadResults != null && !uploadResults.isEmpty()) {
            UploadResult uploadResult = uploadResults.get("fileDefault");
            if (uploadResult != null) {
                entity.setImageDefault(getDatabasePath(uploadResult));
            }
            uploadResult = uploadResults.get("fileIphone4");
            if (uploadResult != null) {
                entity.setImageIphone4(getDatabasePath(uploadResult));
            }
            uploadResult = uploadResults.get("fileIphone6");
            if (uploadResult != null) {
                entity.setImageIphone6(getDatabasePath(uploadResult));
            }
            uploadResult = uploadResults.get("fileAndroid");
            if (uploadResult != null) {
                entity.setImageAndroid(getDatabasePath(uploadResult));
            }
        }
        if (isCreate) {
            entity.setUpdateTime(entity.getCreateTime());
        }
        String comments = convertCharSet(request, "comments");
        entity.setComments(comments);
        entity.setSortOrder(System.currentTimeMillis());
        return entity;
    }

    @Override
    protected void doRemove(
            HttpServletRequest request, HttpServletResponse response, Model model, Serializable... ids)
            throws Exception {
        if (ids == null || ids.length == 0) {
            return;
        }
        AppStartGuide asg = loadEntity(request);
        if (StringUtils.isNotBlank(asg.getImageDefault())) {
            removeFile(asg.getImageDefault());
        }
        if (StringUtils.isNotBlank(asg.getImageAndroid())) {
            removeFile(asg.getImageAndroid());
        }
        if (StringUtils.isNotBlank(asg.getImageIphone4())) {
            removeFile(asg.getImageIphone4());
        }
        if (StringUtils.isNotBlank(asg.getImageIphone6())) {
            removeFile(asg.getImageIphone6());
        }
        super.doRemove(request, response, model, ids);
    }

    private void removeFile(String path) {
        File file = new File(getStorageRoot() + path);
        try {
            try {
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
    }

    /**
     * 解决方案置顶
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "moveTop")
    @ResponseBody
    public JsonResult moveTop(HttpServletRequest request, HttpServletResponse response) {

        JsonResult result = new JsonResult();
        try {
            String id = request.getParameter("id");
            this.getEntityService().moveTop(Long.valueOf(id));
            result.setMessage("置底成功");
        } catch (Exception e) {
            handleException(result, "置底", e);
        }
        return result;
    }

    /**
     * 上移解决方案
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "moveUp")
    @ResponseBody
    public JsonResult moveUp(HttpServletRequest request, HttpServletResponse response) {

        JsonResult result = new JsonResult();
        try {
            String id = request.getParameter("id");
            this.getEntityService().moveUp(Long.valueOf(id));
            result.setMessage("下移成功");
        } catch (Exception e) {
            handleException(result, "下移", e);
        }
        return result;
    }

    @Override
    protected PageInfo<AppStartGuide> doList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Boolean> sortMap = Maps.newHashMap();
        sortMap.put("sortOrder", false);
        return getEntityService()
                .query(getPageInfo(request), getSearchParams(request), sortMap);
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allStatuss", allStatuss);
    }
}

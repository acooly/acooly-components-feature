package com.acooly.module.ofile.web;

import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.core.common.web.MappingMethod;
import com.acooly.core.common.web.support.JsonListResult;
import com.acooly.core.utils.Strings;
import com.acooly.module.ofile.OFileProperties;
import com.acooly.module.ofile.domain.OnlineFile;
import com.acooly.module.ofile.enums.OFileType;
import com.acooly.module.ofile.enums.SearchTypeEnum;
import com.acooly.module.ofile.service.OnlineFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/manage/module/ofile/onlineFile")
public class OnlineFileManagerController extends AbstractJsonEntityController<OnlineFile, OnlineFileService> {

    private static final Logger logger = LoggerFactory.getLogger(OnlineFileManagerController.class);

    private static Map<String, String> ofileTypes = OFileType.mapping();
    @Autowired
    private OFileProperties oFileProperties;
    @Autowired
    private OnlineFileService onlineFileService;

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allFileTypes", ofileTypes);
        model.put("bussId", request.getParameter("bussId"));
    }

    @RequestMapping({"getInitFiles"})
    @ResponseBody
    public JsonListResult<OnlineFile> getInitFiles(HttpServletRequest request, HttpServletResponse response) {
        JsonListResult<OnlineFile> result = new JsonListResult();
        this.allow(request, response, MappingMethod.list);

        try {
            String ids = request.getParameter("ids");
            JSONObject jsonObject = new JSONObject(ids);
            if (jsonObject.has(SearchTypeEnum.ID.getCode())) {
                String idString = jsonObject.getString(SearchTypeEnum.ID.getCode());
                List<Long> listIds = Arrays.stream(idString.split(","))
                        .map(Long::parseLong)
                        .collect(Collectors.toList());
                List<OnlineFile> fileList = onlineFileService.findById(listIds);
                result.setRows(fileList);
            } else if (jsonObject.has(SearchTypeEnum.OBJ.getCode())) {
                String objString = jsonObject.getString(SearchTypeEnum.OBJ.getCode());
                List<String> listObjs = Arrays.stream(objString.split(","))
                        .collect(Collectors.toList());
                List<OnlineFile> fileList = onlineFileService.findByobjectId(listObjs);
                result.setRows(fileList);
            }

//
        } catch (Exception var5) {
            this.handleException(result, "根据ids查询文件", var5);
        }

        return result;
    }

    @Override
    protected void onRemove(
            HttpServletRequest request, HttpServletResponse response, Model model, Serializable... ids)
            throws Exception {
        OnlineFile onlineFile = null;
        for (Serializable id : ids) {
            onlineFile = onlineFileService.get(id);
            if (onlineFile == null) {
                continue;
            }
            removeFile(onlineFile.getFilePath());
            if (Strings.isNotBlank(onlineFile.getThumbnail())) {
                removeFile(onlineFile.getThumbnail());
            }
        }
    }

    protected void removeFile(String path) {
        try {
            File f = new File(oFileProperties.getStorageRoot() + path);
            if (f.exists()) {
                f.delete();
            }
        } catch (Exception e) {
            logger.warn("删除文件失败:{}", e.getMessage());
        }
    }


    /**
     * 文件上传
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping({"/uploadFilePage{type}"})
    public String uploadFilePage(@PathVariable("type") String type, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            model.addAllAttributes(this.referenceData(request));
        } catch (Exception var5) {
            logger.warn(this.getExceptionMessage("index", var5), var5);
            this.handleException("文件上传", var5, request);
        }
        return "/manage/module/ofile/onlineFileUpload" + type;
    }


}

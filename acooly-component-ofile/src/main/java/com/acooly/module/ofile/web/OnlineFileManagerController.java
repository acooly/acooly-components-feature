package com.acooly.module.ofile.web;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.utils.Strings;
import com.acooly.module.ofile.OFileProperties;
import com.acooly.module.ofile.domain.OnlineFile;
import com.acooly.module.ofile.enums.OFileType;
import com.acooly.module.ofile.service.OnlineFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.Serializable;
import java.util.Map;

@Controller
@RequestMapping(value = "/manage/module/ofile/onlineFile")
public class OnlineFileManagerController
        extends AbstractJQueryEntityController<OnlineFile, OnlineFileService> {

    private static final Logger logger = LoggerFactory.getLogger(OnlineFileManagerController.class);

    private static Map<String, String> ofileTypes = OFileType.mapping();
    @Autowired
    private OFileProperties oFileProperties;
    @Autowired
    private OnlineFileService onlineFileService;

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("ofileTypes", ofileTypes);
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
}

package com.acooly.module.cms.web;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Dates;
import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.mapper.BeanCopier;
import com.acooly.module.cms.domain.*;
import com.acooly.module.cms.event.ContentCreatedEvent;
import com.acooly.module.cms.service.AttachmentService;
import com.acooly.module.cms.service.CmsCodeService;
import com.acooly.module.cms.service.ContentService;
import com.acooly.module.cms.service.ContentTypeService;
import com.acooly.module.event.EventBus;
import com.acooly.module.ofile.OFileProperties;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;

@Controller
@RequestMapping(value = "/manage/module/cms/content")
@Slf4j
public class ContentManagerController extends AbstractJsonEntityController<Content, ContentService> {

    public static Map<Integer, String> allStatuss = Maps.newTreeMap();

    static {
        allStatuss.put(Content.STATUS_ENABLED, "正常");
        allStatuss.put(Content.STATUS_DISABLED, "禁用");
    }

    @Autowired
    private ContentService contentService;
    @Autowired
    private ContentTypeService contentTypeService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private CmsCodeService cmsCodeService;
    @Autowired
    private OFileProperties oFileProperties;
    @Autowired
    private EventBus eventBus;

    /**
     * 预览
     */
    @RequestMapping(value = "preview_{id}")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("id") String id) {
        try {
            Content content = contentService.get(Long.parseLong(id));
            model.addAttribute("content", content);
        } catch (Exception e) {
            handleException("获取内容失败", e, request);
        }
        return "/portal/content_single";
    }

    @RequestMapping(value = "removeAttachment")
    @ResponseBody
    public JsonResult removeAttachment(HttpServletRequest request, HttpServletResponse response) {

        JsonResult result = new JsonResult();
        try {
            String filePath = getFileStorageRoot();
            String id = request.getParameter("id");
            if (StringUtils.isNotBlank(id)) {
                Attachment a = attachmentService.get(Long.valueOf(id));
                if (a != null) {
                    filePath += StringUtils.substringAfter(a.getFilePath(), oFileProperties.getServerRoot());
                    File f = new File(filePath);
                    if (f.exists()) {
                        f.delete();
                    }
                }
                attachmentService.remove(a);
            }
            result.setMessage("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            handleException(result, "删除", e);
        }
        return result;
    }

    @Override
    protected Content onSave(HttpServletRequest request, HttpServletResponse response, Model model, Content entity, boolean isCreate)
            throws Exception {

        Set<Attachment> items = new HashSet<Attachment>();
        String[] strlist = request.getParameterValues("attachment");
        if (strlist != null && strlist.length != 0) {
            for (int index = 0; index < strlist.length; index++) {
                String str = strlist[index];
                if (StringUtils.isNotBlank(str)) {
                    String[] v = str.split("｜");
                    Attachment a = new Attachment();
                    if (v[0] != null && !"".endsWith(v[0])) {
                        a.setId(Long.parseLong(v[0]));
                    }
                    a.setFilePath(v[1] == null ? "0" : v[1]);
                    a.setFileName(v[2] == null ? "0" : v[2]);
                    a.setFileSize(Long.parseLong(v[3] == null ? "0" : v[3]));
                    a.setContent(entity);
                    items.add(a);
                }
            }
        }
        entity.setAttachments(items);
        getUploadConfig().setThumbnailEnable(true);
        Map<String, UploadResult> uploadResults = doUpload(request);
        if (uploadResults != null && uploadResults.size() > 0) {
            UploadResult uploadResult = uploadResults.get("cover_f");
            if (uploadResult != null) {
                if (uploadResult.getSize() > 0) {
                    entity.setCover(uploadResult.getRelativeFile());
                }
            }
            UploadResult appUploadResult = uploadResults.get("cover_app");
            if (appUploadResult != null) {
                if (appUploadResult.getSize() > 0) {
                    entity.setAppcover(appUploadResult.getRelativeFile());
                }
            }
        }
        String code = request.getParameter("code");
        String id = request.getParameter("id");
        ContentType contentType = contentTypeService.getContentType(code);
        if (contentType == null) {
            throw new BusinessException("参数有误，内容类型为null，code=" + code);
        }
        entity.setContentType(contentType);
        ContentBody contentBody = new ContentBody();
        String contents = request.getParameter("contents");
        contentBody.setBody(contents == null ? "" : contents);
        if (!isCreate && id != null) {
            contentBody.setId(Long.valueOf(id));
        }
        entity.setContentBody(contentBody);
        contentBody.setContent(entity);
        setPubDate(entity, request);
        return entity;
    }

    private void setPubDate(Content entity, HttpServletRequest request) {
        String pubDate = request.getParameter("pubDate");
        if (StringUtils.isNotEmpty(pubDate)) {
            Date parse = Dates.parse(pubDate, "yyyy-MM-dd HH:mm:ss");
            entity.setPubDate(parse);
        }
    }

    private String getFileStorageRoot() {
        return oFileProperties.getStorageRoot();
    }

    /**
     * @return
     */
    private String getFileServerRoot() {
        return oFileProperties.getServerRoot();
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allStatuss", allStatuss);
        model.put("mediaRoot", getFileServerRoot());

        List<CmsCode> allCode = cmsCodeService.getAll();
        Map<String, String> codes = Maps.newHashMap();
        String code = request.getParameter("code");
        allCode.forEach(
                cmsCode -> {
                    if (1 == cmsCode.getStatus() && (cmsCode.getTypeCode() != null && cmsCode.getTypeCode().equals(code))) {
                        codes.put(cmsCode.getKeycode(), cmsCode.getDescn());
                    }
                });
        model.put("allCodes", codes);
    }

    @Override
    protected void onEdit(
            HttpServletRequest request, HttpServletResponse response, Model model, Content entity) {
        entity.setPubDateStr(Dates.format(entity.getPubDate()));
    }

    /**
     * 删除内容
     * <p>
     * 包括删除文件
     *
     * @param request
     * @param response
     * @param model
     * @param ids
     * @throws Exception
     */
    @Override
    protected void onRemove(HttpServletRequest request, HttpServletResponse response, Model model, Serializable... ids) throws Exception {

        if (ids == null || ids.length == 0) {
            throw new RuntimeException("请求参数中没有指定需要删除的实体Id");
        }
        // 循环删除文件
        Content content = null;
        for (Serializable id : ids) {
            content = getEntityService().get(id);
            if (content == null) {
                continue;
            }
            deleteRelativeFile(content.getCover(), request);
            deleteRelativeFile(content.getAppcover(), request);

            deleteFileWithBody(content.getContentBody().getBody(), request);
        }
    }


    private void deleteFileWithBody(String body, HttpServletRequest request) {

        try {
            Document doc = Jsoup.parse(body);
            Elements elements = doc.select("img[src^=" + getFileServerRoot() + "]");
            String filePath = null;
            for (Element e : elements) {
                filePath = e.attr("src");
                if (Strings.isBlank(filePath)) {
                    continue;
                }
                filePath = Strings.substringAfter(filePath, getFileServerRoot());
                deleteRelativeFile(filePath, request);
            }
        } catch (Exception e) {
            log.warn("删除body中的上传文件失败");
        }


    }

    private void deleteRelativeFile(String relativePath, HttpServletRequest request) {
        if (Strings.isBlank(relativePath)) {
            return;
        }
        File f = new File(getFileStorageRoot(), relativePath);
        File thumb = getThumbnailFile(f, request);
        FileUtils.deleteQuietly(f);
        FileUtils.deleteQuietly(thumb);
    }

    @Override
    protected Content doSave(
            HttpServletRequest request, HttpServletResponse response, Model model, boolean isCreate)
            throws Exception {

        Content entity = loadEntity(request);
        if (entity == null) {
            entity = new Content();
        }
        doDataBinding(request, entity);
        onSave(request, response, model, entity, isCreate);
        // 这里服务层默认是根据entity的Id是否为空自动判断是SAVE还是UPDATE.
        getEntityService().save(entity);
        entity.setContentBody(new ContentBody()); // IE8 兼容性问题，html 转JSON

        if (isEventNotify(request)) {
            ContentCreatedEvent event = new ContentCreatedEvent();
            BeanCopier.copy(entity, event);
            eventBus.publishAsync(event);
        }

        return entity;
    }

    private boolean isEventNotify(HttpServletRequest request) {
        String isEventNotify = request.getParameter("isEventNotify");
        return StringUtils.isNotBlank(isEventNotify);
    }

    @RequestMapping("importJsonReview")
    @ResponseBody
    public String importJsonReview(
            HttpServletRequest request, HttpServletResponse response, Model model) {
        String filePath = "";
        try {
            String storageRoot = getFileStorageRoot();
            Map<String, UploadResult> uploadResults = doUpload(request);
            if (uploadResults != null && uploadResults.size() > 0) {

                Iterator<Entry<String, UploadResult>> iterator = uploadResults.entrySet().iterator();

                if (iterator.hasNext()) {
                    Map.Entry<String, UploadResult> entry = iterator.next();
                    UploadResult uploadResult = entry.getValue();
                    if (uploadResult != null) {
                        if (uploadResult.getSize() > 0) {
                            File f = uploadResult.getFile();
                            filePath = f.getPath();
                            filePath = filePath.substring(storageRoot.length());
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return getFileServerRoot() + filePath;
    }

    @RequestMapping(value = "moveTop")
    @ResponseBody
    public JsonResult moveTop(HttpServletRequest request, HttpServletResponse response) {

        JsonResult result = new JsonResult();
        try {
            String id = request.getParameter(getEntityIdName());
            contentService.moveTop(Long.valueOf(id));
            result.setMessage("置顶成功");
        } catch (Exception e) {
            handleException(result, "置顶", e);
        }
        return result;
    }

    @RequestMapping(value = "moveUp")
    @ResponseBody
    public JsonResult moveUp(HttpServletRequest request, HttpServletResponse response) {

        JsonResult result = new JsonResult();
        try {
            String id = request.getParameter(getEntityIdName());
            contentService.moveUp(Long.valueOf(id));
            result.setMessage("上移成功");
        } catch (Exception e) {
            handleException(result, "上移", e);
        }
        return result;
    }

    @Override
    protected UploadConfig getUploadConfig() {
        super.uploadConfig.setUseMemery(false);
        super.uploadConfig.setNeedTimePartPath(false);
        super.uploadConfig.setStorageRoot(getFileStorageRoot());
        super.uploadConfig.setStorageNameSpace(oFileProperties.getStorageNameSpace());
        return this.uploadConfig;
    }

    @Override
    protected Map<String, Object> getSearchParams(HttpServletRequest request) {
        Map<String, Object> params = Servlets.getParametersStartingWith(request, "search_");
        String code = request.getParameter("code"); // 内容类型编号
        if (StringUtils.isNotBlank(code)) {
            params.put("EQ_contentType.code", code);
        }
        params.put("EQ_status", Content.STATUS_ENABLED);
        return params;
    }

    @Override
    protected Map<String, Boolean> getSortMap(HttpServletRequest request) {
        Map<String, Boolean> sortMap = Maps.newLinkedHashMap();
        sortMap.put("pubDate", false);
        return sortMap;
    }
}

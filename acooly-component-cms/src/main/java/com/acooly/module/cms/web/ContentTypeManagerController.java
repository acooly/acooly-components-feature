package com.acooly.module.cms.web;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonListResult;
import com.acooly.core.utils.mapper.BeanMapper;
import com.acooly.module.cms.domain.ContentType;
import com.acooly.module.cms.service.ContentTypeService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 内容类型管理 控制器
 *
 * @author zhangpu
 */
@Controller
@RequestMapping(value = "/manage/module/cms/contentType")
public class ContentTypeManagerController
        extends AbstractJQueryEntityController<ContentType, ContentTypeService> {

    @Autowired
    private ContentTypeService contentTypeService;

    @RequestMapping(value = "loadTree")
    @ResponseBody
    public JsonListResult<Map<String, Object>> loadTree(
            HttpServletRequest request, HttpServletResponse response) {

        JsonListResult<Map<String, Object>> result = new JsonListResult<Map<String, Object>>();
        try {
            result.appendData(referenceData(request));
            String parentId = request.getParameter("id");
            List<ContentType> entities = contentTypeService.getLevel(parentId);
            List<Map<String, Object>> rows = Lists.newLinkedList();
            if (entities != null) {
                String[] properties = new String[]{"id", "code", "name"};
                for (ContentType contentType : entities) {
                    Map<String, Object> row = BeanMapper.deepMap(contentType, properties);
                    row.put(
                            "parentId", contentType.getParent() != null ? contentType.getParent().getId() : 0);
                    row.put(
                            "state",
                            contentType.getChildren() != null && contentType.getChildren().size() > 0
                                    ? "closed"
                                    : "open");
                    row.put("comments", contentType.getComments());
                    rows.add(row);
                }
            }
            result.setTotal((long) entities.size());
            result.setRows(rows);
        } catch (Exception e) {
            handleException(result, "列表查询", e);
        }
        return result;
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {

        model.put("parent", getParent(request));
    }

    @Override
    protected ContentType onSave(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model,
            ContentType entity,
            boolean isCreate)
            throws Exception {

        entity.setParent(getParent(request));
        return entity;
    }

    private ContentType getParent(HttpServletRequest request) {

        String parentId = request.getParameter("parentId");
        if (StringUtils.isNotBlank(parentId)) {
            return contentTypeService.get(Long.valueOf(parentId));
        } else {
            return null;
        }
    }
}

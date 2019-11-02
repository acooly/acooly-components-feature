package com.acooly.module.security.web;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.core.common.web.support.JsonListResult;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Dates;
import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.Strings;
import com.acooly.module.security.config.FrameworkPropertiesHolder;
import com.acooly.module.security.config.SecurityProperties;
import com.acooly.module.security.domain.Resource;
import com.acooly.module.security.dto.ResourceNode;
import com.acooly.module.security.service.ResourceService;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.AbstractFileResolvingResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.ServletContextResource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping(value = "/manage/system/resource")
@ConditionalOnProperty(
        value = SecurityProperties.PREFIX + ".shiro.auth.enable",
        matchIfMissing = true
)
public class ResourceController extends AbstractJsonEntityController<Resource, ResourceService> {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
    private Set<String> icons = null;
    @Autowired
    protected ResourceService resourceService;

    /**
     * 从资源中load icon资源图片的样式class
     *
     * @return
     */
    private Set<String> loadIcon(HttpServletRequest request) {
        Set<String> icons = Sets.newLinkedHashSet();
        // 从CSS资源文件分析读取
        InputStream in = null;
        try {
            // load for custom
            AbstractFileResolvingResource resource = loadResource(request, "static/manage/assert/style/icon.css");
            if (resource.exists() && resource.isReadable()) {
                in = resource.getInputStream();
                List<String> lines = IOUtils.readLines(in, "UTF-8");
                String icon = null;
                for (String line : lines) {
                    icon = getIcon(line);
                    if (Strings.isNotBlank(icon)) {
                        icons.add(icon);
                    }
                }
            }

            // load for awesome
            resource = loadResource(request, "static/manage/assert/plugin/awesome/4.7.0/css/font-awesome.css");
            if (resource.exists() && resource.isReadable()) {
                in = resource.getInputStream();
                List<String> lines = IOUtils.readLines(in, "UTF-8");
                String icon = null;
                String temp[] = null;
                for (String line : lines) {
                    if (Strings.startsWith(line, ".fa-") &&
                            Strings.contains(line, ":before")) {
                        temp = Strings.split(line, ":");
                        if (temp.length > 0) {
                            icon = Strings.removeStart(temp[0], ".");
                            icons.add(icon);
                        }
                    }
                }
            }

        } catch (Exception e) {
            logger.warn("从CSS资源文件加载图标失败:", e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    //ig
                }
            }
        }
        return icons;
    }


    private AbstractFileResolvingResource loadResource(HttpServletRequest request, String path) {
        AbstractFileResolvingResource resource = new ClassPathResource(path);
        if (!resource.exists()) {
            resource = new ServletContextResource(request.getSession().getServletContext(), Strings.removeStart(path, "static"));
        }
        return resource;
    }

    private static String getIcon(String iconClassDef) {
        String temp = Strings.trimToEmpty(iconClassDef);
        if (Strings.isBlank(temp) || temp.indexOf(".icons-resource") == -1) {
            return null;
        }
        temp = Strings.substringAfter(temp, ".");
        if (Strings.contains(temp, "{")) {
            temp = Strings.substringBefore(temp, "{");
        }
        temp = Strings.trimToEmpty(temp);
        return temp;
    }

    @Override
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        if (icons == null) {
            icons = loadIcon(request);
        }
        return super.index(request, response, model);
    }

    @Override
    protected Map<String, Boolean> getSortMap(HttpServletRequest request) {
        Map<String, Boolean> sortMap = Maps.newLinkedHashMap();
        sortMap.put("menu.orderTime", false);
        sortMap.put("orderTime", false);
        return sortMap;
    }

    @RequestMapping(value = {"listAllJson"})
    @ResponseBody
    public JsonListResult<ResourceNode> listAllJson(
            HttpServletRequest request, HttpServletResponse response) {
        JsonListResult<ResourceNode> result = new JsonListResult<ResourceNode>();
        try {
            result.appendData(referenceData(request));
            List<ResourceNode> resources = resourceService.getAllResourceNode();
            result.setTotal(Long.valueOf(resources.size()));
            result.setRows(resources);
        } catch (Exception e) {
            handleException(result, "查询所有数据", e);
        }
        return result;
    }

    @RequestMapping(value = "move")
    @ResponseBody
    public JsonResult move(HttpServletRequest request, HttpServletResponse response) {
        JsonResult result = new JsonResult();
        String moveType = request.getParameter("moveType");
        String sourceId = request.getParameter("sourceId");
        String targetId = request.getParameter("targetId");
        try {
            Resource source = resourceService.get(Long.valueOf(sourceId));
            Resource target = resourceService.get(Long.valueOf(targetId));
            if ("inner".equals(moveType)) {
                source.setParent(target);
            } else if ("prev".equals(moveType)) {
                source.setOrderTime(Dates.addDate(target.getOrderTime(), 1000));
                // 不同级
                if (source.getParent() != null
                        && target.getParent() != null
                        && !source.getParent().getId().equals(target.getParent().getId())) {
                    source.setParent(target.getParent());
                }
            } else if ("next".equals(moveType)) {
                source.setOrderTime(Dates.addDate(target.getOrderTime(), -1000));
                // 不同级
                if (source.getParent() != null
                        && target.getParent() != null
                        && !source.getParent().getId().equals(target.getParent().getId())) {
                    source.setParent(target.getParent());
                }
            }
            resourceService.save(source);
        } catch (Exception e) {
            handleException(result, "移动[" + moveType + "]", e);
        }
        return result;
    }

    @Override
    protected void onRemove(
            HttpServletRequest request, HttpServletResponse response, Model model, Serializable... ids)
            throws Exception {
        Resource role = null;
        for (Serializable id : ids) {
            role = getEntityService().get(id);
            if (role != null && Collections3.isNotEmpty(role.getRoles())) {
                throw new RuntimeException("资源已分配给角色，不能直接删除。");
            }
        }
    }

    @Override
    protected Resource onSave(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model,
            Resource entity,
            boolean isCreate)
            throws Exception {
        String parentId = request.getParameter("parentId");
        if (StringUtils.isNotBlank(parentId)) {
            entity.setParent(resourceService.get(Long.valueOf(parentId)));
        }

        String customIcon = Servlets.getParameter(request,"customIcon");
        if(Strings.isNotBlank(customIcon)){
            entity.setIcon(customIcon);
        }
        return entity;
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allShowStates", FrameworkPropertiesHolder.get().getShowStates());
        model.put("allShowModes", FrameworkPropertiesHolder.get().getShowModes());
        model.put("allTypes", FrameworkPropertiesHolder.get().getResourceTypes());
        model.put("allIcons", icons);
        model.put("menuId", request.getParameter("menuId"));
    }
}

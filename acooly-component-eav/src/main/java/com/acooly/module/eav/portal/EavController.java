package com.acooly.module.eav.portal;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.type.DBMap;
import com.acooly.core.common.view.ViewResult;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.common.web.support.JsonListResult;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Assert;
import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Servlets;
import com.acooly.core.utils.Strings;
import com.acooly.module.eav.dto.EavSchemeDto;
import com.acooly.module.eav.entity.EavAttribute;
import com.acooly.module.eav.entity.EavEntity;
import com.acooly.module.eav.enums.AttributeFormatEnum;
import com.acooly.module.eav.enums.AttributePermissionEnum;
import com.acooly.module.eav.enums.AttributeTypeEnum;
import com.acooly.module.eav.service.EavAttributeEntityService;
import com.acooly.module.eav.service.EavEntityEntityService;
import com.acooly.module.eav.service.EavSchemeEntityService;
import com.acooly.module.eav.service.impl.EavEntityService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qiuboboy@qq.com
 * @author zhangpu
 * @date 2018-06-26 21:28
 */
@Profile("!online")
@RestController
@RequestMapping(value = "/eav")
public class EavController extends AbstractJQueryEntityController {

    @Autowired
    private EavEntityEntityService eavEntityEntityService;
    @Autowired
    private EavEntityService eavEntityService;
    @Autowired
    private EavSchemeEntityService eavSchemeEntityService;
    @Autowired
    private EavAttributeEntityService eavAttributeEntityService;

    {
        allowMapping = "";
    }

    /**
     * 查询单个实体
     */
    @RequestMapping("/entity/get/{id}")
    public JsonEntityResult getEavEntityByPath(@PathVariable("id") Long id) {
        JsonEntityResult result = new JsonEntityResult();
        try {
            if (id == null) {
                throw new RuntimeException("实体ID不能为空");
            }
            result.setEntity(eavEntityService.loadEavEntity(id));
        } catch (Exception e) {
            handleException(result, "查看", e);
        }
        return result;
    }

    @RequestMapping("/entity/get")
    public JsonEntityResult getEavEntityById(Long id) {
        return getEavEntityByPath(id);
    }


    @RequestMapping("/entity/delete")
    public JsonResult getEavEntityByPath(HttpServletRequest request) {
        JsonResult result = new JsonResult();
        try {
            Serializable[] ids = this.getRequestIds(request);
            if (ids != null && ids.length != 0) {
                if (ids.length == 1) {
                    eavEntityEntityService.removeById(ids[0]);
                } else {
                    eavEntityEntityService.removes(ids);
                }
            } else {
                throw new IllegalArgumentException("请求参数中没有指定需要删除的实体Id");
            }
        } catch (Exception e) {
            handleException(result, "查看", e);
        }
        return result;
    }

    /**
     * 创建实体
     * <p>
     * /eav/createEavEntity.json?schemeId=2&color=白色&cpu=intel&os=windows&cputype=第五代智能英特尔酷睿i5处理器&touchbar=true&publishDate=2018-01-01 01:01:01&cpuCore=2
     */
    @RequestMapping("/entity/save")
    public JsonEntityResult createEavEntity(HttpServletRequest request) {
        JsonEntityResult result = new JsonEntityResult();
        try {
            Long schemeId = getSchemeId(request);
            Map<String, String> parameters = Servlets.getParameters(request);
            EavEntity eavEntity = eavEntityService.save(schemeId, parameters);
            result.setEntity(eavEntity);
            result.appendData(referenceDataScheme(schemeId));
        } catch (Exception e) {
            handleException(result, "添加", e);
        }
        return result;
    }


    @RequestMapping("/entity/update")
    public JsonEntityResult updateEavEntity(HttpServletRequest request) {
        JsonEntityResult result = new JsonEntityResult();
        try {
            Long schemeId = getSchemeId(request);
            EavEntity eavEntity = eavEntityService.save(schemeId, Servlets.getParameters(request));
            result.setEntity(eavEntity);
            result.appendData(referenceDataScheme(schemeId));
        } catch (Exception e) {
            handleException(result, "编辑", e);
        }
        return result;
    }


    @RequestMapping("/entity/list")
    public JsonListResult getEavEntitys(HttpServletRequest request, Long schemeId) {
        JsonListResult result = new JsonListResult();
        try {
            // search开头的表单,例如：search_Like_name,search_EQ_id
            Map searchParams = getSearchParams(request);
            searchParams.remove("EQ_schemeId");
            searchParams.remove("schemeId");
            if (schemeId == null) {
                schemeId = getSchemeId(request);
            }

            Assert.notNull(schemeId, "方案ID不能为空");
            List list = eavEntityService.query(schemeId, searchParams, getSortMap(request));
            EavSchemeDto eavScheme = eavEntityService.findEavSchemaDto(schemeId);
            result.setRows(convertList(list, eavScheme));
            result.appendData(referenceDataScheme(schemeId));
        } catch (Exception e) {
            handleException(result, "列表", e);
        }
        return result;
    }


    /**
     * 分页查询
     * /eav/getEavEntitysByPage.json?schemeId=2&...
     * <p>
     * 查询条件：EQ_XXXX=2 & LIKE_XXX=0
     * 排序条件：sort=列名 & order=true/false
     *
     * @param schemeId
     */
    @RequestMapping("/entity/query")
    public JsonListResult getEavEntitysByPage(HttpServletRequest request, Long schemeId) {
        JsonListResult result = new JsonListResult();
        try {
            PageInfo pageInfo = getPageInfo(request);
            eavEntityService.query(schemeId, pageInfo, getSearchParams(request), getSortMap(request));
            pageInfoToResult(pageInfo, result);
            EavSchemeDto eavScheme = eavEntityService.findEavSchemaDto(schemeId);
            result.setRows(convertList(pageInfo.getPageResults(), eavScheme));
            result.appendData(referenceDataScheme(schemeId));
        } catch (Exception e) {
            handleException(result, "分页", e);
        }
        return result;
    }


    /**
     * 查询单个方案及子数据
     */
    @RequestMapping("/scheme/get")
    public JsonEntityResult getEavSchema(Long id, HttpServletRequest request) {
        JsonEntityResult result = new JsonEntityResult();
        try {
            result.setEntity(eavEntityService.findEavSchemaDto(id));
            result.appendData(referenceData(request));
        } catch (Exception e) {
            handleException(result, "加载方案", e);
        }
        return result;
    }

    @RequestMapping("/scheme/list")
    public JsonListResult getEavSchemas(HttpServletRequest request) {
        JsonListResult result = new JsonListResult();
        try {
            result.setRows(eavSchemeEntityService.getAll());
            result.appendData(referenceData(request));
        } catch (Exception e) {
            handleException(result, "加载方案列表", e);
        }
        return result;
    }


    /**
     * 增加或者修改属性
     * <p>
     * http://127.0.0.1:8081/eav/addEavEntityValue.json?entityId=1&age=2&sex=male
     */
    @RequestMapping("/addEavEntityValue")
    public ViewResult addEavEntityValue(Long entityId, HttpServletRequest request) {
        Map<String, String> parameters = Servlets.getParameters(request);
        parameters.remove("entityId");
        eavEntityService.addEavEntityValue(entityId, (Map) parameters);
        return ViewResult.success(null);
    }

    /**
     * 生成scheme对应的选项字段的mapping
     *
     * @param schemeId
     * @return
     */
    protected Map referenceDataScheme(Long schemeId) {
        // referenceData
        List<EavAttribute> eavAttributes = eavAttributeEntityService.loadEavAttribute(schemeId);
        Map mapping = Maps.newHashMap();
        for (EavAttribute attribute : eavAttributes) {
            if (attribute.getAttributeType() == AttributeTypeEnum.ENUM) {
                mapping.put("all" + StringUtils.capitalize(attribute.getName()) + "s"
                        , Collections3.extractToMap(attribute.getOptions(), "code", "name"));
            }
        }
        return mapping;
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map model) {
        model.put("formatTypes", AttributeFormatEnum.mapping());
        model.put("showTypes", AttributePermissionEnum.mapping());
        model.put("attributeTypes", AttributeTypeEnum.mapping());
    }

    protected void pageInfoToResult(PageInfo pageInfo, JsonListResult result) {
        result.setTotal(pageInfo.getTotalCount());
        result.setRows(pageInfo.getPageResults());
        result.setHasNext(pageInfo.hasNext());
        result.setPageNo(pageInfo.getCurrentPage());
        result.setPageSize(pageInfo.getCountOfCurrentPage());
    }

    protected Long getSchemeId(HttpServletRequest request) {
        Long schemeId = Servlets.getLongParameter("schemeId");
        if (schemeId != null) {
            return schemeId;
        }
        String querySchemeId = Servlets.getParameter("search_EQ_schemeId");
        if (Strings.isNumeric(querySchemeId)) {
            return Long.valueOf(querySchemeId);
        }
        return null;
    }

    protected List<Map<String, Object>> convertList(List<EavEntity> eavEntities, final EavSchemeDto eavScheme) {
        List<Map<String, Object>> list = Lists.newArrayList();
        eavEntities.forEach(value -> {
            list.add(convertEavEntity(value, eavScheme));
        });
        return list;
    }

    protected Map convertEavEntity(EavEntity eavEntity, EavSchemeDto eavScheme) {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("id", eavEntity.getId());
        map.put("schemeId", eavEntity.getSchemeId());
        map.putAll(sortValue(eavEntity.getValue(), eavScheme));
        map.put("createTime", eavEntity.getCreateTime());
        map.put("updateTime", eavEntity.getUpdateTime());
        return map;
    }

    protected LinkedHashMap sortValue(DBMap dbMap, EavSchemeDto eavSchemeDto) {
        LinkedHashMap map = Maps.newLinkedHashMap();
        for (String key : eavSchemeDto.getAttributes().keySet()) {
            if (dbMap.get(key) != null) {
                map.put(key, dbMap.get(key));
            }
        }
        return map;
    }

}

package com.acooly.module.eav.portal;

import com.acooly.core.common.type.DBMap;
import com.acooly.core.common.view.ViewResult;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.utils.Assert;
import com.acooly.core.utils.Servlets;
import com.acooly.module.eav.dto.EavPageInfo;
import com.acooly.module.eav.entity.EavEntity;
import com.acooly.module.eav.enums.AttributeFormatEnum;
import com.acooly.module.eav.enums.AttributePermissionEnum;
import com.acooly.module.eav.enums.AttributeTypeEnum;
import com.acooly.module.eav.service.EavEntityEntityService;
import com.acooly.module.eav.service.EavSchemeEntityService;
import com.acooly.module.eav.service.impl.EavEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    {
        allowMapping = "";
    }

    /**
     * 查询实体
     */
    @RequestMapping("/getEavEntity")
    public ViewResult getEavEntity(Long id) {
        return ViewResult.success(eavEntityEntityService.get(id));
    }

    @RequestMapping("/getEavEntitys")
    public ViewResult getEavEntitys(HttpServletRequest request, Long schemaId) {
        Map parameters = Servlets.getParameters(request);
        parameters.remove("schemeId");
        return ViewResult.success(eavEntityService.query(schemaId, parameters));
    }

    /**
     * http://127.0.0.1:8081/eav/getEavEntitysByPage.json?schemaId=2&cpuCore=2&touchbar=0&eavPage=1&eavRows=2&eavOrder=id&eavSort=asc
     * <p>
     * 查询条件： cpuCore=2 & touchbar=0
     * 排序条件：eavOrder=id & eavSort=desc
     *
     * @param schemaId
     * @param eavPage  当前页，从1开始
     * @param eavRows  每页数据条数
     * @param eavOrder 排序条件
     * @param eavSort  排序方向：asc,desc
     */
    @RequestMapping("/getEavEntitysByPage")
    public ViewResult getEavEntitysByPage(HttpServletRequest request, Long schemaId, Integer eavPage, Integer eavRows, String eavSort, String eavOrder) {
        Assert.notNull(eavPage);
        Assert.notNull(eavRows);
        Map parameters = Servlets.getParameters(request);
        EavPageInfo pageinfo = new EavPageInfo();
        pageinfo.setCurrentPage(eavPage);
        pageinfo.setCountOfCurrentPage(eavRows);
        pageinfo.setEavOrder(eavOrder);
        pageinfo.setEavSort(eavSort);
        parameters.remove("schemeId");
        parameters.remove("eavPage");
        parameters.remove("eavRows");
        parameters.remove("eavSort");
        parameters.remove("eavOrder");
        eavEntityService.queryByPage(schemaId, parameters, pageinfo);
        return ViewResult.success(pageinfo);
    }

    /**
     * 查询单个方案及子数据
     */
    @RequestMapping("/getEavScheme")
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

    @RequestMapping("/getEavSchemas")
    public ViewResult getEavSchemas() {
        return ViewResult.success(eavSchemeEntityService.getAll());
    }

    /**
     * 创建实体
     * <p>
     * http://127.0.0.1:8081/eav/createEavEntityWithJsonValue.json?schemaId=2&value=%7b%22color%22%3a+%22%E7%99%BD%E8%89%B2%22%2c%22cpu%22%3a+%22intel%22%2c%22os%22%3a+%22windows%22%2c%22cputype%22%3a+%22%E7%AC%AC%E4%BA%94%E4%BB%A3%E6%99%BA%E8%83%BD%E8%8B%B1%E7%89%B9%E5%B0%94%E9%85%B7%E7%9D%BFi5%E5%A4%84%E7%90%86%E5%99%A8%22%2c%22touchbar%22%3a+true%2c%22publishDate%22%3a+%222018-01-01+01%3a01%3a01%22%2c%22cpuCore%22%3a+2%7d
     * <p>
     * json内容为：
     * <p>
     * {
     * "color": "白色",
     * "cpu": "intel",
     * "os": "windows",
     * "cputype": "第五代智能英特尔酷睿i5处理器",
     * "touchbar": 1,
     * "publishDate": "2018-01-01 01:01:01",
     * "cpuCore": 2
     * }
     */
    @RequestMapping("/createEavEntityWithJsonValue")
    public ViewResult createEavEntityWithJsonValue(EavEntity eavEntity) {
        eavEntityEntityService.save(eavEntity);
        return ViewResult.success(eavEntity);
    }

    /**
     * 创建实体
     * <p>
     * http://127.0.0.1:8081/eav/createEavEntity.json?schemaId=2&color=白色&cpu=intel&os=windows&cputype=第五代智能英特尔酷睿i5处理器&touchbar=true&publishDate=2018-01-01 01:01:01&cpuCore=2
     */
    @RequestMapping("/createEavEntity")
    public ViewResult createEavEntity(Long schemaId, HttpServletRequest request) {
        EavEntity eavEntity = new EavEntity();
        eavEntity.setSchemeId(schemaId);
        Map<String, String> parameters = Servlets.getParameters(request);
        parameters.remove("schemeId");
        DBMap dbMap = new DBMap();
        dbMap.putAll(parameters);
        eavEntity.setValue(dbMap);
        eavEntityEntityService.save(eavEntity);
        return ViewResult.success(eavEntity);
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

    @Override
    protected void referenceData(HttpServletRequest request, Map model) {
        model.put("formatTypes", AttributeFormatEnum.mapping());
        model.put("showTypes", AttributePermissionEnum.mapping());
        model.put("attributeTypes", AttributeTypeEnum.mapping());
    }
}

package com.acooly.module.eav.service.impl;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.exception.AppConfigException;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.exception.CommonErrorCodes;
import com.acooly.core.common.type.DBMap;
import com.acooly.core.utils.Assert;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.arithmetic.perm.BitPermissions;
import com.acooly.module.eav.dao.EavEntityDao;
import com.acooly.module.eav.dao.EavEntityDynamicDao;
import com.acooly.module.eav.dao.EavSchemeDao;
import com.acooly.module.eav.dto.EavAttributeInfo;
import com.acooly.module.eav.dto.EavEntityInfo;
import com.acooly.module.eav.dto.EavSchemeDto;
import com.acooly.module.eav.entity.EavAttribute;
import com.acooly.module.eav.entity.EavEntity;
import com.acooly.module.eav.entity.EavScheme;
import com.acooly.module.eav.enums.AttributePermissionEnum;
import com.acooly.module.eav.service.EavAttributeEntityService;
import com.acooly.module.eav.service.EavEntityEntityService;
import com.acooly.module.eav.service.EavSchemeEntityService;
import com.acooly.module.eav.validator.ValueValidatorService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.acooly.module.eav.EavAutoConfig.EAV_REDIS_TOPIC;

/**
 * @author qiuboboy@qq.com
 * @author zhangpu
 * @date 2018-06-27 10:47
 */
@Service
@Slf4j
public class EavEntityService {
    public static final String EAV_ATTRIBUTE_KEY = "eavAttribute:";
    public static final String EAV_SCHEMA_KEY = "eavSchema:";
    public static final String SQL_SORT = "eavSort";
    public static final String SQL_ORDER = "eavOrder";
    @Autowired
    private EavAttributeEntityService eavAttributeEntityService;
    @Autowired
    private EavSchemeEntityService eavSchemeEntityService;
    @Autowired
    private EavSchemeDao eavSchemaDao;
    @Autowired
    private ValueValidatorService valueValidatorService;
    @Autowired
    private EavEntityDao eavEntityDao;
    @Autowired
    private EavEntityEntityService eavEntityEntityService;

    @Autowired
    private EavEntityDynamicDao eavEntityDynamicDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 非动态属性
     */
    private static final List<String> ENTITY_COMMON_ATTRS =
            Lists.newArrayList(new String[]{"id", "createTime", "updateTime", "memo", "schemeName", "schemeTitle"});

    /**
     * 加载单个Scheme
     * 包括所有子对象的定义（缓存）
     *
     * @param id
     * @return
     */
    public EavSchemeDto findEavSchemaDto(Long id) {
        try {
            return schemaDtoCache.get(id);
        } catch (ExecutionException e) {
            throw new AppConfigException(e);
        }
    }


    /**
     * 保存实体
     *
     * @param schemeId
     * @param parameters
     * @return
     */
    public EavEntity save(Long schemeId, Map parameters) {
        Assert.notNull(schemeId,"方案编码不能为空");
        EavScheme eavScheme = eavSchemeEntityService.get(schemeId);
        String id = (String) parameters.get("id");
        parameters.remove("schemeId");
        parameters.remove("id");
        EavEntity entity = null;
        if (!Strings.isNumber(id)) {
            // 新增
            entity = new EavEntity();
            entity.setSchemeId(schemeId);
            entity.setSchemeName(eavScheme.getName());
            entity.setSchemeTitle(eavScheme.getTitle());
            entity.setValue(new DBMap(parameters));
            validate(entity);
            eavEntityEntityService.save(entity);
        } else {
            // 编辑
            entity = eavEntityEntityService.get(Long.valueOf(id));
            entity.getValue().putAll(parameters);
            validate(entity);
            eavEntityEntityService.update(entity);
        }
        return entity;
    }


    /**
     * 加载动态实体信息
     *
     * @param entityId
     * @return
     */
    public EavEntityInfo loadEavEntity(Long entityId) {
        try {
            EavEntity eavEntity = eavEntityEntityService.get(entityId);
            if (eavEntity == null) {
                log.warn("动态实体不存在,entityId:" + entityId);
                throw new BusinessException(CommonErrorCodes.OBJECT_NOT_EXIST, "动态实体不存在,entityId:" + entityId);
            }
            List<EavAttribute> eavAttributes = eavAttributeEntityService.loadEavAttribute(eavEntity.getSchemeId());
            DBMap data = eavEntity.getValue();

            EavEntityInfo eavEntityInfo = new EavEntityInfo();
            eavEntityInfo.setId(eavEntity.getId());
            eavEntityInfo.setSchemeId(eavEntity.getSchemeId());
            eavEntityInfo.setSchemeName(eavEntity.getSchemeName());
            eavEntityInfo.setSchemeTitle(eavEntity.getSchemeTitle());
            eavEntityInfo.setMemo(eavEntity.getMemo());
            eavEntityInfo.setCreateTime(eavEntity.getCreateTime());
            eavEntityInfo.setUpdateTime(eavEntity.getUpdateTime());
            eavEntityInfo.setValue(data);
            eavEntityInfo.setJsonValue(data.toJson());

            for (EavAttribute attribute : eavAttributes) {
                eavEntityInfo.getEavAttributeInfos().add(new EavAttributeInfo(attribute, data.get(attribute.getName())));
            }
            return eavEntityInfo;
        } catch (BusinessException be) {
            throw be;
        } catch (Exception e) {
            throw new BusinessException(CommonErrorCodes.INTERNAL_ERROR, "加载动态实体失败");
        }


    }

    /**
     * 分页查询
     *
     * @param schemeId
     * @param pageInfo
     * @param map
     * @param sortMap
     * @return
     * @throws BusinessException
     */
    public PageInfo<EavEntity> query(Long schemeId, PageInfo<EavEntity> pageInfo, Map<String, Object> map, Map<String, Boolean> sortMap) throws BusinessException {
        Assert.notNull(schemeId, "schemeId不能为空");
        map.put("schemeId", schemeId);
        return eavEntityDynamicDao.query(pageInfo, map, sortMap);
    }


    /**
     * 列表查询
     *
     * @param schemeId
     * @param map
     * @param sortMap
     * @return
     */
    public List<EavEntity> query(Long schemeId, Map<String, Object> map, Map<String, Boolean> sortMap) {
        Assert.notNull(schemeId, "schemaId不能为空");
        map.put("schemeId", schemeId);
        return eavEntityDynamicDao.list(map, sortMap);

    }


    /**
     * 列表查询
     *
     * @param schemeId
     * @param parameters
     * @return
     */
    public List<EavEntity> query(Long schemeId, Map<String, Object> parameters) {
        return query(schemeId, parameters, null);
    }


    public void sendEavAttributeChangeMessage(Serializable id) {
        redisTemplate.convertAndSend(EAV_REDIS_TOPIC, EAV_ATTRIBUTE_KEY + id);
    }

    public void sendEavSchemaChangeMessage(Serializable id) {
        redisTemplate.convertAndSend(EAV_REDIS_TOPIC, EAV_SCHEMA_KEY + id);
    }

    public void invalidateCache(String key) {
        if (key.startsWith(EAV_ATTRIBUTE_KEY)) {
            Long attrId = Long.valueOf(key.replace(EAV_ATTRIBUTE_KEY, ""));
            valueValidatorService.invalidateCacheByEavAttributeId(attrId);
            schemaDtoCache.asMap().forEach((id, eavSchemaDto) -> {
                if (eavSchemaDto.getAttributes().values().stream().anyMatch(eavAttribute -> attrId.equals(eavAttribute.getId()))) {
                    schemaDtoCache.invalidate(eavSchemaDto.getId());
                    log.info("清除schemaDtoCache缓存：{}", eavSchemaDto.getId());
                }
            });
        }
        if (key.startsWith(EAV_SCHEMA_KEY)) {
            Long schemaId = Long.valueOf(key.replace(EAV_SCHEMA_KEY, ""));
            schemaDtoCache.invalidate(schemaId);
            log.info("清除schemaDtoCache缓存：{}", schemaId);

            valueValidatorService.invalidateCacheBySchemaId(schemaId);
        }
    }


    public void validate(EavEntity eavEntity) {
        Assert.notNull(eavEntity);
        Assert.notNull(eavEntity.getSchemeId());
        Assert.notNull(eavEntity.getValue());
        EavSchemeDto eavSchemaDto = findEavSchemaDto(eavEntity.getSchemeId());
        DBMap dbMap = new DBMap();
        for (EavAttribute attribute : eavSchemaDto.getAttributes().values()) {
            if (!BitPermissions.hasPerm(attribute.getShowType(), AttributePermissionEnum.CREATE.getCode())
                    && !BitPermissions.hasPerm(attribute.getShowType(), AttributePermissionEnum.UPDATE.getCode())) {
                continue;
            }
            String attrName = attribute.getName();
            Object atrrValue = eavEntity.getValue().get(attrName);
            dbMap.put(attrName, valueValidatorService.validate(attribute, atrrValue));
        }
        eavEntity.setValue(dbMap);
    }

    public void addEavEntityValue(Long entityId, Map<String, Object> parameters) {
        Assert.notNull(entityId);
        Assert.notNull(parameters);
        EavEntity eavEntity = eavEntityDao.get(entityId);
        Assert.notNull(eavEntity);
        parametersConvertAndCheck(eavEntity.getSchemeId(), parameters);
        int valueSize = parameters.size();

        String sql = "UPDATE eav_entity SET value = JSON_SET(value," + StringUtils.repeat("?", ",", valueSize * 2) + ") WHERE id = ?";
        jdbcTemplate.update(sql, ps -> {
            int idx = 1;
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                ps.setString(idx, "$." + entry.getKey());
                idx++;
                if (entry.getValue() instanceof Boolean) {
                    ps.setBoolean(idx, (Boolean) entry.getValue());
                } else {
                    ps.setObject(idx, entry.getValue());
                }
                idx++;
            }
            ps.setLong(idx, entityId);
        });
    }


    private void parametersConvertAndCheck(Long schemaId, Map<String, Object> parameters) {
        EavSchemeDto eavSchemaDto = findEavSchemaDto(schemaId);
        Assert.notNull(eavSchemaDto);
        parameters.forEach((s, o) -> {
            EavAttribute eavAttribute = eavSchemaDto.getAttributes().get(s);
            if (eavAttribute == null) {
                throw new BusinessException("属性[" + s + "]不存在", false);
            }
            parameters.put(s, valueValidatorService.validate(eavAttribute, o));
        });
    }

    private EavSchemeDto doFindEavSchemaDto(Long id) {
        EavScheme eavSchema = eavSchemaDao.get(id);
        if (eavSchema != null) {
            EavSchemeDto eavSchemaDto = eavSchema.to(EavSchemeDto.class);
            List<EavAttribute> attributes = eavAttributeEntityService.loadEavAttribute(id);
            Map<String, EavAttribute> attributeMap = Maps.newLinkedHashMapWithExpectedSize(attributes.size());
            attributes.forEach(eavAttribute -> attributeMap.put(eavAttribute.getName(), eavAttribute));
            eavSchemaDto.setAttributes(attributeMap);
            return eavSchemaDto;
        }
        throw new BusinessException("schemeId=" + id + "不存在", false);
    }


    private LoadingCache<Long, EavSchemeDto> schemaDtoCache = CacheBuilder.newBuilder().build(new CacheLoader<Long, EavSchemeDto>() {
        @Override
        public EavSchemeDto load(Long key) {
            return doFindEavSchemaDto(key);
        }
    });
}

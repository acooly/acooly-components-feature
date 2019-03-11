package com.acooly.module.eav.service.impl;

import com.acooly.core.common.dao.support.SearchFilter;
import com.acooly.core.common.exception.AppConfigException;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.exception.CommonErrorCodes;
import com.acooly.core.common.type.DBMap;
import com.acooly.core.utils.Assert;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.arithmetic.perm.BitPermissions;
import com.acooly.module.eav.dao.EavAttributeDao;
import com.acooly.module.eav.dao.EavEntityDao;
import com.acooly.module.eav.dao.EavSchemeDao;
import com.acooly.module.eav.dto.EavAttributeInfo;
import com.acooly.module.eav.dto.EavEntityInfo;
import com.acooly.module.eav.dto.EavPageInfo;
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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.acooly.module.eav.EavAutoConfig.EAV_REDIS_TOPIC;

/**
 * @author qiuboboy@qq.com
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
    private EavAttributeDao eavAttributeDao;
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
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


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
            entity.setValue(new DBMap(parameters));
            eavEntityEntityService.update(entity);
        }
        return entity;
    }


    public EavEntityInfo loadEavEntity(Long entityId) {
        try {
            EavEntity eavEntity = eavEntityEntityService.get(entityId);
            List<EavAttribute> eavAttributes = eavAttributeEntityService.loadEavAttribute(eavEntity.getSchemeId());
            DBMap data = eavEntity.getValue();

            EavEntityInfo eavEntityInfo = new EavEntityInfo();
            eavEntityInfo.setId(eavEntity.getId());
            eavEntityInfo.setSchemeId(eavEntity.getSchemeId());
            eavEntityInfo.setSchemeName(eavEntity.getSchemeName());
            eavEntityInfo.setSchemeTitle(eavEntity.getSchemeTitle());
            eavEntityInfo.setMemo(eavEntity.getMemo());
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
     * 列表查询
     *
     * @param schemaId
     * @param parameters
     * @return
     */
    public List<EavEntity> query(Long schemaId, Map<String, Object> parameters) {
        Assert.notNull(schemaId, "schemaId不能为空");
        if (parameters == null) {
            parameters = Collections.emptyMap();
        }
        parametersConvertAndCheck(schemaId, parameters);
        StringBuilder sql = new StringBuilder("SELECT * FROM eav_entity WHERE scheme_id= ?");
        List<Object> args = Lists.newArrayList();
        args.add(schemaId);
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            sql.append(" and  value->'$.").append(entry.getKey()).append("'=?");
            args.add(entry.getValue());
        }
        return executeSql(sql, args);
    }

    /**
     * 分页查询
     *
     * @param schemeId
     * @param parameters
     * @param pageinfo
     */
    public void queryByPage(Long schemeId, Map<String, Object> parameters, EavPageInfo pageinfo) {
        Assert.notNull(schemeId, "schemeId不能为空");
        if (parameters == null) {
            parameters = Collections.emptyMap();
        }
//        parametersConvertAndCheck(schemeId, parameters);
        StringBuilder sql = new StringBuilder("SELECT * FROM eav_entity WHERE scheme_id= ?");
        List<Object> args = Lists.newArrayList();
        args.add(schemeId);
        parameters.remove("EQ_schemeId");
        List<SearchFilter> searchFilters = SearchFilter.parse(parameters);
        for (SearchFilter searchFilter : searchFilters) {
            sql.append(" and  value->'$.").append(searchFilter.fieldName).append("'").append(operatorParse(searchFilter));
//            args.add(searchFilter.value);
        }

//        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
//            sql.append(" and  value->'$.").append(entry.getKey()).append("'=?");
//            args.add(entry.getValue());
//        }
        int idx = pageinfo.getCountOfCurrentPage() * (pageinfo.getCurrentPage() - 1);
        if (pageinfo.getEavSort() != null) {
            sql.append(" order by  ").append(pageinfo.getEavSort()).append(" ");
            sql.append(pageinfo.getEavOrder() == null ? "" : pageinfo.getEavOrder());
        }
        sql.append(" LIMIT ");
        sql.append(idx);
        sql.append(",");
        sql.append(pageinfo.getCountOfCurrentPage());
        pageinfo.setPageResults(executeSql(sql, args));
    }

    private String operatorParse(SearchFilter searchFilter) {
        StringBuilder sb = new StringBuilder();
        Object value = searchFilter.value;
        switch (searchFilter.operator) {
            case EQ:
                if (value instanceof String) {
                    sb.append(" = '" + value + "'");
                } else {
                    sb.append(" = " + value);
                }
                break;
            case NEQ:
                if (value instanceof String) {
                    sb.append(" != '" + value + "'");
                } else {
                    sb.append(" != " + value);
                }
                break;
            case LIKE:
                sb.append(" like '%" + value + "%'");
                break;
            case LLIKE:
                sb.append(" like '%" + value + "'");
                break;
            case RLIKE:
                sb.append(" like '" + value + "%'");
                break;
            case GT:
                if (value instanceof String) {
                    sb.append(" > '" + value + "'");
                } else {
                    sb.append(" > " + value);
                }
                break;
            case LT:
                if (value instanceof String) {
                    sb.append(" < '" + value + "'");
                } else {
                    sb.append(" < " + value);
                }
                break;
            case GTE:
                if (value instanceof String) {
                    sb.append(" >= '" + value + "'");
                } else {
                    sb.append(" >= " + value);
                }
                break;
            case LTE:
                if (value instanceof String) {
                    sb.append(" <= '" + value + "'");
                } else {
                    sb.append(" <= " + value);
                }
                break;
            case NULL:
                sb.append(" is null ");
                break;
            case NOTNULL:
                sb.append(" is not null ");
                break;
        }
        return sb.toString();
    }

    private List<EavEntity> executeSql(StringBuilder sql, List<Object> args) {
        return jdbcTemplate.query(sql.toString(), args.toArray(), (rs, rowNum) -> {
            EavEntity eavEntity = new EavEntity();
            eavEntity.setId(rs.getLong("id"));
            eavEntity.setSchemeId(rs.getLong("scheme_id"));
            eavEntity.setSchemeName(rs.getString("scheme_name"));
            eavEntity.setValue(DBMap.fromJson(rs.getString("value")));
            eavEntity.setCreateTime(rs.getDate("create_time"));
            eavEntity.setUpdateTime(rs.getDate("update_time"));
            return eavEntity;
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

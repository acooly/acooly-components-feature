package com.acooly.module.eav.service.impl;

import com.acooly.core.common.exception.AppConfigException;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.type.DBMap;
import com.acooly.core.utils.Assert;
import com.acooly.module.eav.dao.EavAttributeDao;
import com.acooly.module.eav.dao.EavEntityDao;
import com.acooly.module.eav.dao.EavSchemaDao;
import com.acooly.module.eav.dto.EavPageInfo;
import com.acooly.module.eav.dto.EavSchemaDto;
import com.acooly.module.eav.entity.EavAttribute;
import com.acooly.module.eav.entity.EavEntity;
import com.acooly.module.eav.entity.EavSchema;
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
    private EavSchemaDao eavSchemaDao;
    @Autowired
    private ValueValidatorService valueValidatorService;
    @Autowired
    private EavEntityDao eavEntityDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private LoadingCache<Long, EavSchemaDto> schemaDtoCache = CacheBuilder.newBuilder().build(new CacheLoader<Long, EavSchemaDto>() {
        @Override
        public EavSchemaDto load(Long key) {
            return doFindEavSchemaDto(key);
        }
    });

    public EavSchemaDto findEavSchemaDto(Long id) {
        try {
            return schemaDtoCache.get(id);
        } catch (ExecutionException e) {
            throw new AppConfigException(e);
        }
    }

    private EavSchemaDto doFindEavSchemaDto(Long id) {
        EavSchema eavSchema = eavSchemaDao.get(id);
        if (eavSchema != null) {
            EavSchemaDto eavSchemaDto = eavSchema.to(EavSchemaDto.class);
            List<EavAttribute> attributes = eavAttributeDao.findAttributesBySchemaId(id);
            Map<String, EavAttribute> attributeMap = Maps.newHashMapWithExpectedSize(attributes.size());
            attributes.forEach(eavAttribute -> attributeMap.put(eavAttribute.getName(), eavAttribute));
            eavSchemaDto.setAttributes(attributeMap);
            return eavSchemaDto;
        }
        throw new BusinessException("schemaId=" + id + "不存在", false);
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
        Assert.notNull(eavEntity.getSchemaId());
        Assert.notNull(eavEntity.getValue());
        EavSchemaDto eavSchemaDto = findEavSchemaDto(eavEntity.getSchemaId());
        for (EavAttribute attribute : eavSchemaDto.getAttributes().values()) {
            String attrName = attribute.getName();
            Object atrrValue = eavEntity.getValue().get(attrName);
            eavEntity.getValue().put(attrName, valueValidatorService.validate(attribute, atrrValue));
        }
    }

    public void addEavEntityValue(Long entityId, Map<String, Object> parameters) {
        Assert.notNull(entityId);
        Assert.notNull(parameters);
        EavEntity eavEntity = eavEntityDao.get(entityId);
        Assert.notNull(eavEntity);
        parametersConvertAndCheck(eavEntity.getSchemaId(), parameters);
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

    public List<EavEntity> query(Long schemaId, Map<String, Object> parameters) {
        Assert.notNull(schemaId, "schemaId不能为空");
        if (parameters == null) {
            parameters = Collections.emptyMap();
        }
        parametersConvertAndCheck(schemaId, parameters);
        StringBuilder sql = new StringBuilder("SELECT * FROM eav_entity WHERE schema_id= ?");
        List<Object> args = Lists.newArrayList();
        args.add(schemaId);
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            sql.append(" and  value->'$.").append(entry.getKey()).append("'=?");
            args.add(entry.getValue());
        }
        return executeSql(sql, args);
    }


    public void queryByPage(Long schemaId, Map<String, Object> parameters, EavPageInfo pageinfo) {
        Assert.notNull(schemaId, "schemaId不能为空");
        if (parameters == null) {
            parameters = Collections.emptyMap();
        }
        parametersConvertAndCheck(schemaId, parameters);
        StringBuilder sql = new StringBuilder("SELECT * FROM eav_entity WHERE schema_id= ?");
        List<Object> args = Lists.newArrayList();
        args.add(schemaId);
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            sql.append(" and  value->'$.").append(entry.getKey()).append("'=?");
            args.add(entry.getValue());
        }
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

    private List<EavEntity> executeSql(StringBuilder sql, List<Object> args) {
        return jdbcTemplate.query(sql.toString(), args.toArray(), (rs, rowNum) -> {
            EavEntity eavEntity = new EavEntity();
            eavEntity.setId(rs.getLong("id"));
            eavEntity.setSchemaId(rs.getLong("schema_id"));
            eavEntity.setValue(DBMap.fromJson(rs.getString("value")));
            eavEntity.setCreateTime(rs.getDate("create_time"));
            eavEntity.setUpdateTime(rs.getDate("update_time"));
            return eavEntity;
        });
    }

    private void parametersConvertAndCheck(Long schemaId, Map<String, Object> parameters) {
        EavSchemaDto eavSchemaDto = findEavSchemaDto(schemaId);
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
}

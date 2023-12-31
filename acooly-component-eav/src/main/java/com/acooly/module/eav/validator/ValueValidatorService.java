package com.acooly.module.eav.validator;

import com.acooly.core.common.exception.AppConfigException;
import com.acooly.module.eav.entity.EavAttribute;
import com.acooly.module.eav.enums.AttributeTypeEnum;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Constructor;
import java.util.EnumMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

/**
 * @author qiuboboy@qq.com
 * @date 2018-06-27 16:35
 */
@Service
@Slf4j
public class ValueValidatorService implements InitializingBean {

    private Cache<Long, ValueValidator> valueValidatorCache = CacheBuilder.newBuilder().build();
    private EnumMap<AttributeTypeEnum, Class<? extends ValueValidator>> classEnumMap;


    public Object validate(EavAttribute eavAttribute, Object value) {
        ValueValidator valueValidator = getValueValidator(eavAttribute);
        return valueValidator.apply(value);
    }

    private ValueValidator getValueValidator(EavAttribute eavAttribute) {
        try {
            // change by zhangpu 2019-6-14
            // 如果是枚举类型，因为枚举的Option值可能改变，所以不使用Validator缓存
            // 后期可改造ValueValidator的结构实现进行优化
            if (eavAttribute.getAttributeType() == AttributeTypeEnum.ENUM) {
                valueValidatorCache.invalidate(eavAttribute.getId());
            }
            return valueValidatorCache.get(eavAttribute.getId(), () -> {
                ValueValidator valueValidator;
                Class<? extends ValueValidator> clazz = classEnumMap.get(eavAttribute.getAttributeType());
                Constructor<? extends ValueValidator> constructor = ClassUtils.getConstructorIfAvailable(clazz, EavAttribute.class);
                try {
                    valueValidator = constructor.newInstance(eavAttribute);
                } catch (Exception e) {
                    throw new AppConfigException(e);
                }
                return valueValidator;
            });
        } catch (ExecutionException e) {
            throw new AppConfigException(e);
        }
    }

    public void invalidateCacheByEavAttributeId(Long id) {
        valueValidatorCache.invalidate(id);
        log.info("清除valueValidatorCache缓存：{}", id);

    }

    public void invalidateCacheBySchemaId(Long id) {
        ConcurrentMap<Long, ValueValidator> map = valueValidatorCache.asMap();
        for (ValueValidator valueValidator : map.values()) {
            if (valueValidator.getEavAttribute().getSchemeId().equals(id)) {
                invalidateCacheByEavAttributeId(valueValidator.getEavAttribute().getId());
            }
        }
    }

    @Override
    public void afterPropertiesSet() {
        classEnumMap = Maps.newEnumMap(AttributeTypeEnum.class);
        classEnumMap.put(AttributeTypeEnum.NUMBER_DECIMAL, DoubleValueValidator.class);
        classEnumMap.put(AttributeTypeEnum.NUMBER_INTEGER, LongValueValidator.class);
        classEnumMap.put(AttributeTypeEnum.NUMBER_MONEY, MoneyValueValidator.class);
        classEnumMap.put(AttributeTypeEnum.DATE, DateValueValidator.class);
        classEnumMap.put(AttributeTypeEnum.ENUM, EnumValueValidator.class);
        classEnumMap.put(AttributeTypeEnum.STRING, StringValueValidator.class);
    }
}

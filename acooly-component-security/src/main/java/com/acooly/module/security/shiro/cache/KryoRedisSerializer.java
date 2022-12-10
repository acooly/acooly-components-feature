/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-12-07 15:29
 */
package com.acooly.module.security.shiro.cache;

import com.acooly.core.utils.kryos.Kryos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * @author zhangpu
 * @date 2022-12-07 15:29
 */
@Slf4j
public class KryoRedisSerializer implements RedisSerializer<Object> {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if (o == null) {
            return new byte[0];
        }
        return Kryos.serialize(o);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        return Kryos.deserialize(bytes);
    }
}

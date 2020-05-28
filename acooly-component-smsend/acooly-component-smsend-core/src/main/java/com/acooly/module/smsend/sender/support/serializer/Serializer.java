package com.acooly.module.smsend.sender.support.serializer;

/**
 * @author shuijing
 */
public interface Serializer<T> {
    String serialize(T obj, String encoding) throws Exception;
}

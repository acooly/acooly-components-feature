package com.acooly.module.config;

import com.acooly.core.common.boot.Apps;
import com.acooly.core.common.exception.AppConfigException;
import com.acooly.module.config.service.impl.AppConfigManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.function.Function;

/**
 * 从数据库中读取配置，首先从本地缓存读取，然后从redis读取，最后从数据库中读取。
 *
 * @author qiuboboy@qq.com
 * @date 2018-06-20 04:30
 */
public class Configs {
    private static AppConfigManager configManager;
    private static ObjectMapper objectMapper;

    /**
     * 读取配置
     *
     * @param name 配置项名称
     * @return 值
     */
    public static String get(String name) {
        init();
        return configManager.get(name);
    }


    /**
     * 读取配置
     *
     * @param name 配置项名称
     * @return 值
     */
    public static Integer getInt(String name) {
        return get(name, Integer::parseInt);
    }

    /**
     * 读取配置
     *
     * @param name 配置项名称
     * @return 值
     */
    public static Long getLong(String name) {
        return get(name, Long::parseLong);
    }

    /**
     * 读取配置
     *
     * @param name 配置项名称
     * @return 值
     */
    public static Boolean getBoolean(String name) {
        return get(name, Boolean::parseBoolean);
    }


    /**
     * 读取配置，并转换为指定的类型
     *
     * @param name  配置项名称
     * @param clazz 转换为的类型
     * @param <T>   返回类型
     * @return 值
     */
    public static <T> T get(String name, Class<T> clazz) {
        init();
        return configManager.get(name, clazz);
    }

    /**
     * 读取配置
     *
     * @param name     配置项名称
     * @param function 转换方法
     * @param <T>      返回类型
     * @return 值
     */
    public static <T> T get(String name, Function<String, T> function) {
        String value = get(name);
        if (value == null) {
            return null;
        }
        return function.apply(value);
    }

    /**
     * 读取json并转换为对象
     *
     * @param name  配置项名称
     * @param clazz 转换为的类型
     * @param <T>   返回类型
     * @return 值
     */
    public static <T> T getJsonObject(String name, Class<T> clazz) {
        return get(name, value -> {
            try {
                return objectMapper.readValue(value, clazz);
            } catch (IOException e) {
                throw new AppConfigException(e);
            }
        });
    }

    private static void init() {
        if (configManager == null) {
            configManager = Apps.getApplicationContext().getBean(AppConfigManager.class);
        }
        if (objectMapper == null) {
            objectMapper = Apps.getApplicationContext().getBean(ObjectMapper.class);
        }
    }
}

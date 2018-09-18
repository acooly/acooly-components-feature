package com.acooly.module.sms.sender.support;

import com.acooly.module.sms.sender.support.serializer.BooleanSerializer;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author shuijing
 */
public class BaseSmsSendVo {

    private static Gson gson = null;

    public static synchronized Gson getGson() {
        if (gson == null) {
            GsonBuilder b = new GsonBuilder();
            b.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);
            BooleanSerializer serializer = new BooleanSerializer();
            b.registerTypeAdapter(Boolean.class, serializer);
            b.registerTypeAdapter(boolean.class, serializer);
            gson = b.create();
        }
        return gson;
    }
}

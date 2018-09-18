package com.acooly.module.sms.sender.support.serializer;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * @author shuijing
 */
public class BooleanSerializer implements JsonSerializer<Boolean>, JsonDeserializer<Boolean> {

    @Override
    public JsonElement serialize(Boolean arg0, Type arg1, JsonSerializationContext arg2) {
        return new JsonPrimitive(arg0 ? 1 : 0);
    }

    @Override
    public Boolean deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
            throws JsonParseException {
        return arg0.getAsInt() == 1;
    }
}

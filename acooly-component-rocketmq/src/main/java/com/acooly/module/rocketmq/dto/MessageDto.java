package com.acooly.module.rocketmq.dto;

import com.acooly.core.utils.ToString;
import com.acooly.core.utils.validate.Validators;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@Setter
public class MessageDto {
    /**
     * gid
     */
    @NotNull
    @Length(min = 10, max = 64)
    private String gid;
    /**
     * 队列主题名字
     */
    @NotNull
    @Length(min = 1, max = 64)
    private String topic;
    /**
     * 消息唯一id
     */
    @NotNull
    @Length(min = 1, max = 64)
    private String msgId;
    /**
     * 消息tag
     */
    private String tags;
    /**
     * 数据字段，key-value形式的数据存储，为了防止不兼容性的情况出现(消息接收方没有消息发送方依赖类),请使用基础对象
     */
    @NotNull
    private Map<String, Object> data;

    /**
     * 添加键值对
     */
    public void param(String key, Object value) {
        if (data == null) {
            data = Maps.newHashMap();
        }
        data.put(key, value);
    }

    public void check() {
        Validators.assertJSR303(this);
    }

    @Override
    public String toString() {
        return ToString.toString(this);
    }
}

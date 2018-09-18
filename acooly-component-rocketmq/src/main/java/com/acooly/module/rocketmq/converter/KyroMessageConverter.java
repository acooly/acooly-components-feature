package com.acooly.module.rocketmq.converter;

import com.acooly.core.common.exception.AppConfigException;
import com.acooly.core.utils.kryos.Kryos;
import com.acooly.module.rocketmq.dto.MessageDto;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qiubo
 */
@Slf4j
public class KyroMessageConverter implements MessageConverter {
    @Override
    public Message to(MessageDto dto) {
        try {
            byte[] data = Kryos.serialize(dto);
            Message message = new Message();
            message.setTopic(dto.getTopic());
            message.setBody(data);
            message.setKeys(dto.getMsgId());
            message.setTags(dto.getTags());
            return message;
        } catch (Exception e) {
            log.error("kryo序列化对象失败");
            throw new AppConfigException("序列化对象失败", e);
        }
    }

    @Override
    public MessageDto from(MessageExt messageExt) {
        if (messageExt == null) {
            return null;
        }
        try {
            return Kryos.deserialize(messageExt.getBody(), MessageDto.class);
        } catch (Exception e) {
            log.error("kryo反序列化失败.", e);
            throw new AppConfigException("消息格式不兼容,反序列化失败", e);
        }
    }
}

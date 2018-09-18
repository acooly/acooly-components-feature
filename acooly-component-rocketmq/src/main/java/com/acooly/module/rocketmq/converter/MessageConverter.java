package com.acooly.module.rocketmq.converter;

import com.acooly.module.rocketmq.dto.MessageDto;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * @author qiubo
 */
public interface MessageConverter {

    Message to(MessageDto messageDto);

    MessageDto from(MessageExt message);
}

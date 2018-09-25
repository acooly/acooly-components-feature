package com.acooly.core.test.rocketmq;

import com.acooly.core.utils.Ids;
import com.acooly.module.rocketmq.consumer.RocketListener;
import com.acooly.module.rocketmq.dto.MessageDto;
import com.acooly.module.rocketmq.producer.MessageProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mq")
@Slf4j
public class MQController {

    @Autowired(required = false)
    private MessageProducer messageProducer;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public void send() {
        MessageDto messageDto = buildMsg();
        messageDto.setTopic("test2");
        messageProducer.send(messageDto);
    }

    @RequestMapping(value = "/send1", method = RequestMethod.GET)
    @Transactional
    public void send1() {
        MessageDto messageDto = buildMsg();
        messageDto.setTags("a");
        messageProducer.send(messageDto);
        messageDto = buildMsg();
        messageDto.setTags("b");
        messageProducer.send(messageDto);
    }

    @RocketListener(topic = "test2")
    public void processMessage2(MessageDto message) {
        log.info("{}", message);
    }

    @RocketListener(topic = "test", tags = "b")
    public void processMessage(MessageDto message) {
        log.info("{}", message);
    }

    @RocketListener(topic = "test1", tags = "a")
    public void processMessage1(MessageDto message) {
        log.info("{}", message);
    }

    private MessageDto buildMsg() {
        MessageDto messageDto = new MessageDto();
        messageDto.setGid(Ids.gid());
        messageDto.setMsgId(Ids.gid());
        messageDto.setTopic("test");
        Map<String, Object> map = new HashMap<>();
        map.put("name", "test");
        messageDto.setData(map);
        return messageDto;
    }
}

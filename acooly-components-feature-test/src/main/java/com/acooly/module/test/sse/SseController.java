/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2024-06-07 12:23
 */
package com.acooly.module.test.sse;

import com.acooly.core.utils.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author zhangpu
 * @date 2024-06-07 12:23
 */
@Slf4j
@RestController
@RequestMapping("/sse")
public class SseController {

    @Autowired
    private SseNoticeService sseNoticeService;

    @CrossOrigin
    @RequestMapping("/connect")
    public SseEmitter connect(@RequestParam String userId) {
        return sseNoticeService.connect(userId);
    }

    @CrossOrigin
    @RequestMapping("/broadcast")
    public void broadcast(@RequestParam(required = true) String msg) {
        sseNoticeService.broadcast(msg);
    }

    @CrossOrigin
    @RequestMapping("/send")
    public void send(@RequestParam(required = true) String userId, @RequestParam(required = true) String msg) {
        if (Strings.isBlank(userId) || Strings.isBlank(msg)) {
            return;
        }
        SseMessage sseMessage = new SseMessage(userId, msg);
        sseNoticeService.send(sseMessage.getUserId(), sseMessage.getData());
    }

    @CrossOrigin
    @GetMapping("/close")
    public void close(@RequestParam(required = true) String userId) {
        sseNoticeService.close(userId);
    }

}

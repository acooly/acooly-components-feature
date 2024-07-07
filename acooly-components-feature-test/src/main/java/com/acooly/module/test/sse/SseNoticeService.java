/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2024-06-07 12:24
 */
package com.acooly.module.test.sse;

import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Ids;
import com.acooly.core.utils.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author zhangpu
 * @date 2024-06-07 12:24
 */
@Slf4j
@Component
public class SseNoticeService {

    private Map<String, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();

    private AtomicInteger count = new AtomicInteger(0);

    /**
     * 创建连接
     */
    public SseEmitter connect(String userId) {
        // 是否需要给客户端推送ID
        if (Strings.isBlank(userId)) {
            userId = Ids.newId15();
        }
        // 设置超时时间，0表示不过期。默认30秒
        SseEmitter sseEmitter = new SseEmitter();
        // 注册回调
        sseEmitter.onCompletion(onCompletion(userId));
        sseEmitter.onError(onError(userId));
        sseEmitter.onTimeout(onTimeout(userId));
        // 加入缓存
        sseEmitterMap.put(userId, sseEmitter);
        count.getAndIncrement();
        // 发送会话ID
        try {
            // 注册成功返回用户信息
            sseEmitter.send(SseEmitter.event().id(String.valueOf(HttpStatus.CREATED)).data(userId, MediaType.APPLICATION_JSON));
        } catch (IOException e) {
            log.error("创建长链接异常，客户端ID:{}   异常信息:{}", userId, e.getMessage());
        }
        return sseEmitter;
    }


    public void broadcast(String msg) {
        if (Collections3.isEmpty(sseEmitterMap.values())) {
            return;
        }
        // 判断发送的消息是否为空
        for (Map.Entry<String, SseEmitter> entry : sseEmitterMap.entrySet()) {
            SseMessage messageVo = new SseMessage();
            messageVo.setUserId(entry.getKey());
            messageVo.setData(msg);
            doSend(entry.getKey(), messageVo, entry.getValue());
        }
    }

    public void send(String clientId, String msg) {
        SseMessage sseMessage = new SseMessage(clientId, msg);
        doSend(clientId, sseMessage, sseEmitterMap.get(clientId));
    }

    private void doSend(String clientId, SseMessage sseMessage, SseEmitter sseEmitter) {
        if (sseEmitter == null) {
            log.error("推送消息失败：客户端{}未创建长链接,失败消息:{}", clientId, sseMessage.toString());
            return;
        }
        SseEmitter.SseEventBuilder sendData = SseEmitter.event().id(String.valueOf(HttpStatus.OK))
                .data(sseMessage, MediaType.APPLICATION_JSON);
        try {
            sseEmitter.send(sendData);
        } catch (IOException e) {
            // 推送消息失败，记录错误日志，进行重推
            log.error("推送消息失败：{},尝试进行重推", sseMessage);
            boolean isSuccess = true;
            // 推送消息失败后，每隔10s推送一次，推送5次
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(10000);
                    sseEmitter = sseEmitterMap.get(clientId);
                    if (sseEmitter == null) {
                        log.error("{}的第{}次消息重推失败，未创建长链接", clientId, i + 1);
                        continue;
                    }
                    sseEmitter.send(sendData);
                } catch (Exception ex) {
                    log.error("{}的第{}次消息重推失败", clientId, i + 1, ex);
                    continue;
                }
                log.info("{}的第{}次消息重推成功,{}", clientId, i + 1, sseMessage);
                return;
            }
        }
    }

    private Runnable onCompletion(String userId) {
        return () -> {
            log.info("用户[{}]完成连接。累计用户:{}", userId, sseEmitterMap.size());
            removeUser(userId);
        };
    }

    private Consumer onError(String userId) {
        return e -> {
            log.info("用户[{}]连接错误，从列表中清除，等待重连。", userId, e);
            removeUser(userId);
        };
    }

    private Runnable onTimeout(String userId) {
        return () -> {
            log.info("用户[{}]连接超时，从列表中清除，等待重连。", userId);
            removeUser(userId);
        };
    }

    private void removeUser(String userId) {
        if (sseEmitterMap.containsKey(userId)) {
            sseEmitterMap.remove(userId);
            count.getAndDecrement();
        }
    }


    /**
     * 关闭连接
     *
     * @param userId 客户端ID
     */
    public void close(String userId) {
        SseEmitter sseEmitter = sseEmitterMap.get(userId);
        if (sseEmitter != null) {
            sseEmitter.complete();
            removeUser(userId);
        }
    }

}

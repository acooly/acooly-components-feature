/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2024-06-08 17:19
 */
package com.acooly.module.test.sse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

/**
 * 针对SSE的超时异常做特别处理
 *
 * @author zhangpu
 * @date 2024-06-08 17:19
 */
@ControllerAdvice
public class SseExceptionHandler {
    @ExceptionHandler(AsyncRequestTimeoutException.class)
    @RequestMapping(headers = "Accept=text/event-stream")
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public ResponseEntity<String> handleAsyncRequestTimeoutException() {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("请求超时，请稍后重试！");
    }
}

package com.acooly.core.test.mock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author qiuboboy@qq.com
 * @date 2018-09-03 09:17
 */
@Component
@Slf4j
public class XServiceImpl implements XService {
    @Override
    public String echo(String body) {
        log.info("{}",body);
        return body;
    }
}

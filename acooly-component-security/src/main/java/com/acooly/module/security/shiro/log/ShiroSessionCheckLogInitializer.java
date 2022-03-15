/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-03-15 13:18
 */
package com.acooly.module.security.shiro.log;

import ch.qos.logback.classic.Level;
import com.acooly.core.common.boot.log.LogbackConfigurator;
import com.acooly.core.common.boot.log.initializer.AbstractLogInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

/**
 * 关闭Shiro Session check 的日志
 * 优化：每指定时间打印Session检测日志的情况。
 *
 * @author zhangpu
 * @date 2022-03-15 13:18
 */
@Order
public class ShiroSessionCheckLogInitializer extends AbstractLogInitializer {
    @Override
    public void init(LogbackConfigurator configurator) {
        // 设置Shiro会话监控的日志不打印
        String loggerName = "org.apache.shiro.session.mgt.AbstractValidatingSessionManager";
        String level = "OFF";
        configurator.log("设置loggerName=%s,日志级别为:%s", loggerName, level);
        configurator.logger(loggerName, Level.toLevel(level));
    }
}

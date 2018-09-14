package com.acooly.module.olog.collector.logger;

import com.acooly.core.common.facade.InfoBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;

/**
 * 日志处理的目标
 *
 * @author zhangpu
 */
@Getter
@Setter
@AllArgsConstructor
public class OlogTarget extends InfoBase {
    private Object bean;
    private Method method;
    private Object[] args;
    private Object result;
    private long executeMilliseconds;
}

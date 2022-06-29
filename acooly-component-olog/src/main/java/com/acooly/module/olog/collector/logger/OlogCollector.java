package com.acooly.module.olog.collector.logger;

import com.acooly.module.olog.facade.dto.OlogDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Olog 日志处理器
 *
 * @author zhangpu
 */
public interface OlogCollector {

    /**
     * 收集日志
     *
     * @param request
     * @param response
     * @param target
     * @param context
     * @return
     */
    OlogDTO collect(HttpServletRequest request, HttpServletResponse response, OlogTarget target,
                    Map<String, Object> context);

    /**
     * 收集日志
     *
     * @param request
     * @param response
     * @param target
     * @return
     */
    OlogDTO collect(HttpServletRequest request, HttpServletResponse response, OlogTarget target);
}

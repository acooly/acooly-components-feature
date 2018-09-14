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

    OlogDTO collect(HttpServletRequest request, HttpServletResponse response, OlogTarget target,
                    Map<String, Object> context);

    OlogDTO collect(HttpServletRequest request, HttpServletResponse response, OlogTarget target);
}

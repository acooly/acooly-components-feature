package com.acooly.module.scheduler.executor;

import com.acooly.module.scheduler.domain.SchedulerRule;
import com.acooly.module.scheduler.exceptions.SchedulerExecuteException;
import com.github.kevinsawicki.http.HttpRequest;

/**
 * @author shuijing
 */
@TaskExecutor.Type(type = TaskTypeEnum.HTTP_TASK)
public class HttpTaskExecutor implements TaskExecutor {

    public static final String HTTP = "http";
    public static int TIME_OUT = 5 * 1000;

    @Override
    public Boolean execute(SchedulerRule schedulerRule) {
        String address = schedulerRule.getProperties().trim();
        if (!address.startsWith(HTTP)) {
            address = "http://" + address;
        }
        try {
            HttpRequest.post(address.trim())
                    .readTimeout(TIME_OUT)
                    .connectTimeout(TIME_OUT)
                    .trustAllCerts()
                    .trustAllHosts()
                    .body();
        } catch (Exception e) {
            throw new SchedulerExecuteException(e);
        }
        return true;
    }

    @Override
    public void destroy() throws Exception {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}

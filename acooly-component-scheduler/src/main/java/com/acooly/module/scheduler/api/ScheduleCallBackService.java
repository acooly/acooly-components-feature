package com.acooly.module.scheduler.api;

import javax.jws.WebService;

/**
 *
 * schedule dubbo定时任务回调接口。
 *
 * 业务系统实现此接口，并把相关参数通过ScheduleService初始化，schedule就可以定时调用此webservice
 *
 * @author shuijing
 */

@WebService
public interface ScheduleCallBackService {
    /**
     * schedule通过webservice调用此方法 JUST DO IT
     */
    void justDoIT();
}

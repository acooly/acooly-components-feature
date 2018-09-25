package com.acooly.core.test.scheduler;

import com.acooly.module.scheduler.api.ScheduleCallBackService;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author shuijing
 */
@Slf4j
@Service(group = "demo", version = "1.5")
public class DemoScheduleCallBackService implements ScheduleCallBackService {
    @Override
    public void justDoIT() {
        Map<String, String> attachments = RpcContext.getContext().getAttachments();
        String myname = "";
        if (attachments.size() > 0) {
            myname = attachments.get("myname");
        }
        log.info("run scheduleCallBackService by dubbo invoke,my name is {}", myname);
    }
}

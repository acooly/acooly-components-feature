package com.acooly.module.scheduler.job;

import com.acooly.module.scheduler.dao.SchedulerRuleDao;
import com.acooly.module.scheduler.domain.SchedulerRule;
import com.acooly.module.scheduler.executor.TaskExecutor;
import com.acooly.module.scheduler.executor.TaskExecutorProvider;
import com.acooly.module.scheduler.executor.TaskTypeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.ws.http.HTTPException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import static com.acooly.module.scheduler.engine.ScheduleEngineImpl.getLogPrefix;

/**
 * @author shuijing
 */
@Component
public class QuartzJob implements Job {

    /**
     * 任务对象key
     */
    public static final String SCHEDULER_RULE_DO_KEY = "schedulerRuleDO";
    private static final Logger logger = LoggerFactory.getLogger(QuartzJob.class);
    @Autowired
    private SchedulerRuleDao schedulerRuleRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void execute(JobExecutionContext context) {

        String msgAtLastExecute = "";
        SchedulerRule schedulerRule = null;
        try {
            JobDataMap dataMap = context.getJobDetail().getJobDataMap();

            schedulerRule = objectMapper.readValue((String) dataMap.get(SCHEDULER_RULE_DO_KEY), SchedulerRule.class);

            if (schedulerRule == null) {
                logger.error("任务执行失败,没有找到任务实体,context：{}", context.toString());
                return;
            }
            //支持gid oid 不再重写就用gid了
            MDC.put("gid", getLogPrefix(schedulerRule.getId()));

            TaskExecutor taskExecutor =
                    TaskExecutorProvider.get(TaskTypeEnum.getEnumByCode(schedulerRule.getActionType()));

            logger.info("开始执行,taskId:{}, memo:{},creater:{}",
                    schedulerRule.getId(),
                    schedulerRule.getMemo(),
                    schedulerRule.getCreater());

            Boolean executeResult = taskExecutor.execute(schedulerRule);

            logger.info("执行结束,执行结果:{}", executeResult);
            msgAtLastExecute = "执行成功";
        } catch (Exception e) {
            Throwable ex = Throwables.getRootCause(e);
            msgAtLastExecute = ex.getMessage();
            if (msgAtLastExecute != null && msgAtLastExecute.length() > 240) {
                msgAtLastExecute = msgAtLastExecute.substring(0, 240) + "...";
            }
            if (isReadTimeOutException(schedulerRule, ex)) {
                return;
            }

            if ((ex instanceof HTTPException)
                    || (ex instanceof ConnectException)
                    || (ex instanceof SocketException)
                    || (ex instanceof SocketTimeoutException)) {
                logger.error(
                        "请求:{} ,异常信息:{}",
                        schedulerRule.getId(),
                        schedulerRule.getProperties(),
                        ex.getMessage());
            } else {
                logger.error("执行异常", e);
            }

        } finally {
            schedulerRuleRepository.updateExceptionAtLastExecute(msgAtLastExecute, schedulerRule.getId());
            MDC.clear();
        }
    }

    private boolean isReadTimeOutException(SchedulerRule schedulerRule, Throwable ex) {
        //忽略读超时异常
        if ((ex instanceof SocketTimeoutException
                && ex.getMessage() != null
                && ex.getMessage().contains("Read timed out"))) {
            logger.warn("请求:{} 读超时,异常信息:{}", schedulerRule.getProperties(), ex.getMessage());
            return true;
        }
        if (ex instanceof TimeoutException) {
            logger.warn("请求. 读超时,异常信息:{}", ex.getMessage());
            return true;
        }
        return false;
    }
}

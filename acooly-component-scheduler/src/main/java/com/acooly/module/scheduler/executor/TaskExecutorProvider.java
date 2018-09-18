package com.acooly.module.scheduler.executor;

import com.acooly.core.common.boot.ApplicationContextHolder;
import com.acooly.core.utils.Exceptions;
import com.acooly.module.scheduler.exceptions.SchedulerExecuteException;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author shuijing
 */
@Service
public class TaskExecutorProvider implements InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(TaskExecutorProvider.class);

    private static EnumMap<TaskTypeEnum, TaskExecutor> taskMap = Maps.newEnumMap(TaskTypeEnum.class);

    public static TaskExecutor get(TaskTypeEnum taskTypeEnum) {
        Preconditions.checkNotNull(taskTypeEnum, "任务类型不能为null");
        TaskExecutor taskExecutor = taskMap.get(taskTypeEnum);
        if (taskExecutor == null) {
            throw new SchedulerExecuteException(taskTypeEnum + "类型的处理器不存在:" + taskTypeEnum.getCode());
        }
        return taskExecutor;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        Map<String, TaskExecutor> beansMap =
                ApplicationContextHolder.get().getBeansOfType(TaskExecutor.class);

        if (beansMap.isEmpty()) {
            logger.error("定时任务执行器加载失败，无法执行定时任务!");
            return;
        }

        beansMap
                .values()
                .forEach(
                        task -> {
                            TaskExecutor.Type taskType = task.getClass().getAnnotation(TaskExecutor.Type.class);

                            if (taskType == null || taskType.type() == null) {
                                logger.error("{}没有指定任务类型", task.getClass());
                                throw Exceptions.runtimeException(task.getClass() + "没有指定任务类型");
                            }

                            TaskTypeEnum taskTypeEnum = taskType.type();

                            if (taskMap.containsKey(taskTypeEnum)) {
                                String msg =
                                        taskTypeEnum
                                                + "已经注册,"
                                                + "已注册的执行器:"
                                                + taskMap.get(taskTypeEnum).getClass().getCanonicalName();
                                throw Exceptions.runtimeException(msg);
                            }

                            taskMap.put(taskTypeEnum, task);

                            logger.info("装载定时task执行器完成:{}", taskTypeEnum);
                        });
    }
}

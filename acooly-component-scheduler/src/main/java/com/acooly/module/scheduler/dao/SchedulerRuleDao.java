package com.acooly.module.scheduler.dao;

import com.acooly.module.jpa.EntityJpaDao;
import com.acooly.module.scheduler.domain.SchedulerRule;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface SchedulerRuleDao extends EntityJpaDao<SchedulerRule, Long> {

    @Query(value = "select  current_timestamp from dual", nativeQuery = true)
    Date getSysdate();

    @Transactional
    @Modifying
    @Query(
            value =
                    "UPDATE scheduler_rule SET exception_at_last_execute= :lastExecute , last_execute_time= current_timestamp,execute_num=execute_num+1  WHERE id= :id ",
            nativeQuery = true
    )
    void updateExceptionAtLastExecute(@Param("lastExecute") String lastExecute, @Param("id") Long id);

    /**
     * 查询本地任务唯一
     *
     * @param actionType
     * @param className
     * @param methodName
     * @return
     */
    List<SchedulerRule> findByActionTypeAndClassNameAndMethodName(String actionType, String className, String methodName);

    List<SchedulerRule> findByActionTypeAndProperties(String actionType, String properties);

    List<SchedulerRule> findByActionTypeAndDGroupAndDVersion(String actionType, String dGroup, String dVersion);
}

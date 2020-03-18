package com.acooly.module.scheduler;

import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
import com.google.common.collect.Lists;
import java.util.List;

/**
 * Zhouxi O_o
 *
 * @author xi
 * @description
 */
public class DefaultStandardDatabaseScriptIniter extends StandardDatabaseScriptIniter {

    @Override
    public String getEvaluateTable() {
        return "scheduler_rule";
    }

    @Override
    public String getComponentName() {
        return "scheduler";
    }

    @Override
    public List<String> getInitSqlFile() {
        return Lists.newArrayList("scheduler", "scheduler_urls");
    }
}

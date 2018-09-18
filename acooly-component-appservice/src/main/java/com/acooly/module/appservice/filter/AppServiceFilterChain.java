/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * qiubo@yiji.com 2016-09-22 10:49 创建
 */
package com.acooly.module.appservice.filter;

import com.acooly.core.common.boot.EnvironmentHolder;
import com.acooly.core.common.exception.AppConfigException;
import com.acooly.module.filterchain.Filter;
import com.acooly.module.filterchain.FilterChainBase;
import com.google.common.base.Strings;

/**
 * @author qiubo@yiji.com
 */
public class AppServiceFilterChain extends FilterChainBase<AppServiceContext> {

    private void replaceDefaultCheckFilter() {
        String parameterCheckFilterImpl =
                EnvironmentHolder.get().getProperty("yiji.appService.parameterCheckFilterImpl");
        if (!Strings.isNullOrEmpty(parameterCheckFilterImpl)
                && !parameterCheckFilterImpl.equals(ParameterCheckFilter.class.getName())) {
            int defaultCheckFilterIdx = 0;
            int newCheckFilterIdx = 0;
            for (int i = 0; i < filters.size(); i++) {
                if (filters.get(i) instanceof ParameterCheckFilter) {
                    defaultCheckFilterIdx = i;
                }
                if (filters.get(i).getClass().getName().equals(parameterCheckFilterImpl)) {
                    newCheckFilterIdx = i;
                }
            }
            if (newCheckFilterIdx == 0) {
                throw new AppConfigException(parameterCheckFilterImpl + "没有找到");
            }
            Filter<AppServiceContext> defaultCheckFilter = filters.get(defaultCheckFilterIdx);
            Filter<AppServiceContext> newCheckFilter = filters.get(newCheckFilterIdx);
            filters.remove(newCheckFilter);
            int idx = filters.indexOf(defaultCheckFilter);
            filters.add(idx, newCheckFilter);
            filters.remove(defaultCheckFilter);
        }
    }

    @Override
    protected void adjustFilters() {
        replaceDefaultCheckFilter();
    }
}

package com.acooly.module.app.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.app.dao.AppWelcomeDao;
import com.acooly.module.app.domain.AppWelcome;
import com.acooly.module.app.service.AppWelcomeService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("appWelcomeService")
public class AppWelcomeServiceImpl extends EntityServiceImpl<AppWelcome, AppWelcomeDao>
        implements AppWelcomeService {

    @Override
    public AppWelcome getLatestOne() {
        return getEntityDao().getLatestOne();
    }

    @Override
    public void moveUp(Long id) {
        try {
            AppWelcome appWelcome = get(id);
            long current = appWelcome.getSortOrder();
            AppWelcome beforeOne = getEntityDao().findBeforeOne(current, id);
            if (beforeOne != null) {
                appWelcome.setSortOrder(beforeOne.getSortOrder());
                beforeOne.setSortOrder(current);
                this.update(appWelcome);
                this.update(beforeOne);
            }
        } catch (Exception e) {
            throw new BusinessException("上移失败", e);
        }
    }

    @Override
    public void moveTop(Long id) {
        try {
            AppWelcome appWelcome = get(id);
            appWelcome.setSortOrder((new Date()).getTime());
            update(appWelcome);
        } catch (Exception e) {
            throw new BusinessException("置顶失败", e);
        }
    }
}

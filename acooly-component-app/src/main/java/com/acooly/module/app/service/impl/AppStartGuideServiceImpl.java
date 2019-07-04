package com.acooly.module.app.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.module.app.dao.AppStartGuideDao;
import com.acooly.module.app.domain.AppStartGuide;
import com.acooly.module.app.enums.EntityStatus;
import com.acooly.module.app.service.AppStartGuideService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("appStartGuideService")
public class AppStartGuideServiceImpl extends EntityServiceImpl<AppStartGuide, AppStartGuideDao>
        implements AppStartGuideService {

    @Override
    public List<AppStartGuide> loadValidGuides() {
        return getEntityDao().findByStatusOrderBySortOrderAsc(EntityStatus.Enable);
    }

    @Override
    public void moveUp(Long id) {
        try {
            AppStartGuide appStartGuide = get(id);
            long current = appStartGuide.getSortOrder();
            AppStartGuide beforeOne = getEntityDao().findBeforeOne(current, id);
            if (beforeOne != null) {
                appStartGuide.setSortOrder(beforeOne.getSortOrder());
                beforeOne.setSortOrder(current);
                this.update(appStartGuide);
                this.update(beforeOne);
            }
        } catch (Exception e) {
            throw new BusinessException("上移失败", e);
        }
    }

    @Override
    public void moveTop(Long id) {
        try {
            AppStartGuide appStartGuide = get(id);
            appStartGuide.setSortOrder((new Date()).getTime());
            update(appStartGuide);
        } catch (Exception e) {
            throw new BusinessException("置顶失败", e);
        }
    }
}

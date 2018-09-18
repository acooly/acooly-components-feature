/**
 * create by zhangpu date:2015年5月30日
 */
package com.acooly.module.app.dao.impl;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.module.app.dao.AppMessageDaoCustom;
import com.acooly.module.app.domain.AppMessage;
import com.acooly.module.jpa.JapDynamicQueryDao;

/**
 * @author zhangpu
 */
public class AppMessageDaoImpl extends JapDynamicQueryDao<AppMessage>
        implements AppMessageDaoCustom {

    @Override
    public PageInfo<AppMessage> pageQueryUserMessages(
            PageInfo<AppMessage> pageInfo, String userName) {
        String ql =
                "from AppMessage where type='broadcast' or receivers like '%"
                        + userName
                        + "%' order by id desc";
        return pagedQueryByJpql(pageInfo, ql);
    }
}

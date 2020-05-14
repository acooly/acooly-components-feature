/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by shuijing
 * date:2017-08-01
 */
package com.acooly.module.smsend.dao;

import com.acooly.core.utils.enums.SimpleStatus;
import com.acooly.module.mybatis.EntityMybatisDao;
import com.acooly.module.smsend.entity.SmsBlackList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 短信黑名单
 * <p>
 * Date: 2017-08-01 17:28:24
 *
 * @author shuijing
 * @author zhangpu modify for mybatis
 */
public interface SmsBlackListDao extends EntityMybatisDao<SmsBlackList> {

    /**
     * 根据状态查询黑名单列表
     *
     * @param status
     * @return
     */
    @Select("select * from sms_black_list where status = #status")
    List<SmsBlackList> findByStatus(@Param("status") SimpleStatus status);

}

/*
 * acooly.cn Inc.
 * Copyright (c) 2022 All Rights Reserved.
 * create by zhangpu
 * date:2022-10-05
 */
package cn.acooly.component.rbac.service.impl;

import cn.acooly.component.rbac.dao.RbacUserDao;
import cn.acooly.component.rbac.entity.RbacUser;
import cn.acooly.component.rbac.service.RbacUserService;
import com.acooly.core.common.service.EntityServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户表 Service实现
 *
 * @author zhangpu
 * @date 2022-10-05 23:03:16
 */
@Service
public class RbacUserServiceImpl extends EntityServiceImpl<RbacUser, RbacUserDao> implements RbacUserService {


    @Override
    public RbacUser findUserByUsername(String username) {
        return getEntityDao().findByUsername(username);
    }


    @Override
    public RbacUser findUserByMemberNo(String memberNo) {
        return getEntityDao().findByMemberNo(memberNo);
    }
}

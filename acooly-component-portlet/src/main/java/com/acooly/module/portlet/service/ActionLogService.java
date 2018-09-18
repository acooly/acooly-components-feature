/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by acooly
 * date:2017-03-20
 *
 */
package com.acooly.module.portlet.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.portlet.entity.ActionLog;
import com.acooly.module.portlet.enums.ActionChannelEnum;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * portlet_action_log Service接口
 *
 * <p>Date: 2017-03-20 23:36:29
 *
 * @author acooly
 */
public interface ActionLogService extends EntityService<ActionLog> {

    ActionLog logger(
            String action,
            String actionName,
            String userName,
            ActionChannelEnum actionChannel,
            String version,
            String comments,
            HttpServletRequest request);

    ActionLog logger(@NotNull HttpServletRequest request, String userName);
}

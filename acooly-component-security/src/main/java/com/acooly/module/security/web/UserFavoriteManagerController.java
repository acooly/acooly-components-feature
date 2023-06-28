/*
 * acooly.cn Inc.
 * Copyright (c) 2023 All Rights Reserved.
 * create by zhangpu
 * date:2023-06-27
 */
package com.acooly.module.security.web;

import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.core.common.web.support.JsonListResult;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Servlets;
import com.acooly.module.security.domain.User;
import com.acooly.module.security.domain.UserFavorite;
import com.acooly.module.security.dto.UserFavorites;
import com.acooly.module.security.service.UserFavoriteService;
import com.acooly.module.security.utils.ShiroUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * sys_user_favorite 管理控制器
 *
 * @author zhangpu
 * @date 2023-06-27 20:16:01
 */
@Controller
@RequestMapping(value = "/manage/system/userFavorite")
public class UserFavoriteManagerController extends AbstractJsonEntityController<UserFavorite, UserFavoriteService> {


    {
        allowMapping = "*";
    }

    @SuppressWarnings("unused")
    @Autowired
    private UserFavoriteService userFavoriteService;

    /**
     * 查询当前用户收藏夹
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "favorites")
    public JsonListResult<UserFavorites> favorites(HttpServletRequest request, HttpServletResponse response) {
        JsonListResult<UserFavorites> result = new JsonListResult<>();
        try {
            User user = ShiroUtils.getCurrentUser();
            List<UserFavorites> rows = userFavoriteService.queryFavorites(user.getId());
            result.setRows(rows);
        } catch (Exception e) {
            handleException(result, "收藏列表", e);
        }
        return result;
    }


    @Override
    protected UserFavorite onSave(HttpServletRequest request, HttpServletResponse response, Model model, UserFavorite entity, boolean isCreate) throws Exception {
        User user = ShiroUtils.getCurrentUser();
        entity.setUserId(user.getId());
        entity.setRescId(Servlets.getLongParameter(request, "rescId"));
        // 设置新增数据默认的sortTime为当前时间戳（排序到顶部）
        if (isCreate) {
            entity.setSortTime(System.currentTimeMillis());
        }
        return super.onSave(request, response, model, entity, isCreate);
    }

    @RequestMapping(value = "deleteByRescId")
    @ResponseBody
    public JsonResult deleteByRescId(HttpServletRequest request, HttpServletResponse response) {
        JsonResult result = new JsonResult();
        User user = ShiroUtils.getCurrentUser();
        try {
            userFavoriteService.deleteByUserIdAndResourceId(user.getId(),
                    Servlets.getLongParameter(request, "rescId"));
            result.setMessage("取消收藏成功");
        } catch (Exception e) {
            handleException(result, "取消收藏", e);
        }
        return result;
    }

    /**
     * 实体实现了Sortable接口，启用了移动功能，则需要固定排序方式为按sortTime倒叙排序
     */
    @Override
    protected Map<String, Boolean> getSortMap(HttpServletRequest request) {
        Map<String, Boolean> sortMap = Maps.newHashMap();
        sortMap.put("sortTime", false);
        return sortMap;
    }


}

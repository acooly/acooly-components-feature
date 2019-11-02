package com.acooly.module.security.web;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.AbstractJsonEntityController;
import com.acooly.module.security.config.FrameworkPropertiesHolder;
import com.acooly.module.security.config.SecurityProperties;
import com.acooly.module.security.domain.Portallet;
import com.acooly.module.security.domain.User;
import com.acooly.module.security.service.PortalletService;
import com.acooly.module.security.utils.ShiroUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping(value = "/manage/system/portallet")
@ConditionalOnProperty(
        value = SecurityProperties.PREFIX + ".shiro.auth.enable",
        matchIfMissing = true
)
public class PortalletController
        extends AbstractJsonEntityController<Portallet, PortalletService> {

    private static Map<Integer, String> allCollapsibles = Maps.newTreeMap();

    static {
        allCollapsibles.put(1, "true");
        allCollapsibles.put(0, "false");
    }

    @Autowired
    private PortalletService portalletService;

    @Override
    protected Map<String, Object> getSearchParams(HttpServletRequest request) {
        Map<String, Object> map = super.getSearchParams(request);
        User user = ShiroUtils.getCurrentUser();
        if (user.getUserType() != User.USER_TYPE_ADMIN) {
            map.put("EQ_userName", user.getUsername());
        }
        return map;
    }

    @Override
    protected Portallet onSave(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model,
            Portallet entity,
            boolean isCreate)
            throws Exception {
        User user = ShiroUtils.getCurrentUser();
        if (user.getUserType() != User.USER_TYPE_ADMIN) {
            entity.setUserName(user.getUsername());
        } else {
            entity.setUserName(null);
        }
        return entity;
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allCollapsibles", allCollapsibles);
        model.put("allLoadModes", FrameworkPropertiesHolder.get().getLoadModes());
        model.put("allShowModes", FrameworkPropertiesHolder.get().getShowModes());
    }
}

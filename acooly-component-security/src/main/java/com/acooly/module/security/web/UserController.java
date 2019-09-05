package com.acooly.module.security.web;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.MappingMethod;
import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.common.web.support.JsonListResult;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Strings;
import com.acooly.core.utils.mapper.BeanCopier;
import com.acooly.core.utils.mapper.JsonMapper;
import com.acooly.module.event.EventBus;
import com.acooly.module.security.config.FrameworkPropertiesHolder;
import com.acooly.module.security.config.SecurityProperties;
import com.acooly.module.security.domain.Org;
import com.acooly.module.security.domain.Role;
import com.acooly.module.security.domain.User;
import com.acooly.module.security.dto.UserDto;
import com.acooly.module.security.dto.UserRole;
import com.acooly.module.security.event.UserCreatedEvent;
import com.acooly.module.security.service.OrgService;
import com.acooly.module.security.service.RoleService;
import com.acooly.module.security.service.UserService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Slf4j
@SuppressWarnings("unchecked")
@Controller
@RequestMapping(value = "/manage/system/user")
@ConditionalOnProperty(
        value = SecurityProperties.PREFIX + ".shiro.auth.enable",
        matchIfMissing = true
)
public class UserController extends AbstractJQueryEntityController<User, UserService> {

    private static Map<Integer, String> allUserTypes = FrameworkPropertiesHolder.get().getUserTypes();
    private static Map<Integer, String> allStatus = FrameworkPropertiesHolder.get().getUserStatus();

    @Autowired
    private RoleService roleService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private EventBus eventBus;

    @RequestMapping(value = {"listUser"})
    @ResponseBody
    public JsonListResult<UserDto> listUser(
            HttpServletRequest request, HttpServletResponse response) {
        JsonListResult<UserDto> result = new JsonListResult<UserDto>();
        allow(request, response, MappingMethod.list);
        try {
            result.appendData(referenceData(request));
            PageInfo<UserDto> pageInfo = getEntityService().queryDto(getPageInfo(request), getSearchParams(request), getSortMap(request));
            result.setTotal(pageInfo.getTotalCount());
            result.setRows(pageInfo.getPageResults());
        } catch (Exception e) {
            handleException(result, "分页查询", e);
        }
        return result;
    }

    @Override
    @RequestMapping(value = "saveJson")
    @ResponseBody
    public JsonEntityResult<User> saveJson(HttpServletRequest request, HttpServletResponse response) {
        JsonEntityResult<User> result = new JsonEntityResult<>();
        try {
            result = super.saveJson(request, response);
            if (!result.isSuccess()) {
                return result;
            }
            User entity = result.getEntity();
            entity.setRoleName(getRoleNames(entity.getId()));
            result.setEntity(entity);
            result.setMessage("保存成功！");
            UserCreatedEvent userCreatedEvent = new UserCreatedEvent();
            BeanCopier.copy(entity, userCreatedEvent);
            eventBus.publishAsync(userCreatedEvent);
        } catch (Exception e) {
            handleException(result, "保存账户", e);
        }
        return result;
    }

    @Override
    @RequestMapping(value = "updateJson")
    @ResponseBody
    public JsonEntityResult<User> updateJson(
            HttpServletRequest request, HttpServletResponse response) {
        JsonEntityResult<User> result = new JsonEntityResult<>();
        try {
            result = super.updateJson(request, response);
            if (!result.isSuccess()) {
                return result;
            }
            User entity = result.getEntity();
            entity.setRoleName(getRoleNames(entity.getId()));
            result.setEntity(entity);
            result.setMessage("更新成功！");
        } catch (Exception e) {
            handleException(result, "更新账户", e);
        }
        return result;
    }

    @Override
    protected PageInfo getPageInfo(HttpServletRequest request) {
        PageInfo pageinfo = new PageInfo();
        pageinfo.setCountOfCurrentPage(getDefaultPageSize());
        String page = request.getParameter("page");
        if (StringUtils.isNotBlank(page)) {
            pageinfo.setCurrentPage(Integer.parseInt(page));
        }
        String rows = request.getParameter("rows");
        if (StringUtils.isNotBlank(rows)) {
            pageinfo.setCountOfCurrentPage(Integer.parseInt(rows));
        }
        return pageinfo;
    }

    @RequestMapping(value = "alreadyExists")
    @ResponseBody
    public String alreadyExists(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        try {
            User user = getEntityService().getAndCheckUser(username);
            if (user != null) {
                return "true";
            } else {
                return "false";
            }
        } catch (Exception e) {
            return "true";
        }
    }

    @RequestMapping(value = "showChangePassword")
    public String showChangePassword(
            @ModelAttribute("loadEntity") User entity,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response) {
        try {
            model.addAttribute(getEntityName(), loadEntity(request));
        } catch (Exception e) {
            handleException("修改密码界面", e, request);
        }

        String url = getRequestMapperValue() + "Password";
        return url;
    }

    @RequestMapping(value = "changePassword")
    @ResponseBody
    public JsonResult changePassword(
            HttpServletRequest request, HttpServletResponse response, Model model) {
        String newPassword = request.getParameter("newPassword");
        JsonResult result = new JsonResult();
        try {
            User entity = loadEntity(request);
            getEntityService().changePassword(entity, newPassword);
            result.setMessage("密码修改成功");
        } catch (Exception e) {
            handleException(result, "修改用户密码", e);
        }
        return result;
    }

    private String getRoleIds(Long userId) {
        List<Long> list = Lists.newArrayList();
        try {
            List<UserRole> roleIds = getEntityService().getRoleIdsByUserId(userId);
            roleIds.forEach(id -> list.add(id.getRoleId()));
        } catch (Exception e) {
            log.error("查询角色失败", e);
        }
        return JsonMapper.nonDefaultMapper().toJson(list);
    }

    private String getRoleNames(Long userId) {
        StringBuilder sb = new StringBuilder();
        List<UserRole> roleIds = getEntityService().getRoleIdsByUserId(userId);
        for (UserRole userRole : roleIds) {
            Role role = roleService.get(userRole.getRoleId());
            sb.append(",").append(role.getName());
        }
        String res = sb.toString();
        return res.length() > 1 ? res.substring(1, res.length()) : res;
    }

    @Override
    protected User doSave(HttpServletRequest request, HttpServletResponse response, Model model, boolean isCreate) throws Exception {
        User entity = loadEntity(request);
        if (entity == null) {
            entity = getEntityClass().newInstance();
        }
        doDataBinding(request, entity);
        onSave(request, response, model, entity, isCreate);
        if (isCreate) {
            getEntityService().createUser(entity);
        } else {
            getEntityService().updateUser(entity);
        }

        return entity;
    }

    @Override
    protected User onSave(HttpServletRequest request, HttpServletResponse response, Model model, User entity, boolean isCreate) throws Exception {
        entity.setRoles(loadRoleFormRequest(request));
        entity.setLastModifyTime(new Date());
        if (entity.getOrgId() != null) {
            Org organize = orgService.get(entity.getOrgId());
            entity.setOrgName(organize.getName());
        }
        return entity;
    }

    @Override
    protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
        model.put("allStatus", allStatus);
        model.put("allUserTypes", allUserTypes);
        List<Role> list = roleService.getAll();
        model.put("allRoles", list);
        model.put("PASSWORD_REGEX", FrameworkPropertiesHolder.get().getPasswordRegex());
        model.put("PASSWORD_ERROR", FrameworkPropertiesHolder.get().getPasswordError());
        String id = request.getParameter(getEntityIdName());
        model.put("roleIds", id == null ? "[]" : getRoleIds(Long.valueOf(id)));
    }

    private Set<Role> loadRoleFormRequest(HttpServletRequest request) {
        Set<Role> roles = new HashSet<>();
        String[] roleArray = request.getParameterValues("role");
        if (roleArray == null) {
            throw new BusinessException("用户角色必选，请选择对应用户角色");
        }
        List<String> rolelist = new ArrayList<>();
        rolelist.addAll(Arrays.asList(roleArray));
        for (String roleid : rolelist) {
            Role role = roleService.get(Long.valueOf(roleid));
            roles.add(role);
        }
        return roles;
    }

    /**
     * 不自动绑定对象中的roleList属性，另行处理。
     */
    @InitBinder
    @Override
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("roles");
        super.initBinder(binder);
    }


    @Override
    protected User doImportEntity(List<String> fields) {
        User user = new User();
        user.setUsername(fields.get(0));
        user.setPassword(fields.get(1));
        user.setRealName(fields.get(2));
        user.setMobileNo(fields.get(3));
        user.setEmail(fields.get(4));
        // 角色
        if (fields.size() >= 6) {
            String roleName = fields.get(5);
            if (Strings.isNotBlank(roleName)) {
                Set<Role> roles = roleService.getRole(roleName);
                if (Collections3.isNotEmpty(roles)) {
                    user.setRoles(roles);
                }
            }
        }
        user.setUserType(2);
        return user;
    }

    @Override
    protected List<User> doImport(HttpServletRequest request, HttpServletResponse response, FileType fileType) throws Exception {
        Map<String, UploadResult> uploadResults = doUpload(request);
        List<List<String>> lines = loadImportFile(uploadResults, fileType, "utf-8");
        beforeUnmarshal(lines);
        List<User> entities = unmarshal(lines, request);
        afterUnmarshal(entities);

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.get(1L));
        for (User user : entities) {
            if(Collections3.isEmpty(user.getRoles())){
                user.setRoles(roles);
            }
            getEntityService().createUser(user);
        }
        return entities;
    }


}

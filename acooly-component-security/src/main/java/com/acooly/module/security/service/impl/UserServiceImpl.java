package com.acooly.module.security.service.impl;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Dates;
import com.acooly.core.utils.Encodes;
import com.acooly.module.security.config.FrameworkProperties;
import com.acooly.module.security.config.FrameworkPropertiesHolder;
import com.acooly.module.security.dao.UserDao;
import com.acooly.module.security.domain.User;
import com.acooly.module.security.dto.UserDto;
import com.acooly.module.security.dto.UserRole;
import com.acooly.module.security.service.OrgService;
import com.acooly.module.security.service.UserService;
import com.acooly.module.security.utils.Digests;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.*;

@Service("userService")
@Transactional
public class UserServiceImpl extends EntityServiceImpl<User, UserDao> implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private FrameworkProperties frameworkProperties;
    @Autowired
    private OrgService orgService;

    @Override
    public User findUserByUsername(String username) {
        return getEntityDao().findByUsername(username);
    }

    @Override
    public User getAndCheckUser(String username) {
        User user = getEntityDao().getAuthenticateUser(username);
        if (user.getStatus() != User.STATUS_ENABLE) {
            throw new AuthenticationException("用户状态不可用");
        }
        //用户织机构无效情况,组织机构无效，下面所有子机构用户无法登录
        if (user.getOrgId() != null) {
            if (!orgService.checkOrgValid(user.getOrgId())) {
                throw new AuthenticationException("你织机构已失效,无法登录,请联系管理员");
            }
        }
        return user;
    }

    @Override
    public void createUser(User user) throws BusinessException {
        checkUnique(user);
        try {
            if (StringUtils.isNotBlank(user.getPassword())) {
                entryptPassword(user, true);
            }

            if (FrameworkPropertiesHolder.get().isExpire()) {
                user.setExpirationTime(
                        Dates.addDay(new Date(), FrameworkPropertiesHolder.get().getExpireDays()));
            }

            super.save(user);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public void updateUser(User user) throws BusinessException {
        checkUnique(user);
        try {
            User orginalUser = get(user.getId());
            String oPswd = orginalUser.getPassword();
            String oSalt = orginalUser.getSalt();
            String oUsername = orginalUser.getUsername();
            user.setPassword(oPswd);
            user.setSalt(oSalt);
            user.setUsername(oUsername);
            update(user);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public void changePassword(User user, String newPassword) throws BusinessException {
        if (StringUtils.isNotBlank(newPassword)) {
            user.setPassword(newPassword);
            entryptPassword(user, false);
            if (FrameworkPropertiesHolder.get().isExpire()) {
                user.setExpirationTime(
                        Dates.addDay(new Date(), FrameworkPropertiesHolder.get().getExpireDays()));
            }
            if (user.getStatus() == User.STATUS_EXPIRES) {
                user.setStatus(User.STATUS_ENABLE);
            }
            user.setLastModifyTime(new Date());
            update(user);
        }
    }

    @Override
    public boolean validatePassword(User user, String plaintPassword) throws BusinessException {
        return entryptPassword(plaintPassword, user.getSalt()).equals(user.getPassword());
    }

    /**
     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
     *
     * @param user
     */
    private void entryptPassword(User user, boolean isCreate) {
        if (Strings.isNullOrEmpty(user.getSalt()) && !isCreate) {
            user.setPassword(entryptPassword(user.getPassword(), user.getSalt()));
        } else {
            byte[] salt = Digests.generateSalt(SALT_SIZE);
            user.setSalt(Encodes.encodeHex(salt));
            user.setPassword(entryptPassword(user.getPassword(), Encodes.encodeHex(salt)));
        }
    }

    private String entryptPassword(String plainPassword, String salt) {
        if (Strings.isNullOrEmpty(salt)) {
            return DigestUtils.md5DigestAsHex(plainPassword.getBytes(Charsets.UTF_8));
        } else {
            return Encodes.encodeHex(
                    Digests.sha1(plainPassword.getBytes(), Encodes.decodeHex(salt), HASH_INTERATIONS));
        }
    }

    private void checkUnique(User user) {
        User exsit = getEntityDao().getAuthenticateUser(user.getUsername());
        if (exsit != null && (user.getId() == null || !user.getId().equals(exsit.getId()))) {
            throw new RuntimeException("用户名:[" + user.getUsername() + "]已经存在");
        }
        if (StringUtils.isNotBlank(user.getEmail())) {
            exsit = getEntityDao().getAuthenticateUser(user.getEmail());
            if (exsit != null && (user.getId() == null || !user.getId().equals(exsit.getId()))) {
                throw new RuntimeException("电子邮件:[" + user.getEmail() + "]已经存在");
            }
        }
        if (StringUtils.isNotBlank(user.getMobileNo())) {
            exsit = getEntityDao().getAuthenticateUser(user.getMobileNo());
            if (exsit != null && (user.getId() == null || !user.getId().equals(exsit.getId()))) {
                throw new RuntimeException("手机号码:[" + user.getMobileNo() + "]已经存在");
            }
        }
    }

    @Transactional
    @Override
    public void clearLoginFailureCount(String username) {
        User user = findUserByUsername(username);
        user.setLoginFailTimes(0);
        Date now = new Date();
        user.setLastModifyTime(now);
        save(user);
    }

    @Transactional
    @Override
    public User addLoginFailureCount(String username) {
        try {
            User user = findUserByUsername(username);
            user.setLoginFailTimes(user.getLoginFailTimes() + 1);
            Date now = new Date();
            user.setLastModifyTime(now);
            if (user.getStatus() == User.STATUS_ENABLE
                    && frameworkProperties.isLoginLock()
                    && user.getLoginFailTimes() >= frameworkProperties.getLoginLockErrorTimes()) {
                user.setUnlockTime(Dates.addDate(now, frameworkProperties.getLoginLockSeconds() * 1000));
                user.setStatus(User.STATUS_LOCK);
            }
            save(user);
            return user;
        } catch (Exception e) {
            logger.error("清除登录次数失败:{}", e.getMessage());
            return null;
        }
    }

    @Override
    public PageInfo<UserDto> queryDto(
            PageInfo<UserDto> pageInfo, Map<String, Object> map, Map<String, Boolean> orderMap) {
        PageInfo<UserDto> userDtoPageInfo = getEntityDao().queryDto(pageInfo, map, orderMap);
        removedDuplicateUser(userDtoPageInfo);
        return userDtoPageInfo;
    }

    @Override
    public List<UserRole> getRoleIdsByUserId(Long userId) {
        return getEntityDao().getRoleIdsByUserId(userId);
    }

    private void removedDuplicateUser(PageInfo<UserDto> pageInfo) {
        List<UserDto> pageResults = pageInfo.getPageResults();
        Set<String> tmpset = new HashSet<>();
        Map<Long, UserDto> removedDuplicate = new TreeMap<>();

        Collections.sort(pageResults, Comparator.comparing(UserDto::getRoleName));

        for (UserDto user : pageResults) {
            if (tmpset.add(user.getUsername())) {
                removedDuplicate.put(user.getId(), user);
            } else {
                UserDto userDto = removedDuplicate.get(user.getId());
                userDto.setRoleName(userDto.getRoleName() + "," + user.getRoleName());
            }
        }
        pageResults.clear();
        pageResults.addAll(removedDuplicate.values());
        pageInfo.setPageResults(pageResults);
    }
}

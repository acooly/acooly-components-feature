package com.acooly.module.security.service;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityService;
import com.acooly.module.security.domain.User;
import com.acooly.module.security.dto.UserDto;
import com.acooly.module.security.dto.UserRole;

import java.util.List;
import java.util.Map;

public interface UserService extends EntityService<User> {

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;

    /**
     * 根据登录名获取用户
     *
     * @return
     */
    User findUserByUsername(String username);

    /**
     * 获取简单用户对象，不含延迟加载的级联对象
     *
     * @param username
     * @return
     */
    User getSimpleUser(String username);

    User getAndCheckUser(String username);

    /**
     * 创建用户
     *
     * @param user
     * @throws BusinessException
     */
    void createUser(User user) throws BusinessException;

    /**
     * 修改用户 用户登录名称和密码不能通过该方法修改
     *
     * @param user
     * @throws BusinessException
     */
    void updateUser(User user) throws BusinessException;

    /**
     * 修改密码
     *
     * @param user
     * @param newPassword
     * @throws BusinessException
     */
    void changePassword(User user, String newPassword) throws BusinessException;

    /**
     * 验证密码
     *
     * @param user
     * @param plaintPassword
     * @return
     * @throws BusinessException
     */
    boolean validatePassword(User user, String plaintPassword) throws BusinessException;

    void clearLoginFailureCount(String username);

    User addLoginFailureCount(String username);

    /**
     * 后台管理专用查询
     *
     * @param pageInfo
     * @param map
     * @param orderMap
     * @return
     */
    PageInfo<UserDto> queryDto(
            PageInfo<UserDto> pageInfo, Map<String, Object> map, Map<String, Boolean> orderMap);

    List<UserRole> getRoleIdsByUserId(Long userId);
}

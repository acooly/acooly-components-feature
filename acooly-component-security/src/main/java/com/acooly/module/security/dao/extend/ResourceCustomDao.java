package com.acooly.module.security.dao.extend;

import com.acooly.module.security.domain.Resource;
import com.acooly.module.security.dto.ResourceNode;

import java.util.List;

/**
 * 资源自定义DAO接口
 *
 * @author zhangpu
 */
public interface ResourceCustomDao {

    List<Resource> getAuthorizedResources(Long userId);

    List<Resource> getTopAuthorizedResource(Long userId);

    List<Resource> getResourcesByRole(Long roleId);

    List<ResourceNode> getAuthorizedResourceNodeAsRole(Long roleId);

    List<ResourceNode> getAuthorizedResourceNodeAsUser(Long userId);

    List<ResourceNode> getResourcesNodeByRole(Long roleId);
}

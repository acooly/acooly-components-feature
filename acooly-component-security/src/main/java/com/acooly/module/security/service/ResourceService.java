/**
 * create by zhangpu date:2015年7月23日
 */
package com.acooly.module.security.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.security.domain.Resource;
import com.acooly.module.security.dto.ResourceNode;

import java.util.List;

public interface ResourceService extends EntityService<Resource> {

    Resource findByResourceValue(String url);

    void moveUp(Long id);

    void moveTop(Long id);

    List<Resource> getResourcesByRole(Long roleId);

    List<Resource> getTopAuthorizedResource(Long userId);

    List<Resource> getAuthorizedResource(Long userId);

    List<ResourceNode> getAuthorizedResourceNode(Long userId, Long roleId);

    List<ResourceNode> getAuthorizedResourceNode(Long userId);

    List<ResourceNode> getAllResourceNode();

    List<ResourceNode> getAllResourceNode(Long roleId);
}

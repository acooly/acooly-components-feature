package com.acooly.module.security.service.impl;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Collections3;
import com.acooly.core.utils.Dates;
import com.acooly.module.security.config.SecurityProperties;
import com.acooly.module.security.dao.ResourceDao;
import com.acooly.module.security.domain.Resource;
import com.acooly.module.security.dto.ResourceNode;
import com.acooly.module.security.service.ResourceService;
import com.acooly.module.security.shiro.realm.ShiroDbRealm;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("resourceService")
@ConditionalOnProperty(
        value = SecurityProperties.PREFIX + ".shiro.auth.enable",
        matchIfMissing = true
)
@Transactional
public class ResourceServiceImpl extends EntityServiceImpl<Resource, ResourceDao>
        implements ResourceService {

    @Autowired
    private ShiroDbRealm shiroDbRealm;

    @Override
    public List<Resource> getTopAuthorizedResource(Long userId) {
        return getEntityDao().getTopAuthorizedResource(userId);
    }

    @Override
    public List<Resource> getAuthorizedResource(Long userId) {
        return getEntityDao().getAuthorizedResources(userId);
    }

    @Override
    public List<Resource> getResourcesByRole(Long roleId) {
        return getEntityDao().getResourcesByRole(roleId);
    }

    @Override
    public List<ResourceNode> getAuthorizedResourceNode(Long userId) {
        List<Resource> resources = getAuthorizedResource(userId);
        List<ResourceNode> result = Lists.newLinkedList();
        try {
            Map<Long, ResourceNode> dtoMap = Maps.newHashMap();
            for (Resource resource : resources) {
                dtoMap.put(resource.getId(), convertResource(resource));
            }
            for (Map.Entry<Long, ResourceNode> entry : dtoMap.entrySet()) {
                ResourceNode node = entry.getValue();
                if (node.getParentId() == null || node.getParentId() == 0) {
                    result.add(node);
                } else {
                    if (dtoMap.get(node.getParentId()) != null) {
                        dtoMap.get(node.getParentId()).addChild(node);
                        Collections.sort(
                                dtoMap.get(node.getParentId()).getChildren(),
                                new ResourceNode.ResourceNodeComparator());
                    }
                }
            }
            resources.clear();
            resources = null;
            dtoMap.clear();
            dtoMap = null;
            Collections.sort(result, new ResourceNode.ResourceNodeComparator());
        } catch (Exception e) {
            throw new BusinessException("获取授权资源列表失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public List<ResourceNode> getAllResourceNode() {
        List<Resource> resources = getAll();
        List<ResourceNode> result = Lists.newArrayList();
        try {
            Map<Long, ResourceNode> dtoMap = Maps.newHashMap();
            for (Resource resource : resources) {
                dtoMap.put(resource.getId(), convertResource(resource));
            }
            for (Map.Entry<Long, ResourceNode> entry : dtoMap.entrySet()) {
                ResourceNode node = entry.getValue();
                if (node.getParentId() == null || node.getParentId() == 0) {
                    result.add(node);
                    Collections.sort(result, new ResourceNode.ResourceNodeComparator());
                } else {
                    if (dtoMap.get(node.getParentId()) != null) {
                        dtoMap.get(node.getParentId()).addChild(node);
                        Collections.sort(
                                dtoMap.get(node.getParentId()).getChildren(),
                                new ResourceNode.ResourceNodeComparator());
                    }
                }
            }
            resources.clear();
            resources = null;
            dtoMap.clear();
            dtoMap = null;
            Collections.sort(result, new ResourceNode.ResourceNodeComparator());
        } catch (Exception e) {
            throw new BusinessException("获取授权资源列表失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public List<ResourceNode> getAuthorizedResourceNode(Long userId, Long roleId) {
        List<ResourceNode> resources = getEntityDao().getAuthorizedResourceNodeAsUser(userId);
        if (roleId != null) {
            List<ResourceNode> roleResources =
                    getEntityDao().getResourcesNodeByRole(Long.valueOf(roleId));
            if (Collections3.isNotEmpty(roleResources)) {
                for (ResourceNode node : resources) {
                    if (roleResources.contains(node)) {
                        node.setChecked(true);
                    }
                }
            }
        }
        List<ResourceNode> result = Lists.newArrayList();
        try {
            Map<Long, ResourceNode> dtoMap = Maps.newHashMap();
            for (ResourceNode resource : resources) {
                dtoMap.put(resource.getId(), resource);
            }
            for (Map.Entry<Long, ResourceNode> entry : dtoMap.entrySet()) {
                ResourceNode node = entry.getValue();
                if (node.getParentId() == null || node.getParentId() == 0) {
                    result.add(node);
                } else {
                    if (dtoMap.get(node.getParentId()) != null) {
                        dtoMap.get(node.getParentId()).addChild(node);
                        Collections.sort(
                                dtoMap.get(node.getParentId()).getChildren(),
                                new ResourceNode.ResourceNodeComparator());
                    }
                }
            }
            resources.clear();
            resources = null;
            dtoMap.clear();
            dtoMap = null;
            Collections.sort(result, new ResourceNode.ResourceNodeComparator());
        } catch (Exception e) {
            throw new BusinessException("获取授权资源列表失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public List<ResourceNode> getAllResourceNode(Long roleId) {
        List<ResourceNode> resources = getEntityDao().getAuthorizedResourceNodeAsRole(roleId);
        List<ResourceNode> result = Lists.newArrayList();
        try {
            Map<Long, ResourceNode> dtoMap = Maps.newHashMap();
            for (ResourceNode resource : resources) {
                dtoMap.put(resource.getId(), resource);
            }
            for (Map.Entry<Long, ResourceNode> entry : dtoMap.entrySet()) {
                ResourceNode node = entry.getValue();
                if (node.getParentId() == null || node.getParentId() == 0) {
                    result.add(node);
                } else {
                    if (dtoMap.get(node.getParentId()) != null) {
                        dtoMap.get(node.getParentId()).addChild(node);
                        Collections.sort(
                                dtoMap.get(node.getParentId()).getChildren(),
                                new ResourceNode.ResourceNodeComparator());
                    }
                }
            }
            resources.clear();
            resources = null;
            dtoMap.clear();
            dtoMap = null;
            Collections.sort(result, new ResourceNode.ResourceNodeComparator());
        } catch (Exception e) {
            throw new BusinessException("获取授权资源列表失败：" + e.getMessage());
        }
        return result;
    }

    private ResourceNode convertResource(Resource r) {
        ResourceNode node =
                new ResourceNode(
                        r.getId(),
                        r.getParent() != null ? r.getParent().getId() : null,
                        r.getName(),
                        r.getType(),
                        r.getShowMode(),
                        r.getValue(),
                        r.getIcon());
        node.setOrderTime(r.getOrderTime());
        node.setShowState(r.getShowState());
        return node;
    }

    @Override
    public void save(Resource o) throws BusinessException {
        super.save(o);
        cleanShiroCached();
    }

    @Override
    public Resource findByResourceValue(String url) {
        return getEntityDao().findByValue(url);
    }

    @Override
    public void moveUp(Long id) {
        try {
            Resource resource = get(id);
            Date current = resource.getOrderTime();
            Resource perv = null;

            long count = getEntityDao().getCountByGtOrderTime(current);
            if (count > 0) {
                PageInfo<Resource> pageInfo = new PageInfo<Resource>(1, (int) count);
                Map<String, Boolean> sortMap = Maps.newLinkedHashMap();
                sortMap.put("menu.orderTime", false);
                sortMap.put("orderTime", false);
                Map<String, Object> searchMap = Maps.newHashMap();
                searchMap.put("GT_orderTime", Dates.format(current, Dates.CHINESE_DATETIME_FORMAT_LINE));
                pageInfo = getEntityDao().query(pageInfo, searchMap, sortMap);
                if (pageInfo.getPageResults() != null && pageInfo.getPageResults().size() > 0) {
                    perv = pageInfo.getPageResults().get(0);
                }
            }

            if (perv != null) {
                resource.setOrderTime(perv.getOrderTime());
                perv.setOrderTime(current);
                save(perv);
            } else {
                resource.setOrderTime(new Date());
            }
            save(resource);
        } catch (Exception e) {
            throw new BusinessException("上移失败", e);
        }
    }

    @Override
    public void moveTop(Long id) {
        try {
            Resource resource = get(id);
            resource.setOrderTime(new Date());
            save(resource);
        } catch (Exception e) {
            throw new BusinessException("置顶失败", e);
        }
    }

    private void cleanShiroCached() {
        try {
            // 清空权限缓存
            shiroDbRealm.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
        } catch (Exception e) {
            // ig
        }
    }
}

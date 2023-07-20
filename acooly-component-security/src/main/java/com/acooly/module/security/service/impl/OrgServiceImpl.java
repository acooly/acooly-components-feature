/*
 * acooly.cn Inc.
 * Copyright (c) 2017 All Rights Reserved.
 * create by shuijing
 * date:2017-05-26
 */
package com.acooly.module.security.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.Strings;
import com.acooly.module.security.dao.OrgDao;
import com.acooly.module.security.domain.Org;
import com.acooly.module.security.enums.OrgStatus;
import com.acooly.module.security.service.OrgService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
 * 组织机构 Service实现
 *
 * <p>
 * Date: 2017-05-26 16:48:57
 *
 * @author shuijing
 */
@Service("orgService")
public class OrgServiceImpl extends EntityServiceImpl<Org, OrgDao> implements OrgService {

    public static final Long ROOT_PARENT_ID = 0L;


    @Override
    public void save(Org o) throws BusinessException {
        if (o.getParentId() == null) {
            o.setParentId(ROOT_PARENT_ID);
        }
        if (o.getSortTime() == null) {
            o.setSortTime(System.currentTimeMillis());
        }
        super.save(o);
        fillOrg(o);
    }

    @Override
    public void update(Org o) throws BusinessException {
        if (o.getSortTime() == null) {
            o.setSortTime(System.currentTimeMillis());
        }
        super.update(o);
        fillOrg(o);
    }

    /**
     * 重新：同级移动
     * 列表排序为：sortTime asc, 移动排序则反之
     *
     * @param id
     * @param map
     * @param sortMap
     */
    @Override
    public void up(Serializable id, Map<String, Object> map, Map<String, Boolean> sortMap) {
        Org org = get(id);
        if (org == null) {
            return;
        }
        Long parentId = org.getParentId();
        map.put("EQ_parentId", parentId);
        map.put("LT_sortTime", org.getSortTime());
        sortMap.put("sortTime", false);
        super.up(id, map, sortMap);
    }

    @Override
    public Map<Long, Object> getOrganizeInfo(long parentId) {
        Map<Long, Object> maps = Maps.newLinkedHashMap();
        Org pOrganize = getEntityDao().get(parentId);
        maps.put(pOrganize.getId(), pOrganize.getName());
        List<Org> organizeList = getEntityDao().findByParentId(parentId);
        for (Org organize : organizeList) {
            maps.put(organize.getId(), organize.getName());
        }
        return maps;
    }

    @Override
    public List<Org> getTreeList(Long orgId) {
        Map<Long, Org> maps = getOrgList();
        List<Org> result = doTreeList(orgId, maps);
        return result;
    }

    @Override
    public List<Org> getTreeListLikeName(Long orgId, String name) {
        Map<Long, Org> allMaps = getOrgList();
        Map<Long, Org> maps = Maps.newHashMap();
        if (Strings.isNotBlank(name)) {
            List<Org> orgLikeList = getEntityDao().findByNameLike(name);
            for (Org org : orgLikeList) {
                maps = getNodeMaps(org, maps, allMaps);
            }
        } else {
            maps = allMaps;
        }
        List<Org> result = doTreeList(orgId, maps);
        return result;
    }

    /**
     * 获取所有组织结构
     *
     * @return
     */
    public Map<Long, Org> getOrgList() {
//        Map<String,Boolean> sortMap = Maps.newLinkedHashMap();
//        sortMap.put("sortTime",true);
        List<Org> organizeList = getAll();
        Map<Long, Org> maps = Maps.newHashMap();
        for (Org organize : organizeList) {
            maps.put(organize.getId(), fillOrg(organize));
        }
        return maps;
    }

    protected Org fillOrg(Org organize) {
        // 设置下拉树，展示节点名称
        if (organize.getStatus() == OrgStatus.invalid) {
            organize.setText(organize.getName() + "(" + OrgStatus.invalid.message() + ")");
        } else {
            organize.setText(organize.getName());
        }
        if (organize.getStatus() != null) {
            organize.setStatusView(organize.getStatus().getMessage());
        }
        return organize;
    }


    /**
     * 递归构造上层结构
     *
     * @param org
     * @param maps
     * @param allMaps
     * @return
     */
    private Map<Long, Org> getNodeMaps(Org org, Map<Long, Org> maps, Map<Long, Org> allMaps) {
        maps.put(org.getId(), org);
        if (org.getParentId().equals(ROOT_PARENT_ID)) {
            return maps;
        }
        if (org.getParentId() == null) {
            return maps;
        }
        return getNodeMaps(allMaps.get(org.getParentId()), maps, allMaps);
    }

    /**
     * 构造树形结构
     *
     * @param orgId
     * @param maps
     * @return
     */
    public List<Org> doTreeList(Long orgId, Map<Long, Org> maps) {
        List<Org> result = Lists.newArrayList();
        Set<Long> mapKey = maps.keySet();
        for (Long keyId : mapKey) {
            Org organize = maps.get(keyId);
            if (orgId.equals(ROOT_PARENT_ID) && organize.getParentId().equals(ROOT_PARENT_ID)) {
                // 传入orgId=0时，查询所有节点
                result.add(organize);
            } else if (organize.getId().longValue() == orgId.longValue()) {
                // 传入orgId!=0时，查询该节点下的所有子节点
                result.add(organize);
            } else {
                Org parentData = maps.get(organize.getParentId());
                if (parentData == null) {
                    continue;
                }
                if (parentData.getChildren() != null) {
                    parentData.getChildren().add(organize);
                } else {
                    List<Org> children = Lists.newArrayList();
                    children.add(organize);
                    parentData.setChildren(children);
                }
            }
        }
        // 排序
//        Collections.sort(result, Comparator.nullsLast(Comparator.comparing(Org::getSortTime)));
        return result;
    }

    @Override
    public boolean checkOrgValid(Long orgId) {
        List<Org> organizeList = getEntityDao().getAll();
        Map<Long, Org> maps = Maps.newHashMap();
        for (Org organize : organizeList) {
            maps.put(organize.getId(), organize);
        }
        return isValid(orgId, maps);
    }

    private boolean isValid(Long orgId, Map<Long, Org> orgMaps) {
        if (orgId.equals(ROOT_PARENT_ID)) {
            return true;
        }
        Org rorg = orgMaps.get(orgId);
        if (rorg == null) {
            return false;
        }
        if (rorg.getStatus() == OrgStatus.invalid) {
            return false;
        } else if (rorg.getParentId() != null) {
            return isValid(rorg.getParentId(), orgMaps);
        }
        return true;
    }

}

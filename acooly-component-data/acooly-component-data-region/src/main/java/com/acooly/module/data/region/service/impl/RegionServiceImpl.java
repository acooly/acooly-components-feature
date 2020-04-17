/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by zhangpu
 * date:2019-05-06
 */
package com.acooly.module.data.region.service.impl;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.arithmetic.tree.QuickTree;
import com.acooly.module.data.region.dao.RegionDao;
import com.acooly.module.data.region.dto.RegionInfo;
import com.acooly.module.data.region.entity.Region;
import com.acooly.module.data.region.service.RegionService;
import com.google.common.collect.Lists;

/**
 * 省市区编码表 Service实现
 * <p>
 * Date: 2019-05-06 18:32:21
 *
 * @author zhangpu
 */
@Service("regionService")
public class RegionServiceImpl extends EntityServiceImpl<Region, RegionDao> implements RegionService {

	@Override
	@Cacheable(value = "acooly.compoment.data.region.tree")
	public List<RegionInfo> tree() {
		List<Region> regions = getAll();
		List<RegionInfo> regionInfos = Lists.newArrayList();
		regions.forEach(e -> {
			regionInfos.add(new RegionInfo(e.getId(), e.getParentId(), e.getName(), e.getSortTime()));
		});
		// 构建树，先按排序时间倒叙，然后按id升序排序
		return QuickTree.quickTree(regionInfos, Region.ROOT_ID, Comparator
				.nullsLast(Comparator.comparing((RegionInfo r) -> -r.getSortTime()).thenComparing(r -> r.getId())));
	}

	@Override
	@Cacheable(value = "acooly.compoment.data.region.tree", key = "#appName")
	public List<RegionInfo> tree(String appName) {
		return tree();
	}

	@Override
	@Cacheable(value = "acooly.compoment.data.region", key = "#id")
	public Region get(Serializable id) throws BusinessException {
		return super.get(id);
	}
}

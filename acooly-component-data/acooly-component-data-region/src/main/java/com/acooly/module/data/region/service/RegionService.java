/*
 * acooly.cn Inc.
 * Copyright (c) 2019 All Rights Reserved.
 * create by zhangpu
 * date:2019-05-06
 *
 */
package com.acooly.module.data.region.service;

import java.util.List;

import com.acooly.core.common.service.EntityService;
import com.acooly.module.data.region.dto.RegionInfo;
import com.acooly.module.data.region.entity.Region;

/**
 * 省市区编码表 Service接口
 * <p>
 * Date: 2019-05-06 18:32:21
 *
 * @author zhangpu
 */
public interface RegionService extends EntityService<Region> {

	/**
	 * 构建区域树
	 * 
	 * 先按排序时间倒叙，然后按id升序排序
	 */
	public List<RegionInfo> tree();

	/**
	 * 有缓存
	 * 
	 * 构建区域树
	 * 
	 * 先按排序时间倒叙，然后按id升序排序
	 * 
	 * @param appName
	 * @return
	 */
	public List<RegionInfo> tree(String appName);

}

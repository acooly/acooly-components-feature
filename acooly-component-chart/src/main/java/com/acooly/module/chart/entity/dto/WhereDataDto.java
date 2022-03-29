package com.acooly.module.chart.entity.dto;

import java.io.Serializable;
import java.util.Map;

import com.acooly.module.chart.enums.WhereTypeEnum;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

import lombok.Data;

@Data
public class WhereDataDto implements Serializable {

	/**
	 * 名称
	 */
	private String name = "用户名:";

	/**
	 * 条件参数
	 **/
	private String conditParam = "name like '%_$name$_%'";

	/**
	 * 数据类型
	 **/
	private String dataType = "text";

	/**
	 * 默认值
	 */
	private String defaultValue;

	/**
	 * 下拉数据转换
	 */
	private Map<String, Object> pullDownMap;

	public Map<String, Object> getPullDownMap() {
		Map<String, Object> maps = Maps.newHashMap();
		if (dataType.equals(WhereTypeEnum.pull_down.code())) {
			maps=JSON.parseObject(getDefaultValue());
		}
		return maps;
	}

}
